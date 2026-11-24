

package Techlab.negocio;

import java.time.LocalDateTime;

public class Servidor extends AtivoTI {

    // Atributos específicos do servidor
    private String ipEstatico;
    private String sistemaOperacional;
    private int capacidadeRAM;

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

        this.ipEstatico = ipEstatico;
        this.sistemaOperacional = sistemaOperacional;
        this.capacidadeRAM = capacidadeRAM;
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

        this.ipEstatico = ipEstatico;
        this.sistemaOperacional = sistemaOperacional;
        this.capacidadeRAM = capacidadeRAM;
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
        this.ipEstatico = ipEstatico;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    public void setCapacidadeRAM(int capacidadeRAM) {
        this.capacidadeRAM = capacidadeRAM;
    }

    // Implementação do método abstrato
    @Override
    public double calcularCustoManutencaoMensal() {
        return 0.0; // Valor provisório
    }
}
