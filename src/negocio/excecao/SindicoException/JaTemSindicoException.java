package negocio.excecao.SindicoException;

/**
 * Exceção lançada quando já existe um síndico cadastrado no sistema
 * e uma nova tentativa de cadastro é feita.
 */
public class JaTemSindicoException extends SindicoException {

    public JaTemSindicoException() {
        super("< Já temos um síndico cadastrado no sistema, faça login. >");
    }

}