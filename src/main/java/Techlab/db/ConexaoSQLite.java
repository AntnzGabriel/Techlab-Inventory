

package Techlab.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import Techlab.excecoes.FalhaConexaoBDException;
import Techlab.interfaces.IConexaoBD;

public class ConexaoSQLite implements IConexaoBD {

    private Connection conexao;

    private static final String URL = "jdbc:sqlite:techlab.db";

    @Override
    public void conectar() throws FalhaConexaoBDException {
        try {
            conexao = DriverManager.getConnection(URL);

            try (Statement statement = conexao.createStatement()) {
                statement.execute("PRAGMA foreign_keys = ON");
            }

            System.out.println("Conexão com SQLite estabelecida!");
            System.out.println("Chaves estrangeiras habilitadas!");

        } catch (SQLException e) {

            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroFechamento) {
                    e.addSuppressed(erroFechamento);
                }

                conexao = null;
            }

            throw new FalhaConexaoBDException(
                "Erro ao conectar ao banco SQLite.", e
            );
        }
    }

    public Connection getConexao()
            throws FalhaConexaoBDException {

        try {
            if (conexao == null || conexao.isClosed()) {
                throw new FalhaConexaoBDException(
                    "Não existe conexão ativa com o banco."
                );
            }

            return conexao;

        } catch (SQLException e) {
            throw new FalhaConexaoBDException(
                "Erro ao verificar conexão com o banco.", e
            );
        }
    }

    @Override
    public void executarQuery(String sql)
            throws FalhaConexaoBDException {

        try (Statement statement = getConexao().createStatement()) {
            statement.execute(sql);

        } catch (SQLException e) {
            throw new FalhaConexaoBDException(
                "Erro ao executar comando SQL.", e
            );
        }
    }

    public void fecharConexao()
            throws FalhaConexaoBDException {

        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
                System.out.println("Conexão encerrada.");
            }

        } catch (SQLException e) {
            throw new FalhaConexaoBDException(
                "Erro ao fechar conexão.", e
            );
        }
    }
}
