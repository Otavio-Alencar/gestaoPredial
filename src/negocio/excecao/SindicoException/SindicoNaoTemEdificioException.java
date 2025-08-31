package negocio.excecao.SindicoException;

public class SindicoNaoTemEdificioException extends SindicoException {
    public SindicoNaoTemEdificioException() {
        super("< O sindico não tem um edificio cadastrado para ser atualizado. > >");
    }
}
