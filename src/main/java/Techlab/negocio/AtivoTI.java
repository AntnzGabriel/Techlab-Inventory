
package Techlab.negocio;

import java.time.LocalDateTime;

public abstract class AtivoTI {

    private static int contadorID = 0;

    private int id;
    private String codigoPatrimonio;
    private String modelo;
    private LocalDateTime dataAquisicao;
    private String status;

    // Métodos auxiliares de validação

    private static String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " inválido.");
        }

        return valor;
    }

    private static LocalDateTime validarData(LocalDateTime data) {
        if (data == null) {
            throw new IllegalArgumentException(
                "Data de aquisição inválida."
            );
        }

        return data;
    }

    private static String validarStatus(String status) {
        if (status == null ||
            !(status.equals("Ativo") ||
              status.equals("EmManutencao") ||
              status.equals("Descartado"))) {

            throw new IllegalArgumentException("Status inválido.");
        }

        return status;
    }

    // Construtor para novos equipamentos

    public AtivoTI(
            String codigoPatrimonio,
            String modelo,
            LocalDateTime dataAquisicao,
            String status) {

        this.id = ++contadorID;

        this.codigoPatrimonio =
                validarTexto(codigoPatrimonio, "Código de patrimônio");

        this.modelo = validarTexto(modelo, "Modelo");

        this.dataAquisicao = validarData(dataAquisicao);

        this.status = validarStatus(status);
    }

    // Construtor para equipamentos recuperados do banco

    public AtivoTI(
            int id,
            String codigoPatrimonio,
            String modelo,
            LocalDateTime dataAquisicao,
            String status) {

        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        this.id = id;

        if (id > contadorID) {
            contadorID = id;
        }

        this.codigoPatrimonio =
                validarTexto(codigoPatrimonio, "Código de patrimônio");

        this.modelo = validarTexto(modelo, "Modelo");

        this.dataAquisicao = validarData(dataAquisicao);

        this.status = validarStatus(status);
    }

    // Getters

    public int getId() {
        return id;
    }

    public String getCodigoPatrimonio() {
        return codigoPatrimonio;
    }

    public String getModelo() {
        return modelo;
    }

    public LocalDateTime getDataAquisicao() {
        return dataAquisicao;
    }

    public String getStatus() {
        return status;
    }

    // Setters

    public void setCodigoPatrimonio(String codigoPatrimonio) {
        this.codigoPatrimonio =
                validarTexto(codigoPatrimonio, "Código de patrimônio");
    }

    public void setModelo(String modelo) {
        this.modelo = validarTexto(modelo, "Modelo");
    }

    public void setDataAquisicao(LocalDateTime dataAquisicao) {
        this.dataAquisicao = validarData(dataAquisicao);
    }

    public void setStatus(String status) {
        this.status = validarStatus(status);
    }

    // Método abstrato

    public abstract double calcularCustoManutencaoMensal();

    // Representação textual do equipamento

    @Override
    public String toString() {
        return "ID: " + id +
               "\nPatrimônio: " + codigoPatrimonio +
               "\nModelo: " + modelo +
               "\nData de aquisição: " + dataAquisicao +
               "\nStatus: " + status;
    }
}
