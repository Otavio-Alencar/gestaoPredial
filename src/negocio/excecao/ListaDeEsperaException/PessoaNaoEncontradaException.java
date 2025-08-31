package negocio.excecao.ListaDeEsperaException;

public class PessoaNaoEncontradaException extends ListaEsperaException {
    public PessoaNaoEncontradaException() {
        super("< Não encontramos essa pessoa na lista >");
    }
}
