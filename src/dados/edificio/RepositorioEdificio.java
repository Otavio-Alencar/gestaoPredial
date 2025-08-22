package dados.edificio;

import dados.base.RepositorioBase;
import negocio.entidade.Edificio;
import negocio.entidade.Morador;
import negocio.entidade.Quarto;
import negocio.enums.StatusQuarto;
import negocio.excecao.MoradorNaoEncontradoException;
import negocio.excecao.NenhumQuartoLivreException;

public class RepositorioEdificio extends RepositorioBase<Edificio> implements IRepositorioEdificio {

    private static RepositorioEdificio instancia;

    private RepositorioEdificio() {
        super();
    }

    public static RepositorioEdificio getInstancia() {
        if (instancia == null) {
            instancia = new RepositorioEdificio();
        }
        return instancia;
    }

    @Override
    public void adicionarEdificio(Edificio novoEdificio) {
        lista.clear();  // só pode ter um edifício
        adicionar(novoEdificio);
    }

    @Override
    public void removerEdificio() {
        if (!lista.isEmpty()) {
            lista.clear();
        }
    }

    @Override
    public void remover(Edificio edificio) {
        lista.remove(edificio);
    }

    @Override
    public void atualizarEdificio(Edificio novoEdificio) {
        if (!lista.isEmpty()) {
            lista.set(0, novoEdificio);
        } else {
            adicionar(novoEdificio);
        }
    }

    @Override
    public int buscarProximoQuartoLivre() {
        Edificio edificio = getEdificio();
        if (edificio == null || edificio.getQuartos() == null) {
            throw new IllegalStateException("Nenhum edifício cadastrado.");
        }

        for (int i = 0; i < edificio.getQuartos().size(); i++) {
            if (!edificio.getQuartos().get(i).isOcupado()) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void preencherQuarto(Morador morador) {
        Edificio edificio = getEdificio();
        if (edificio == null) {
            throw new IllegalStateException("Nenhum edifício cadastrado.");
        }

        for (Quarto q : edificio.getQuartos()) {
            if (q.getStatus() == StatusQuarto.LIVRE) {
                q.ocupar(morador);
                return;
            }
        }
        throw new NenhumQuartoLivreException();
    }

    @Override
    public void removerDoQuarto(Morador morador) throws MoradorNaoEncontradoException {
        if (morador == null) {
            throw new IllegalArgumentException("Morador não pode ser nulo.");
        }

        Edificio edificio = getEdificio();
        if (edificio == null) {
            throw new IllegalStateException("Nenhum edifício cadastrado.");
        }

        for (Quarto q : edificio.getQuartos()) {
            if (q.getStatus() == StatusQuarto.OCUPADO && q.getMorador().equals(morador)) {
                q.liberar();
                return;
            }
        }

        throw new MoradorNaoEncontradoException("Morador não encontrado em nenhum quarto ocupado.");
    }

    @Override
    public Edificio getEdificio() {
        return lista.isEmpty() ? null : lista.get(0);
    }
}
