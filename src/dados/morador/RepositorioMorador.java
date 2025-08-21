package dados.morador;

import negocio.entidade.Morador;
import java.util.ArrayList;

public class RepositorioMorador implements IRepositorioMorador {

    private ArrayList<Morador> listaMoradores = new ArrayList<>();

    @Override
    public void cadastrarMorador(Morador morador) {
        if (morador == null) {
            throw new IllegalArgumentException("Morador não pode ser nulo");
        }
        listaMoradores.add(morador);
    }

    @Override
    public void editarMorador(Morador morador) {
        for (int i = 0; i < listaMoradores.size(); i++) {
            if (listaMoradores.get(i).getCpf().equals(morador.getCpf())) {
                listaMoradores.set(i, morador);
                return;
            }
        }
        throw new RuntimeException("Morador não encontrado para edição");
    }

    @Override
    public void removerMorador(String cpf) {
        boolean removido = listaMoradores.removeIf(m -> m.getCpf().equals(cpf));
        if (!removido) {
            throw new RuntimeException("Nenhum morador encontrado com CPF: " + cpf);
        }
    }



    @Override
    public void adicionarReclamacao(Morador morador,String reclamacao) {
        for (Morador m : listaMoradores) {
            if (m.getCpf().equals(morador.getCpf())) {
                m.adicionarReclamacao(reclamacao);
                if (m.getNumReclamacoes() > 3) {
                    m.setStatus();
                }
                return;
            }
        }
        throw new RuntimeException("Morador não encontrado para adicionar reclamação");
    }

    @Override
    public ArrayList<Morador> listar() {
        return new ArrayList<>(listaMoradores);
    }
}