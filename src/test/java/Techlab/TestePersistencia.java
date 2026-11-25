package Techlab;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import Techlab.conversores.AtivoConversor;
import Techlab.conversores.ChamadoConversor;
import Techlab.dto.AtivoDTO;
import Techlab.dto.ChamadoDTO;
import Techlab.negocio.AtivoTI;
import Techlab.negocio.Servidor;
import Techlab.negocio.ChamadoSuporte;
import Techlab.persistencia.PersistenciaArquivo;

public class TestePersistencia {

    public static void main(String[] args) {

        String arquivoAtivos = "ativos_teste.txt";
        String arquivoChamados = "chamados_teste.txt";

        try {
            PersistenciaArquivo persistenciaAtivos =
                new PersistenciaArquivo(arquivoAtivos);

            PersistenciaArquivo persistenciaChamados =
                new PersistenciaArquivo(arquivoChamados);

            // 1. Teste de ativo nulo
            try {
                persistenciaAtivos.salvarAtivos(
                    Arrays.asList((AtivoDTO) null)
                );

                throw new AssertionError(
                    "O sistema aceitou um ativo nulo."
                );

            } catch (IOException e) {
                System.out.println(
                    "TESTE DE ATIVO NULO: APROVADO!"
                );
            }

            // 2. Teste de data nula em ativo
            AtivoDTO ativoSemData = new AtivoDTO();
            ativoSemData.setId(2);

            try {
                persistenciaAtivos.salvarAtivos(
                    List.of(ativoSemData)
                );

                throw new AssertionError(
                    "O sistema aceitou um ativo sem data."
                );

            } catch (IOException e) {
                System.out.println(
                    "TESTE DE DATA NULA EM ATIVO: APROVADO!"
                );
            }

            // 3. Teste de gravação e leitura de ativo
            AtivoDTO ativo = new AtivoDTO();

            ativo.setId(1);
            ativo.setCodigoPatrimonio("PAT-001");
            ativo.setModelo("Servidor Dell");
            ativo.setDataAquisicao(
                LocalDateTime.of(2026, 11, 25, 10, 0)
            );
            ativo.setStatus("Ativo");
            ativo.setTipo("SERVIDOR");
            ativo.setIpEstatico("192.168.0.10");
            ativo.setSistemaOperacional("Linux");
            ativo.setCapacidadeRAM(32);

            persistenciaAtivos.salvarAtivos(List.of(ativo));

            List<AtivoDTO> ativosRecuperados =
                persistenciaAtivos.carregarAtivos();

            if (ativosRecuperados.size() != 1
                    || !"PAT-001".equals(
                        ativosRecuperados.get(0).getCodigoPatrimonio())
                    || !"Servidor Dell".equals(
                        ativosRecuperados.get(0).getModelo())
                    || ativosRecuperados.get(0).getCapacidadeRAM() != 32) {

                throw new AssertionError(
                    "Falha na leitura de ativo."
                );
            }

            System.out.println(
                "TESTE DE GRAVAÇÃO E LEITURA DE ATIVOS: APROVADO!"
            );

            // 4. Teste de gravação e leitura de chamado
            ChamadoDTO chamado = new ChamadoDTO();

            chamado.setId(1);
            chamado.setTitulo("Computador com lentidão");
            chamado.setDescricao(
                "Verificar memória e desempenho."
            );
            chamado.setPrioridade("Alta");
            chamado.setStatus("Aberto");
            chamado.setDataAbertura(
                LocalDateTime.of(2026, 11, 25, 10, 30)
            );
            chamado.setAtivoRelacionadoId(1);

            persistenciaChamados.salvarChamados(List.of(chamado));

            List<ChamadoDTO> chamadosRecuperados =
                persistenciaChamados.carregarChamados();

            if (chamadosRecuperados.size() != 1
                    || !"Computador com lentidão".equals(
                        chamadosRecuperados.get(0).getTitulo())
                    || !"Alta".equals(
                        chamadosRecuperados.get(0).getPrioridade())
                    || chamadosRecuperados.get(0)
                        .getAtivoRelacionadoId() != 1) {

                throw new AssertionError(
                    "Falha na leitura de chamado."
                );
            }

            System.out.println(
                "TESTE DE GRAVAÇÃO E LEITURA DE CHAMADOS: APROVADO!"
            );

            // 5. Teste de Base64 inválido
            Files.writeString(
                Paths.get(arquivoAtivos),
                "1;@@@;abc;abc;abc;abc;abc;abc;32;abc;abc;4",
                StandardCharsets.UTF_8
            );

            try {
                persistenciaAtivos.carregarAtivos();

                throw new AssertionError(
                    "O sistema aceitou Base64 inválido."
                );

            } catch (IOException e) {
                if (!e.getMessage().contains("linha 1")) {
                    throw new AssertionError(
                        "Mensagem sem número da linha."
                    );
                }

                System.out.println(
                    "TESTE DE BASE64 INVÁLIDO: APROVADO!"
                );
            }

            // 6. Teste de data inválida
            Files.writeString(
                Paths.get(arquivoAtivos),
                "1;UEFULTA==;U2Vydmlkb3I=;ZGF0YS1pbnZhbGlk;QXRpdm8=;U0VSVklET1I=;;;;;;",
                StandardCharsets.UTF_8
            );

            try {
                persistenciaAtivos.carregarAtivos();

                throw new AssertionError(
                    "O sistema aceitou uma data inválida."
                );

            } catch (IOException e) {
                if (!e.getMessage().contains("linha 1")) {
                    throw new AssertionError(
                        "Mensagem sem número da linha."
                    );
                }

                System.out.println(
                    "TESTE DE DATA INVÁLIDA: APROVADO!"
                );
            }

            // 7. Teste de quantidade incorreta de campos
            Files.writeString(
                Paths.get(arquivoAtivos),
                "1;campo;incompleto",
                StandardCharsets.UTF_8
            );

            try {
                persistenciaAtivos.carregarAtivos();

                throw new AssertionError(
                    "O sistema aceitou campos incompletos."
                );

            } catch (IOException e) {
                if (!e.getMessage().contains("linha 1")) {
                    throw new AssertionError(
                        "Mensagem sem número da linha."
                    );
                }

                System.out.println(
                    "TESTE DE QUANTIDADE DE CAMPOS: APROVADO!"
                );
            }

            // 8. Teste de conversão de ativo
            AtivoTI servidor = new Servidor(
                "PAT-CONV-01",
                "Dell PowerEdge",
                LocalDateTime.of(2026, 1, 10, 9, 0),
                "Ativo",
                "192.168.0.20",
                "Linux",
                64
            );

            AtivoConversor ativoConversor =
                new AtivoConversor();

            AtivoDTO servidorDTO =
                ativoConversor.paraDTO(servidor);

            AtivoTI servidorConvertido =
                ativoConversor.paraEntidade(servidorDTO);

            if (!(servidorConvertido instanceof Servidor)
                    || !servidor.getCodigoPatrimonio().equals(
                        servidorConvertido.getCodigoPatrimonio())
                    || !servidor.getModelo().equals(
                        servidorConvertido.getModelo())) {

                throw new AssertionError(
                    "Falha na conversão de ativo."
                );
            }

            System.out.println(
                "TESTE DE CONVERSÃO DE ATIVO: APROVADO!"
            );

            // 9. Teste de conversão de chamado
            ChamadoSuporte chamadoOriginal =
                new ChamadoSuporte(
                    "Falha no servidor",
                    "Servidor não responde.",
                    "Alta",
                    LocalDateTime.of(2026, 11, 25, 10, 45),
                    servidor
                );

            ChamadoConversor chamadoConversor =
                new ChamadoConversor();

            ChamadoDTO chamadoDTO =
                chamadoConversor.paraDTO(chamadoOriginal);

            ChamadoSuporte chamadoConvertido =
                chamadoConversor.paraEntidade(
                    chamadoDTO,
                    servidorConvertido
                );

            if (!chamadoOriginal.getTitulo().equals(
                    chamadoConvertido.getTitulo())
                    || chamadoOriginal.getAtivoRelacionado().getId()
                    != chamadoConvertido.getAtivoRelacionado().getId()) {

                throw new AssertionError(
                    "Falha na conversão de chamado."
                );
            }

            System.out.println(
                "TESTE DE CONVERSÃO DE CHAMADO: APROVADO!"
            );

            System.out.println(
                "\nTODOS OS TESTES FORAM APROVADOS!"
            );

        } catch (Exception e) {

            System.out.println(
                "\nFALHA NO TESTE: " + e.getMessage()
            );

            e.printStackTrace();

        } finally {

            try {
                Files.deleteIfExists(Paths.get(arquivoAtivos));
                Files.deleteIfExists(Paths.get(arquivoChamados));

            } catch (IOException e) {
                System.out.println(
                    "Erro ao remover arquivos temporários."
                );
            }
        }
    }
}
