package dados.Sindico;

import negocio.entidade.Sindico;

public interface IRepositorioLogin {
    void cadastrarSindico(Sindico sindico);
    void removerSindico();
    void alterarSindico();

    Boolean NaoTemSindico();
}
