
package Techlab.negocio;

import java.time.LocalDateTime;

public class Servidor extends AtivoTI {

    private String ipEstatico;
    private String sistemaOperacional;
    private int capacidadeRAM;

    private static String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(
                campo + " inválido."
            );
        }

        return valor;
    }

    private static int validarRAM(int capacidadeRAM) {
        if (capacidadeRAM <= 0) {
            throw new IllegalArgumentException(
                "A capacidade de RAM deve ser maior que zero."
            );
        }

        return capacidadeRAM;
    }

    // Construtor para novos servidores
    public Servidor(
            String codigoPatrimonio,
            String modelo,
            LocalDateTime dataAquisicao,
            String status,
            String ipEstatico,
            String sistemaOperacional,
            int capacidadeRAM) {

        super(codigoPatrimonio, modelo, dataAquisicao, status);

        this.ipEstatico = validarTexto(ipEstatico, "IP estático");
        this.sistemaOperacional =
            validarTexto(sistemaOperacional, "Sistema operacional");
        this.capacidadeRAM = validarRAM(capacidadeRAM);
    }

    // Construtor para servidores recuperados do banco
    public Servidor(
            int id,
            String codigoPatrimonio,
            String modelo,
            LocalDateTime dataAquisicao,
            String status,
            String ipEstatico,
            String sistemaOperacional,
            int capacidadeRAM) {

        super(id, codigoPatrimonio, modelo, dataAquisicao, status);

        this.ipEstatico = validarTexto(ipEstatico, "IP estático");
        this.sistemaOperacional =
            validarTexto(sistemaOperacional, "Sistema operacional");
        this.capacidadeRAM = validarRAM(capacidadeRAM);
    }

    // Getters
    public String getIpEstatico() {
        return ipEstatico;
    }

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }

    public int getCapacidadeRAM() {
        return capacidadeRAM;
    }

    // Setters
    public void setIpEstatico(String ipEstatico) {
        this.ipEstatico = validarTexto(ipEstatico, "IP estático");
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional =
            validarTexto(sistemaOperacional, "Sistema operacional");
    }

    public void setCapacidadeRAM(int capacidadeRAM) {
        this.capacidadeRAM = validarRAM(capacidadeRAM);
    }

    @Override
    public double calcularCustoManutencaoMensal() {
        return 0.0;
    }
}

