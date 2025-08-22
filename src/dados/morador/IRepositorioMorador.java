package dados.morador;

import dados.base.IRepositorioBase;
import negocio.entidade.Morador;
import java.util.List;

public interface IRepositorioMorador extends IRepositorioBase<Morador> {
    void cadastrarMorador(Morador morador);
    void editarMorador(Morador morador);
    void removerMorador(String cpf);
    void adicionarReclamacao(Morador morador, String reclamacao);
    List<Morador> listar();
}