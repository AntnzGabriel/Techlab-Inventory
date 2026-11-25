
package Techlab.dto;

import java.time.LocalDateTime;

public class ChamadoDTO {

    private int id;
    private String titulo;
    private String descricao;
    private String prioridade;
    private String status;
    private LocalDateTime dataAbertura;
    private int ativoRelacionadoId;

    public ChamadoDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public int getAtivoRelacionadoId() {
        return ativoRelacionadoId;
    }

    public void setAtivoRelacionadoId(int ativoRelacionadoId) {
        this.ativoRelacionadoId = ativoRelacionadoId;
    }
}
