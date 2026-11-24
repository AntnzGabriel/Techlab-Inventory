

package Techlab.negocio;

import java.time.LocalDateTime;

public class EstacaoTrabalho extends AtivoTI {

    private String departamento;
    private String usuarioAlocado;

    // Construtor para novas estações de trabalho
    public EstacaoTrabalho(
            String codigoPatrimonio,
            String modelo,
            LocalDateTime dataAquisicao,
            String status,
            String departamento,
            String usuarioAlocado) {

        super(codigoPatrimonio, modelo, dataAquisicao, status);

        this.departamento = departamento;
        this.usuarioAlocado = usuarioAlocado;
    }

    // Construtor para estações recuperadas do banco
    public EstacaoTrabalho(
            int id,
            String codigoPatrimonio,
            String modelo,
            LocalDateTime dataAquisicao,
            String status,
            String departamento,
            String usuarioAlocado) {

        super(id, codigoPatrimonio, modelo, dataAquisicao, status);

        this.departamento = departamento;
        this.usuarioAlocado = usuarioAlocado;
    }

    // Getters
    public String getDepartamento() {
        return departamento;
    }

    public String getUsuarioAlocado() {
        return usuarioAlocado;
    }

    // Setters
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setUsuarioAlocado(String usuarioAlocado) {
        this.usuarioAlocado = usuarioAlocado;
    }

    // Implementação do método abstrato
    @Override
    public double calcularCustoManutencaoMensal() {
        return 0.0; // Valor provisório
    }
}
