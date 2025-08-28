package negocio.excecao;

/**
 * Exceção lançada quando já existe um síndico cadastrado no sistema
 * e uma nova tentativa de cadastro é feita.
 */
public class JaTemSindicoException extends RuntimeException {

    public JaTemSindicoException() {
        super("< Já temos um síndico cadastrado no sistema, faça login. >");
    }

    public JaTemSindicoException(String message) {
        super(message);
    }

    public JaTemSindicoException(String message, Throwable cause) {
        super(message, cause);
    }
}