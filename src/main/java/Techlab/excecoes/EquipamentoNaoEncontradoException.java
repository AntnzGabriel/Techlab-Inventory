
package Techlab.excecoes;

public class EquipamentoNaoEncontradoException extends Exception {

    private static final long serialVersionUID = 1L;

    public EquipamentoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
