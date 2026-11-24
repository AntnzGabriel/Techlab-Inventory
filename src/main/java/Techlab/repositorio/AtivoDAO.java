
package Techlab.repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Techlab.db.ConexaoSQLite;
import Techlab.excecoes.EquipamentoNaoEncontradoException;
import Techlab.excecoes.FalhaConexaoBDException;
import Techlab.interfaces.IPersistencia;
import Techlab.negocio.AtivoTI;
import Techlab.negocio.Servidor;
import Techlab.negocio.EstacaoTrabalho;
import Techlab.negocio.DispositivoRede;

public class AtivoDAO implements IPersistencia<AtivoTI> {

    private final ConexaoSQLite banco;

    public AtivoDAO(ConexaoSQLite banco) {
        if (banco == null) {
            throw new IllegalArgumentException(
                "Conexão com banco inválida."
            );
        }

        this.banco = banco;
    }

    private String identificarTipo(AtivoTI ativo) {

        if (ativo instanceof Servidor) {
            return "SERVIDOR";
        }

        if (ativo instanceof EstacaoTrabalho) {
            return "ESTACAO_TRABALHO";
        }

        if (ativo instanceof DispositivoRede) {
            return "DISPOSITIVO_REDE";
        }

        throw new IllegalArgumentException(
            "Tipo de ativo não reconhecido."
        );
    }

    private void inserirDetalhes(
            Connection conexao,
            AtivoTI ativo) throws SQLException {

        if (ativo instanceof Servidor servidor) {

            String sql = """
                INSERT INTO servidores
                (ativo_id, ip_estatico, sistema_operacional,
                 capacidade_ram)
                VALUES (?, ?, ?, ?)
                """;

            try (PreparedStatement ps =
                    conexao.prepareStatement(sql)) {

                ps.setInt(1, servidor.getId());
                ps.setString(2, servidor.getIpEstatico());
                ps.setString(3, servidor.getSistemaOperacional());
                ps.setInt(4, servidor.getCapacidadeRAM());

                ps.executeUpdate();
            }

        } else if (ativo instanceof EstacaoTrabalho estacao) {

            String sql = """
                INSERT INTO estacoes_trabalho
                (ativo_id, departamento, usuario_alocado)
                VALUES (?, ?, ?)
                """;

            try (PreparedStatement ps =
                    conexao.prepareStatement(sql)) {

                ps.setInt(1, estacao.getId());
                ps.setString(2, estacao.getDepartamento());
                ps.setString(3, estacao.getUsuarioAlocado());

                ps.executeUpdate();
            }

        } else if (ativo instanceof DispositivoRede dispositivo) {

            String sql = """
                INSERT INTO dispositivos_rede
                (ativo_id, quantidade_portas)
                VALUES (?, ?)
                """;

            try (PreparedStatement ps =
                    conexao.prepareStatement(sql)) {

                ps.setInt(1, dispositivo.getId());
                ps.setInt(2, dispositivo.getQuantidadePortas());

                ps.executeUpdate();
            }
        }
    }

    private void executarTransacao(OperacaoSQL operacao)
            throws FalhaConexaoBDException {

        Connection conexao = banco.getConexao();

        try {
            boolean autoCommitOriginal = conexao.getAutoCommit();

            conexao.setAutoCommit(false);

            try {
                operacao.executar(conexao);
                conexao.commit();

            } catch (SQLException | RuntimeException e) {

                try {
                    conexao.rollback();
                } catch (SQLException erroRollback) {
                    e.addSuppressed(erroRollback);
                }

                throw e;

            } finally {
                conexao.setAutoCommit(autoCommitOriginal);
            }

        } catch (SQLException e) {
            throw new FalhaConexaoBDException(
                "Erro durante transação com o banco.", e
            );
        }
    }

    @FunctionalInterface
    private interface OperacaoSQL {
        void executar(Connection conexao) throws SQLException;
    }

    private static class AtivoNaoEncontradoRuntimeException
            extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }

    @Override
    public void salvar(AtivoTI objeto)
            throws FalhaConexaoBDException {

        if (objeto == null) {
            throw new IllegalArgumentException(
                "O ativo não pode ser nulo."
            );
        }

        String tipo = identificarTipo(objeto);

        executarTransacao(conexao -> {

            String sql = """
                INSERT INTO ativos
                (id, codigo_patrimonio, modelo,
                 data_aquisicao, status, tipo)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

            try (PreparedStatement ps =
                    conexao.prepareStatement(sql)) {

                ps.setInt(1, objeto.getId());
                ps.setString(2, objeto.getCodigoPatrimonio());
                ps.setString(3, objeto.getModelo());
                ps.setString(4, objeto.getDataAquisicao().toString());
                ps.setString(5, objeto.getStatus());
                ps.setString(6, tipo);

                ps.executeUpdate();
            }

            inserirDetalhes(conexao, objeto);
        });
    }

    private AtivoTI mapearAtivo(ResultSet rs)
            throws SQLException {

        int id = rs.getInt("id");
        String codigo = rs.getString("codigo_patrimonio");
        String modelo = rs.getString("modelo");

        LocalDateTime data = LocalDateTime.parse(
            rs.getString("data_aquisicao")
        );

        String status = rs.getString("status");
        String tipo = rs.getString("tipo");

        switch (tipo) {

            case "SERVIDOR":
                return new Servidor(
                    id,
                    codigo,
                    modelo,
                    data,
                    status,
                    rs.getString("servidor_ip"),
                    rs.getString("servidor_so"),
                    rs.getInt("servidor_ram")
                );

            case "ESTACAO_TRABALHO":
                return new EstacaoTrabalho(
                    id,
                    codigo,
                    modelo,
                    data,
                    status,
                    rs.getString("est_departamento"),
                    rs.getString("est_usuario")
                );

            case "DISPOSITIVO_REDE":
                return new DispositivoRede(
                    id,
                    codigo,
                    modelo,
                    data,
                    status,
                    rs.getInt("rede_portas")
                );

            default:
                throw new SQLException(
                    "Tipo de ativo desconhecido: " + tipo
                );
        }
    }

    private String consultaCompleta() {

        return """
            SELECT
                a.id,
                a.codigo_patrimonio,
                a.modelo,
                a.data_aquisicao,
                a.status,
                a.tipo,
                s.ip_estatico AS servidor_ip,
                s.sistema_operacional AS servidor_so,
                s.capacidade_ram AS servidor_ram,
                e.departamento AS est_departamento,
                e.usuario_alocado AS est_usuario,
                d.quantidade_portas AS rede_portas
            FROM ativos a
            LEFT JOIN servidores s
                ON a.id = s.ativo_id
            LEFT JOIN estacoes_trabalho e
                ON a.id = e.ativo_id
            LEFT JOIN dispositivos_rede d
                ON a.id = d.ativo_id
            """;
    }

    @Override
    public AtivoTI buscarPorId(int id)
            throws EquipamentoNaoEncontradoException,
                   FalhaConexaoBDException {

        String sql = consultaCompleta() + " WHERE a.id = ?";

        try (PreparedStatement ps =
                banco.getConexao().prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapearAtivo(rs);
                }
            }

            throw new EquipamentoNaoEncontradoException(
                "Ativo com ID " + id + " não encontrado."
            );

        } catch (SQLException e) {
            throw new FalhaConexaoBDException(
                "Erro ao buscar ativo no banco.", e
            );
        }
    }

    @Override
    public List<AtivoTI> listarTodos()
            throws FalhaConexaoBDException {

        List<AtivoTI> lista = new ArrayList<>();

        String sql = consultaCompleta() + " ORDER BY a.id";

        try (PreparedStatement ps =
                banco.getConexao().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearAtivo(rs));
            }

            return lista;

        } catch (SQLException e) {
            throw new FalhaConexaoBDException(
                "Erro ao listar ativos do banco.", e
            );
        }
    }

    @Override
    public void atualizar(AtivoTI objeto)
            throws EquipamentoNaoEncontradoException,
                   FalhaConexaoBDException {

        if (objeto == null) {
            throw new IllegalArgumentException(
                "O ativo não pode ser nulo."
            );
        }

        String tipo = identificarTipo(objeto);

        try {
            executarTransacao(conexao -> {

                String sql = """
                    UPDATE ativos
                    SET codigo_patrimonio = ?,
                        modelo = ?,
                        data_aquisicao = ?,
                        status = ?,
                        tipo = ?
                    WHERE id = ?
                    """;

                try (PreparedStatement ps =
                        conexao.prepareStatement(sql)) {

                    ps.setString(1, objeto.getCodigoPatrimonio());
                    ps.setString(2, objeto.getModelo());
                    ps.setString(3, objeto.getDataAquisicao().toString());
                    ps.setString(4, objeto.getStatus());
                    ps.setString(5, tipo);
                    ps.setInt(6, objeto.getId());

                    if (ps.executeUpdate() == 0) {
                        throw new AtivoNaoEncontradoRuntimeException();
                    }
                }

                String[] tabelas = {
                    "servidores",
                    "estacoes_trabalho",
                    "dispositivos_rede"
                };

                for (String tabela : tabelas) {

                    String exclusao =
                        "DELETE FROM " + tabela + " WHERE ativo_id = ?";

                    try (PreparedStatement ps =
                            conexao.prepareStatement(exclusao)) {

                        ps.setInt(1, objeto.getId());
                        ps.executeUpdate();
                    }
                }

                inserirDetalhes(conexao, objeto);
            });

        } catch (AtivoNaoEncontradoRuntimeException e) {

            throw new EquipamentoNaoEncontradoException(
                "Não é possível atualizar: ativo não encontrado."
            );
        }
    }

    @Override
    public void deletar(int id)
            throws EquipamentoNaoEncontradoException,
                   FalhaConexaoBDException {

        try {
            executarTransacao(conexao -> {

                String sql = "DELETE FROM ativos WHERE id = ?";

                try (PreparedStatement ps =
                        conexao.prepareStatement(sql)) {

                    ps.setInt(1, id);

                    if (ps.executeUpdate() == 0) {
                        throw new AtivoNaoEncontradoRuntimeException();
                    }
                }
            });

        } catch (AtivoNaoEncontradoRuntimeException e) {

            throw new EquipamentoNaoEncontradoException(
                "Não é possível excluir: ativo não encontrado."
            );
        }
    }
}
