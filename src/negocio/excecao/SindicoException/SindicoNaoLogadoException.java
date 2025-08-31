package negocio.excecao.SindicoException;

public class SindicoNaoLogadoException extends SindicoException {

    public SindicoNaoLogadoException() {
        super("< Para cadastrar um predio é necessário estar logado. >");
    }

}
