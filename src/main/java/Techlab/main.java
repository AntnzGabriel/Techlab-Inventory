

package Techlab;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import Techlab.db.ConexaoSQLite;
import Techlab.db.CriadorTabelas;
import Techlab.negocio.*;
import Techlab.repositorio.AtivoDAO;
import Techlab.repositorio.ChamadoDAO;

public class main {

    private static final Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {

        ConexaoSQLite banco = new ConexaoSQLite();

        try {
            banco.conectar();

            CriadorTabelas criador = new CriadorTabelas(banco);
            criador.criarTodasTabelas();

            AtivoDAO ativoDAO = new AtivoDAO(banco);
            ChamadoDAO chamadoDAO = new ChamadoDAO(banco);

            // Sincroniza os contadores de IDs com os registros existentes.
            ativoDAO.listarTodos();
            chamadoDAO.listarTodos();

            int opcao;

            do {
                System.out.println("\n===== TECHLAB INVENTORY =====");
                System.out.println("1 - Cadastrar ativo");
                System.out.println("2 - Listar ativos");
                System.out.println("3 - Atualizar ativo");
                System.out.println("4 - Excluir ativo");
                System.out.println("5 - Cadastrar chamado");
                System.out.println("6 - Listar chamados");
                System.out.println("7 - Atualizar chamado");
                System.out.println("8 - Excluir chamado");
                System.out.println("0 - Sair");

                opcao = lerInt("Escolha uma opção: ");

                try {
                    switch (opcao) {
                        case 1 -> cadastrarAtivo(ativoDAO);
                        case 2 -> listarAtivos(ativoDAO);
                        case 3 -> atualizarAtivo(ativoDAO);
                        case 4 -> excluirAtivo(ativoDAO);
                        case 5 -> cadastrarChamado(ativoDAO, chamadoDAO);
                        case 6 -> listarChamados(chamadoDAO);
                        case 7 -> atualizarChamado(ativoDAO, chamadoDAO);
                        case 8 -> excluirChamado(chamadoDAO);
                        case 0 -> System.out.println("Encerrando sistema...");
                        default -> System.out.println("Opção inválida!");
                    }

                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());

                    if (e.getCause() != null) {
                        System.out.println(
                            "Causa original: " + e.getCause().getMessage()
                        );
                    }

                    e.printStackTrace();
                }

            } while (opcao != 0);

        } catch (Exception e) {
            System.out.println(
                "Erro ao executar o sistema: " + e.getMessage()
            );

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

    // Remove espaços extras das entradas do usuário.
    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return entrada.nextLine().trim();
    }

    private static int lerInt(String mensagem) {
        while (true) {
            try {
                return Integer.parseInt(lerTexto(mensagem));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }

    private static LocalDateTime lerData() {
        return LocalDateTime.parse(
            lerTexto("Data de aquisição (AAAA-MM-DDTHH:MM): ")
        );
    }

    // CADASTRAR ATIVO
    private static void cadastrarAtivo(AtivoDAO dao) throws Exception {

        System.out.println("\nTIPO DE ATIVO");
        System.out.println("1 - Servidor");
        System.out.println("2 - Estação de trabalho");
        System.out.println("3 - Dispositivo de rede");

        int tipo = lerInt("Escolha: ");

        String patrimonio = lerTexto("Código do patrimônio: ");
        String modelo = lerTexto("Modelo: ");
        LocalDateTime data = lerData();

        String status = lerTexto(
            "Status (Ativo, EmManutencao, Descartado): "
        );

        AtivoTI ativo;

        switch (tipo) {
            case 1 -> {
                String ip = lerTexto("IP estático: ");
                String sistema = lerTexto("Sistema operacional: ");
                int ram = lerInt("Capacidade de RAM (GB): ");

                ativo = new Servidor(
                    patrimonio, modelo, data, status, ip, sistema, ram
                );
            }

            case 2 -> {
                String departamento = lerTexto("Departamento: ");
                String usuario = lerTexto("Usuário alocado: ");

                ativo = new EstacaoTrabalho(
                    patrimonio, modelo, data, status, departamento, usuario
                );
            }

            case 3 -> {
                int portas = lerInt("Quantidade de portas: ");

                ativo = new DispositivoRede(
                    patrimonio, modelo, data, status, portas
                );
            }

            default -> {
                System.out.println("Tipo inválido!");
                return;
            }
        }

        dao.salvar(ativo);

        System.out.println("Ativo cadastrado com sucesso!");
    }

    // LISTAR ATIVOS
    private static void listarAtivos(AtivoDAO dao) throws Exception {

        List<AtivoTI> ativos = dao.listarTodos();

        System.out.println("\n===== ATIVOS CADASTRADOS =====");

        if (ativos.isEmpty()) {
            System.out.println("Nenhum ativo cadastrado.");
            return;
        }

        for (AtivoTI ativo : ativos) {
            System.out.println(ativo);
        }
    }

    // ATUALIZAR ATIVO
    private static void atualizarAtivo(AtivoDAO dao) throws Exception {

        int id = lerInt("ID do ativo: ");
        AtivoTI ativo = dao.buscarPorId(id);

        ativo.setCodigoPatrimonio(
            lerTexto("Novo patrimônio: ")
        );

        ativo.setModelo(lerTexto("Novo modelo: "));
        ativo.setDataAquisicao(lerData());

        ativo.setStatus(
            lerTexto("Novo status (Ativo, EmManutencao, Descartado): ")
        );

        if (ativo instanceof Servidor servidor) {

            servidor.setIpEstatico(lerTexto("Novo IP: "));

            servidor.setSistemaOperacional(
                lerTexto("Novo sistema operacional: ")
            );

            servidor.setCapacidadeRAM(
                lerInt("Nova RAM (GB): ")
            );

        } else if (ativo instanceof EstacaoTrabalho estacao) {

            estacao.setDepartamento(
                lerTexto("Novo departamento: ")
            );

            estacao.setUsuarioAlocado(
                lerTexto("Novo usuário: ")
            );

        } else if (ativo instanceof DispositivoRede dispositivo) {

            dispositivo.setQuantidadePortas(
                lerInt("Nova quantidade de portas: ")
            );
        }

        dao.atualizar(ativo);

        System.out.println("Ativo atualizado com sucesso!");
    }

    // EXCLUIR ATIVO
    private static void excluirAtivo(AtivoDAO dao) throws Exception {

        int id = lerInt("ID do ativo que deseja excluir: ");

        dao.deletar(id);

        System.out.println("Ativo excluído com sucesso!");
    }

    // CADASTRAR CHAMADO
    private static void cadastrarChamado(
            AtivoDAO ativoDAO,
            ChamadoDAO chamadoDAO) throws Exception {

        listarAtivos(ativoDAO);

        int idAtivo = lerInt("ID do ativo relacionado: ");
        AtivoTI ativo = ativoDAO.buscarPorId(idAtivo);

        String titulo = lerTexto("Título: ");
        String descricao = lerTexto("Descrição: ");

        String prioridade = lerTexto(
            "Prioridade (Baixa, Media, Alta): "
        );

        ChamadoSuporte chamado = new ChamadoSuporte(
            titulo,
            descricao,
            prioridade,
            LocalDateTime.now(),
            ativo
        );

        chamadoDAO.salvar(chamado);

        System.out.println("Chamado cadastrado com sucesso!");
    }

    // LISTAR CHAMADOS
    private static void listarChamados(ChamadoDAO dao) throws Exception {

        List<ChamadoSuporte> chamados = dao.listarTodos();

        System.out.println("\n===== CHAMADOS CADASTRADOS =====");

        if (chamados.isEmpty()) {
            System.out.println("Nenhum chamado cadastrado.");
            return;
        }

        for (ChamadoSuporte chamado : chamados) {
            System.out.println(chamado);
        }
    }

    // ATUALIZAR CHAMADO
    private static void atualizarChamado(
            AtivoDAO ativoDAO,
            ChamadoDAO chamadoDAO) throws Exception {

        int id = lerInt("ID do chamado: ");

        ChamadoSuporte antigo = chamadoDAO.buscarPorId(id);

        String titulo = lerTexto("Novo título: ");
        String descricao = lerTexto("Nova descrição: ");

        String prioridade = lerTexto(
            "Prioridade (Baixa, Media, Alta): "
        );

        String status = lerTexto(
            "Status (Aberto, EmAndamento, Fechado): "
        );

        listarAtivos(ativoDAO);

        int idAtivo = lerInt("Novo ID do ativo relacionado: ");
        AtivoTI ativo = ativoDAO.buscarPorId(idAtivo);

        ChamadoSuporte atualizado = new ChamadoSuporte(
            antigo.getId(),
            titulo,
            descricao,
            prioridade,
            status,
            antigo.getDataAbertura(),
            ativo
        );

        chamadoDAO.atualizar(atualizado);

        System.out.println("Chamado atualizado com sucesso!");
    }

    // EXCLUIR CHAMADO
    private static void excluirChamado(ChamadoDAO dao) throws Exception {

        int id = lerInt("ID do chamado que deseja excluir: ");

        dao.deletar(id);

        System.out.println("Chamado excluído com sucesso!");
    }
}
