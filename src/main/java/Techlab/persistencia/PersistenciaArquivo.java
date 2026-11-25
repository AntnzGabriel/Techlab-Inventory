
package Techlab.persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import Techlab.dto.AtivoDTO;
import Techlab.dto.ChamadoDTO;

public class PersistenciaArquivo {

    private final Path caminhoArquivo;

    public PersistenciaArquivo(String nomeArquivo) {
        if (nomeArquivo == null || nomeArquivo.isBlank()) {
            throw new IllegalArgumentException(
                "Nome do arquivo inválido."
            );
        }

        this.caminhoArquivo = Paths.get(nomeArquivo);
    }

    private String codificar(String valor) {
        if (valor == null) {
            return "";
        }

        return Base64.getEncoder().encodeToString(
            valor.getBytes(StandardCharsets.UTF_8)
        );
    }

    private String decodificar(String valor) {
        if (valor == null || valor.isEmpty()) {
            return "";
        }

        return new String(
            Base64.getDecoder().decode(valor),
            StandardCharsets.UTF_8
        );
    }

    // Salva ativos com os atributos de todas as subclasses
    public void salvarAtivos(List<AtivoDTO> ativos)
            throws IOException {

        if (ativos == null) {
            throw new IllegalArgumentException(
                "A lista de ativos não pode ser nula."
            );
        }

        try (BufferedWriter escritor =
                Files.newBufferedWriter(caminhoArquivo)) {

            for (AtivoDTO ativo : ativos) {

                escritor.write(
                    ativo.getId() + ";" +
                    codificar(ativo.getCodigoPatrimonio()) + ";" +
                    codificar(ativo.getModelo()) + ";" +
                    codificar(String.valueOf(ativo.getDataAquisicao())) + ";" +
                    codificar(ativo.getStatus()) + ";" +
                    codificar(ativo.getTipo()) + ";" +
                    codificar(ativo.getIpEstatico()) + ";" +
                    codificar(ativo.getSistemaOperacional()) + ";" +
                    (ativo.getCapacidadeRAM() == null
                        ? "" : ativo.getCapacidadeRAM()) + ";" +
                    codificar(ativo.getDepartamento()) + ";" +
                    codificar(ativo.getUsuarioAlocado()) + ";" +
                    (ativo.getQuantidadePortas() == null
                        ? "" : ativo.getQuantidadePortas())
                );

                escritor.newLine();
            }
        }
    }

    // Carrega ativos do arquivo TXT
    public List<AtivoDTO> carregarAtivos()
            throws IOException {

        List<AtivoDTO> ativos = new ArrayList<>();

        if (!Files.exists(caminhoArquivo)) {
            return ativos;
        }

        try (BufferedReader leitor =
                Files.newBufferedReader(caminhoArquivo)) {

            String linha;

            while ((linha = leitor.readLine()) != null) {

                String[] dados = linha.split(";", -1);

                if (dados.length != 12) {
                    continue;
                }

                AtivoDTO dto = new AtivoDTO();

                dto.setId(Integer.parseInt(dados[0]));
                dto.setCodigoPatrimonio(decodificar(dados[1]));
                dto.setModelo(decodificar(dados[2]));
                dto.setDataAquisicao(
                    java.time.LocalDateTime.parse(
                        decodificar(dados[3])
                    )
                );
                dto.setStatus(decodificar(dados[4]));
                dto.setTipo(decodificar(dados[5]));
                dto.setIpEstatico(decodificar(dados[6]));
                dto.setSistemaOperacional(decodificar(dados[7]));

                if (!dados[8].isEmpty()) {
                    dto.setCapacidadeRAM(
                        Integer.parseInt(dados[8])
                    );
                }

                dto.setDepartamento(decodificar(dados[9]));
                dto.setUsuarioAlocado(decodificar(dados[10]));

                if (!dados[11].isEmpty()) {
                    dto.setQuantidadePortas(
                        Integer.parseInt(dados[11])
                    );
                }

                ativos.add(dto);
            }
        }

        return ativos;
    }

    // Salva chamados em arquivo TXT
    public void salvarChamados(List<ChamadoDTO> chamados)
            throws IOException {

        if (chamados == null) {
            throw new IllegalArgumentException(
                "A lista de chamados não pode ser nula."
            );
        }

        try (BufferedWriter escritor =
                Files.newBufferedWriter(caminhoArquivo)) {

            for (ChamadoDTO chamado : chamados) {

                escritor.write(
                    chamado.getId() + ";" +
                    codificar(chamado.getTitulo()) + ";" +
                    codificar(chamado.getDescricao()) + ";" +
                    codificar(chamado.getPrioridade()) + ";" +
                    codificar(chamado.getStatus()) + ";" +
                    codificar(String.valueOf(chamado.getDataAbertura())) + ";" +
                    chamado.getAtivoRelacionadoId()
                );

                escritor.newLine();
            }
        }
    }

    // Carrega chamados do arquivo TXT
    public List<ChamadoDTO> carregarChamados()
            throws IOException {

        List<ChamadoDTO> chamados = new ArrayList<>();

        if (!Files.exists(caminhoArquivo)) {
            return chamados;
        }

        try (BufferedReader leitor =
                Files.newBufferedReader(caminhoArquivo)) {

            String linha;

            while ((linha = leitor.readLine()) != null) {

                String[] dados = linha.split(";", -1);

                if (dados.length != 7) {
                    continue;
                }

                ChamadoDTO dto = new ChamadoDTO();

                dto.setId(Integer.parseInt(dados[0]));
                dto.setTitulo(decodificar(dados[1]));
                dto.setDescricao(decodificar(dados[2]));
                dto.setPrioridade(decodificar(dados[3]));
                dto.setStatus(decodificar(dados[4]));
                dto.setDataAbertura(
                    java.time.LocalDateTime.parse(
                        decodificar(dados[5])
                    )
                );
                dto.setAtivoRelacionadoId(
                    Integer.parseInt(dados[6])
                );

                chamados.add(dto);
            }
        }

        return chamados;
    }
}
