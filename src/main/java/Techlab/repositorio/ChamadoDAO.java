
package Techlab.repositorio;

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
import Techlab.negocio.ChamadoSuporte;

public class ChamadoDAO implements IPersistencia<ChamadoSuporte> {

    private final ConexaoSQLite banco;
    private final AtivoDAO ativoDAO;

    public ChamadoDAO(ConexaoSQLite banco) {
        if (banco == null) {
            throw new IllegalArgumentException(
                "Conexão com banco inválida."
            );
        }

        this.banco = banco;
        this.ativoDAO = new AtivoDAO(banco);
    }

    @Override
    public void salvar(ChamadoSuporte chamado)
            throws FalhaConexaoBDException {

        if (chamado == null) {
            throw new IllegalArgumentException(
                "O chamado não pode ser nulo."
            );
        }

        String sql = """
            INSERT INTO chamados
            (id, titulo, descricao, prioridade, status,
             data_abertura, ativo_id)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement ps =
                banco.getConexao().prepareStatement(sql)) {

            ps.setInt(1, chamado.getId());
            ps.setString(2, chamado.getTitulo());
            ps.setString(3, chamado.getDescricao());
            ps.setString(4, chamado.getPrioridade());
            ps.setString(5, chamado.getStatus());
            ps.setString(6, chamado.getDataAbertura().toString());
            ps.setInt(7, chamado.getAtivoRelacionado().getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new FalhaConexaoBDException(
                "Erro ao salvar chamado no banco.", e
            );
        }
    }

    @Override
    public ChamadoSuporte buscarPorId(int id)
            throws EquipamentoNaoEncontradoException,
                   FalhaConexaoBDException {

        String sql = """
            SELECT * FROM chamados WHERE id = ?
            """;

        try (PreparedStatement ps =
                banco.getConexao().prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapearChamado(rs);
                }
            }

            throw new EquipamentoNaoEncontradoException(
                "Chamado com ID " + id + " não encontrado."
            );

        } catch (SQLException e) {
            throw new FalhaConexaoBDException(
                "Erro ao buscar chamado no banco.", e
            );
        }
    }

    @Override
    public List<ChamadoSuporte> listarTodos()
            throws FalhaConexaoBDException {

        List<ChamadoSuporte> chamados = new ArrayList<>();

        String sql = """
            SELECT * FROM chamados ORDER BY id
            """;

        try (PreparedStatement ps =
                banco.getConexao().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                chamados.add(mapearChamado(rs));
            }

            return chamados;

        } catch (EquipamentoNaoEncontradoException e) {
            throw new FalhaConexaoBDException(
                "Ativo relacionado ao chamado não encontrado.", e
            );

        } catch (SQLException e) {
            throw new FalhaConexaoBDException(
                "Erro ao listar chamados do banco.", e
            );
        }
    }

    @Override
    public void atualizar(ChamadoSuporte chamado)
            throws EquipamentoNaoEncontradoException,
                   FalhaConexaoBDException {

        if (chamado == null) {
            throw new IllegalArgumentException(
                "O chamado não pode ser nulo."
            );
        }

        String sql = """
            UPDATE chamados
            SET titulo = ?,
                descricao = ?,
                prioridade = ?,
                status = ?,
                data_abertura = ?,
                ativo_id = ?
            WHERE id = ?
            """;

        try (PreparedStatement ps =
                banco.getConexao().prepareStatement(sql)) {

            ps.setString(1, chamado.getTitulo());
            ps.setString(2, chamado.getDescricao());
            ps.setString(3, chamado.getPrioridade());
            ps.setString(4, chamado.getStatus());
            ps.setString(5, chamado.getDataAbertura().toString());
            ps.setInt(6, chamado.getAtivoRelacionado().getId());
            ps.setInt(7, chamado.getId());

            if (ps.executeUpdate() == 0) {
                throw new EquipamentoNaoEncontradoException(
                    "Não é possível atualizar: chamado não encontrado."
                );
            }

        } catch (SQLException e) {
            throw new FalhaConexaoBDException(
                "Erro ao atualizar chamado no banco.", e
            );
        }
    }

    @Override
    public void deletar(int id)
            throws EquipamentoNaoEncontradoException,
                   FalhaConexaoBDException {

        String sql = "DELETE FROM chamados WHERE id = ?";

        try (PreparedStatement ps =
                banco.getConexao().prepareStatement(sql)) {

            ps.setInt(1, id);

            if (ps.executeUpdate() == 0) {
                throw new EquipamentoNaoEncontradoException(
                    "Não é possível excluir: chamado não encontrado."
                );
            }

        } catch (SQLException e) {
            throw new FalhaConexaoBDException(
                "Erro ao excluir chamado do banco.", e
            );
        }
    }

    private ChamadoSuporte mapearChamado(ResultSet rs)
            throws SQLException,
                   EquipamentoNaoEncontradoException,
                   FalhaConexaoBDException {

        int ativoId = rs.getInt("ativo_id");

        AtivoTI ativo = ativoDAO.buscarPorId(ativoId);

        return new ChamadoSuporte(
            rs.getInt("id"),
            rs.getString("titulo"),
            rs.getString("descricao"),
            rs.getString("prioridade"),
            rs.getString("status"),
            LocalDateTime.parse(rs.getString("data_abertura")),
            ativo
        );
    }
}

