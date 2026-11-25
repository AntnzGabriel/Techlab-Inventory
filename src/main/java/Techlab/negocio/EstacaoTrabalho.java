
package Techlab.negocio;

import java.time.LocalDateTime;

public class EstacaoTrabalho extends AtivoTI {

    private String departamento;
    private String usuarioAlocado;

    private static String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(
                campo + " inválido."
            );
        }

        return valor;
    }

    // Construtor para novas estações de trabalho
    public EstacaoTrabalho(
            String codigoPatrimonio,
            String modelo,
            LocalDateTime dataAquisicao,
            String status,
            String departamento,
            String usuarioAlocado) {

        super(codigoPatrimonio, modelo, dataAquisicao, status);

        this.departamento =
            validarTexto(departamento, "Departamento");

        this.usuarioAlocado =
            validarTexto(usuarioAlocado, "Usuário alocado");
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

        this.departamento =
            validarTexto(departamento, "Departamento");

        this.usuarioAlocado =
            validarTexto(usuarioAlocado, "Usuário alocado");
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
        this.departamento =
            validarTexto(departamento, "Departamento");
    }

    public void setUsuarioAlocado(String usuarioAlocado) {
        this.usuarioAlocado =
            validarTexto(usuarioAlocado, "Usuário alocado");
    }

    @Override
    public double calcularCustoManutencaoMensal() {
        return 0.0;
    }
}
