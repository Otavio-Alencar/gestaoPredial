package dados.morador;

import negocio.entidade.Morador;

import java.util.ArrayList;

public class RepositorioMorador implements IRepositorioMorador {

    private ArrayList<Morador> listaMoradores;
    @Override
    public void cadastrarMorador(Morador morador) {
        listaMoradores.add(morador);
    }

    @Override
    public void editarMorador(Morador morador) {

    }


    @Override
    public void removerMorador(String cpf) {
        listaMoradores.removeIf(morador -> morador.getCpf().equals(cpf));
    }

    @Override
    public ArrayList<Morador> listar() {
        return listaMoradores;
    }
    @Override
    public void adicionarReclamacao(Morador morador) {
        for(Morador m : listaMoradores){
            if(m.getCpf().equals(morador.getCpf())){
                m.adicionarReclamacao();
                if(m.getNumReclamacoes() > 3){
                    m.setStatus();
                }
            }

        }
    }
}
