package dados.morador;

import negocio.entidade.Morador;

import java.util.ArrayList;


public interface IRepositorioMorador {
    void cadastrarMorador(Morador morador);
    void editarMorador(Morador morador);
    void removerMorador(String cpf);
    ArrayList<Morador> listar();
    void adicionarReclamacao(Morador morador);
}
