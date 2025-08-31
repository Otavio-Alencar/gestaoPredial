package negocio.excecao.SindicoException;

public class SindicoJaTemEdificioException extends SindicoException {
    public SindicoJaTemEdificioException() {
        super("< O sindico ja tem um edificio cadastrado. > >");
    }
}
