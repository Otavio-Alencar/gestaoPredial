package negocio.excecao;

public class SindicoNaoLogado extends RuntimeException {

    public SindicoNaoLogado() {
        super("< Para cadastrar um predio é necessário estar logado. >");
    }

}
