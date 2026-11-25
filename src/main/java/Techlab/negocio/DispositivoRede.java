
package Techlab.negocio;

import java.time.LocalDateTime;

public class DispositivoRede extends AtivoTI {

    private int quantidadePortas;

    private static int validarQuantidadePortas(int quantidadePortas) {
        if (quantidadePortas <= 0) {
            throw new IllegalArgumentException(
                "A quantidade de portas deve ser maior que zero."
            );
        }

        return quantidadePortas;
    }

    // Construtor para novos dispositivos de rede
    public DispositivoRede(
            String codigoPatrimonio,
            String modelo,
            LocalDateTime dataAquisicao,
            String status,
            int quantidadePortas) {

        super(codigoPatrimonio, modelo, dataAquisicao, status);

        this.quantidadePortas =
            validarQuantidadePortas(quantidadePortas);
    }

    // Construtor para dispositivos recuperados do banco
    public DispositivoRede(
            int id,
            String codigoPatrimonio,
            String modelo,
            LocalDateTime dataAquisicao,
            String status,
            int quantidadePortas) {

        super(id, codigoPatrimonio, modelo, dataAquisicao, status);

        this.quantidadePortas =
            validarQuantidadePortas(quantidadePortas);
    }

    // Getter
    public int getQuantidadePortas() {
        return quantidadePortas;
    }

    // Setter
    public void setQuantidadePortas(int quantidadePortas) {
        this.quantidadePortas =
            validarQuantidadePortas(quantidadePortas);
    }

    @Override
    public double calcularCustoManutencaoMensal() {
        return 0.0;
    }
}

