package negocio.excecao;

public class PessoaNaoEncontrada extends RuntimeException {
    public PessoaNaoEncontrada() {
        super("< Não encontramos essa pessoa na lista >");
    }
}
