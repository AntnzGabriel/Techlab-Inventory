
package Techlab.excecoes;

public class FalhaConexaoBDException extends Exception {

    private static final long serialVersionUID = 1L;

    public FalhaConexaoBDException(String mensagem) {
        super(mensagem);
    }

    public FalhaConexaoBDException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
