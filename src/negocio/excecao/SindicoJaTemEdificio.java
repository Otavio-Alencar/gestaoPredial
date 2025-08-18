package negocio.excecao;

public class SindicoJaTemEdificio extends RuntimeException {
    public SindicoJaTemEdificio() {
        super("< O sindico ja tem um edificio cadastrado. > >");
    }
}
