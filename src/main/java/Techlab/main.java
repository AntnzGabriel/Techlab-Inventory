
package Techlab;

import java.util.List;

import Techlab.db.ConexaoSQLite;
import Techlab.db.CriadorTabelas;
import Techlab.negocio.AtivoTI;
import Techlab.negocio.ChamadoSuporte;
import Techlab.repositorio.AtivoDAO;
import Techlab.repositorio.ChamadoDAO;

public class main {

    public static void main(String[] args) {

        ConexaoSQLite banco = new ConexaoSQLite();

        try {
            banco.conectar();

            CriadorTabelas criador = new CriadorTabelas(banco);
            criador.criarTodasTabelas();

            AtivoDAO ativoDAO = new AtivoDAO(banco);
            ChamadoDAO chamadoDAO = new ChamadoDAO(banco);

            List<AtivoTI> ativos = ativoDAO.listarTodos();

            List<ChamadoSuporte> chamados =
                chamadoDAO.listarTodos();

            System.out.println("\n===== TECHLAB INVENTORY =====");

            System.out.println(
                "\nTotal de ativos cadastrados: " + ativos.size()
            );

            for (AtivoTI ativo : ativos) {
                System.out.println(
                    "ID: " + ativo.getId() +
                    " | Patrimônio: " + ativo.getCodigoPatrimonio() +
                    " | Modelo: " + ativo.getModelo() +
                    " | Status: " + ativo.getStatus()
                );
            }

            System.out.println(
                "\nTotal de chamados cadastrados: " +
                chamados.size()
            );

            for (ChamadoSuporte chamado : chamados) {
                System.out.println(
                    "ID: " + chamado.getId() +
                    " | Título: " + chamado.getTitulo() +
                    " | Prioridade: " + chamado.getPrioridade() +
                    " | Status: " + chamado.getStatus()
                );
            }

            System.out.println(
                "\n===== SISTEMA EXECUTADO COM SUCESSO ====="
            );

        } catch (Exception e) {
            System.out.println("Erro ao executar o sistema: " +
                e.getMessage());
            e.printStackTrace();

        } finally {
            try {
                banco.fecharConexao();
            } catch (Exception e) {
                System.out.println(
                    "Erro ao fechar conexão: " + e.getMessage()
                );
            }
        }
    }
}
