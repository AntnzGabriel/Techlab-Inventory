
package Techlab.dto;

import java.time.LocalDateTime;

public class AtivoDTO {

    private int id;
    private String codigoPatrimonio;
    private String modelo;
    private LocalDateTime dataAquisicao;
    private String status;
    private String tipo;

    // Dados específicos de Servidor
    private String ipEstatico;
    private String sistemaOperacional;
    private Integer capacidadeRAM;

    // Dados específicos de EstacaoTrabalho
    private String departamento;
    private String usuarioAlocado;

    // Dados específicos de DispositivoRede
    private Integer quantidadePortas;

    public AtivoDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoPatrimonio() {
        return codigoPatrimonio;
    }

    public void setCodigoPatrimonio(String codigoPatrimonio) {
        this.codigoPatrimonio = codigoPatrimonio;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public LocalDateTime getDataAquisicao() {
        return dataAquisicao;
    }

    public void setDataAquisicao(LocalDateTime dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIpEstatico() {
        return ipEstatico;
    }

    public void setIpEstatico(String ipEstatico) {
        this.ipEstatico = ipEstatico;
    }

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    public Integer getCapacidadeRAM() {
        return capacidadeRAM;
    }

    public void setCapacidadeRAM(Integer capacidadeRAM) {
        this.capacidadeRAM = capacidadeRAM;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getUsuarioAlocado() {
        return usuarioAlocado;
    }

    public void setUsuarioAlocado(String usuarioAlocado) {
        this.usuarioAlocado = usuarioAlocado;
    }

    public Integer getQuantidadePortas() {
        return quantidadePortas;
    }

    public void setQuantidadePortas(Integer quantidadePortas) {
        this.quantidadePortas = quantidadePortas;
    }
}
