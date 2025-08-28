package negocio.excecao;

/**
 * Exceção lançada quando a lista de espera estiver vazia.
 */

public class ListaEsperaVazia extends RuntimeException {
    public ListaEsperaVazia() {
        super("< A lista de espera está vazia. >");
    }
}
