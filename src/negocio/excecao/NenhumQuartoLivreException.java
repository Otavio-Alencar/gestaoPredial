package negocio.excecao;

public class NenhumQuartoLivreException extends RuntimeException {
    public NenhumQuartoLivreException() {
        super("< Nenhum Quarto Livre >");
    }
}
