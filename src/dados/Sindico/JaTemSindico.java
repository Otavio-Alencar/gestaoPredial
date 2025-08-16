package dados.Sindico;

public class JaTemSindico extends RuntimeException {
    public JaTemSindico() {
        super("<Já temos um síndico cadastrado no sistema, faça login. >: ");
    }
}
