
package Techlab;

import java.util.List;

import Techlab.db.ConexaoSQLite;
import Techlab.db.CriadorTabelas;
import Techlab.excecoes.EquipamentoNaoEncontradoException;
import Techlab.excecoes.FalhaConexaoBDException;
import Techlab.negocio.AtivoTI;
import Techlab.repositorio.AtivoDAO;

public class main {

    public static void main(String[] args) {

        ConexaoSQLite banco = new ConexaoSQLite();

        try {
            banco.conectar();

            CriadorTabelas criador = new CriadorTabelas(banco);
            criador.criarTodasTabelas();

            AtivoDAO dao = new AtivoDAO(banco);

            // Exibir os ativos antes da exclusão
            System.out.println("\n=== ATIVOS ANTES DA EXCLUSÃO ===");

            List<AtivoTI> ativos = dao.listarTodos();

            for (AtivoTI ativo : ativos) {
                System.out.println(
                    "ID: " + ativo.getId()
                    + " | Patrimônio: " + ativo.getCodigoPatrimonio()
                    + " | Modelo: " + ativo.getModelo()
                );
            }

            // Excluir o dispositivo de rede
            System.out.println("\n=== TESTE DE EXCLUSÃO ===");

            dao.deletar(3);

            System.out.println("Ativo de ID 3 excluído com sucesso!");

            // Consultar novamente
            System.out.println("\n=== ATIVOS APÓS A EXCLUSÃO ===");

            List<AtivoTI> ativosRestantes = dao.listarTodos();

            for (AtivoTI ativo : ativosRestantes) {
                System.out.println(
                    "ID: " + ativo.getId()
                    + " | Patrimônio: " + ativo.getCodigoPatrimonio()
                    + " | Modelo: " + ativo.getModelo()
                );
            }

            System.out.println(
                "\nTotal de ativos restantes: " + ativosRestantes.size()
            );

        } catch (
            FalhaConexaoBDException |
            EquipamentoNaoEncontradoException e
        ) {
            System.out.println("\nFalha: " + e.getMessage());
            e.printStackTrace();

        } finally {
            try {
                banco.fecharConexao();

            } catch (FalhaConexaoBDException e) {
                System.out.println(
                    "Erro ao fechar conexão: " + e.getMessage()
                );
            }
        }
    }
}
