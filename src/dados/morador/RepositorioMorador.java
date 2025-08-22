package dados.morador;

import dados.base.RepositorioBase;
import negocio.entidade.Morador;
import java.util.List;

public class RepositorioMorador extends RepositorioBase<Morador> implements IRepositorioMorador {

    @Override
    public void cadastrarMorador(Morador morador) {
        adicionar(morador); // método herdado da base
    }

    @Override
    public void editarMorador(Morador morador) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCpf().equals(morador.getCpf())) {
                lista.set(i, morador);
                return;
            }
        }
        throw new RuntimeException("Morador não encontrado para edição");
    }

    @Override
    public void removerMorador(String cpf) {
        Morador morador = lista.stream()
                .filter(m -> m.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);

        if (morador != null) {
            remover(morador); // chama o método genérico
        } else {
            throw new RuntimeException("Nenhum morador encontrado com CPF: " + cpf);
        }
    }

    @Override
    public void remover(Morador morador) {
        boolean removido = lista.removeIf(m -> m.getCpf().equals(morador.getCpf()));
        if (!removido) {
            throw new RuntimeException("Nenhum morador encontrado com CPF: " + morador.getCpf());
        }
    }

    @Override
    public void adicionarReclamacao(Morador morador, String reclamacao) {
        for (Morador m : lista) {
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
    public List<Morador> listar() {
        return super.listar();
    }
}
