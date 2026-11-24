
package Techlab.negocio;

import java.time.LocalDateTime;

public class ChamadoSuporte {

    private static int contadorID = 0;

    private int id;
    private String titulo;
    private String descricao;
    private String prioridade;
    private LocalDateTime dataAbertura;
    private AtivoTI ativoRelacionado;

    // Construtor
    public ChamadoSuporte(
            String titulo,
            String descricao,
            String prioridade,
            LocalDateTime dataAbertura,
            AtivoTI ativoRelacionado) {

        this.id = ++contadorID;

        // Valida e atribui diretamente, sem chamar métodos sobrescrevíveis.
        this.titulo = validarTexto(titulo, "Título");
        this.descricao = validarTexto(descricao, "Descrição");
        this.prioridade = validarPrioridade(prioridade);
        this.dataAbertura = validarData(dataAbertura);
        this.ativoRelacionado = validarAtivo(ativoRelacionado);
    }

    // Métodos auxiliares de validação
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

    private static LocalDateTime validarData(LocalDateTime data) {
        if (data == null) {
            throw new IllegalArgumentException("Data de abertura inválida.");
        }
        return data;
    }

    private static AtivoTI validarAtivo(AtivoTI ativo) {
        if (ativo == null) {
            throw new IllegalArgumentException("Ativo relacionado inválido.");
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
               "\nData de abertura: " + dataAbertura +
               "\nAtivo relacionado: " + ativoRelacionado.getModelo();
    }
}

