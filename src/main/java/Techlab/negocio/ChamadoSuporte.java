

package Techlab.negocio;

import java.time.LocalDateTime;

public class ChamadoSuporte {

    private static int contadorID = 0;

    private int id;
    private String titulo;
    private String descricao;
    private String prioridade;
    private String status;
    private LocalDateTime dataAbertura;
    private AtivoTI ativoRelacionado;

    // Construtor para novos chamados
    public ChamadoSuporte(
            String titulo,
            String descricao,
            String prioridade,
            LocalDateTime dataAbertura,
            AtivoTI ativoRelacionado) {

        this(
            contadorID + 1,
            titulo,
            descricao,
            prioridade,
            "Aberto",
            dataAbertura,
            ativoRelacionado
        );
    }

    // Construtor para recuperar chamados do banco
    public ChamadoSuporte(
            int id,
            String titulo,
            String descricao,
            String prioridade,
            String status,
            LocalDateTime dataAbertura,
            AtivoTI ativoRelacionado) {

        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        this.id = id;
        contadorID = Math.max(contadorID, id);

        this.titulo = validarTexto(titulo, "Título");
        this.descricao = validarTexto(descricao, "Descrição");
        this.prioridade = validarPrioridade(prioridade);
        this.status = validarStatus(status);
        this.dataAbertura = validarData(dataAbertura);
        this.ativoRelacionado = validarAtivo(ativoRelacionado);
    }

    private static String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " inválido.");
        }
        return valor;
    }

    private static String validarPrioridade(String prioridade) {
        if (prioridade == null ||
            !(prioridade.equals("Baixa") ||
              prioridade.equals("Media") ||
              prioridade.equals("Alta"))) {
            throw new IllegalArgumentException("Prioridade inválida.");
        }
        return prioridade;
    }

    private static String validarStatus(String status) {
        if (status == null ||
            !(status.equals("Aberto") ||
              status.equals("EmAndamento") ||
              status.equals("Fechado"))) {
            throw new IllegalArgumentException("Status inválido.");
        }
        return status;
    }

    private static LocalDateTime validarData(LocalDateTime data) {
        if (data == null) {
            throw new IllegalArgumentException(
                "Data de abertura inválida."
            );
        }
        return data;
    }

    private static AtivoTI validarAtivo(AtivoTI ativo) {
        if (ativo == null) {
            throw new IllegalArgumentException(
                "Ativo relacionado inválido."
            );
        }
        return ativo;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public AtivoTI getAtivoRelacionado() {
        return ativoRelacionado;
    }

    // Setters
    public void setTitulo(String titulo) {
        this.titulo = validarTexto(titulo, "Título");
    }

    public void setDescricao(String descricao) {
        this.descricao = validarTexto(descricao, "Descrição");
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = validarPrioridade(prioridade);
    }

    public void setStatus(String status) {
        this.status = validarStatus(status);
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = validarData(dataAbertura);
    }

    public void setAtivoRelacionado(AtivoTI ativoRelacionado) {
        this.ativoRelacionado = validarAtivo(ativoRelacionado);
    }

    @Override
    public String toString() {
        return "ID: " + id +
               "\nTítulo: " + titulo +
               "\nDescrição: " + descricao +
               "\nPrioridade: " + prioridade +
               "\nStatus: " + status +
               "\nData de abertura: " + dataAbertura +
               "\nAtivo relacionado: " + ativoRelacionado.getModelo();
    }
}
