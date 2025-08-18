package negocio.excecao;

public class SindicoNaoTemEdificio extends RuntimeException {
    public SindicoNaoTemEdificio() {
        super("< O sindico não tem um edificio cadastrado para ser atualizado. > >");
    }
}
