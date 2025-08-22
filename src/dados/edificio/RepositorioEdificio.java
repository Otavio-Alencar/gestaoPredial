package dados.edificio;

import negocio.entidade.Edificio;
import negocio.entidade.Morador;
import negocio.entidade.Quarto;
import negocio.enums.StatusQuarto;
import negocio.excecao.MoradorNaoEncontradoException;
import negocio.excecao.NenhumQuartoLivreException;

public class RepositorioEdificio implements IRepositorioEdificio {
    private static RepositorioEdificio instancia;
    private Edificio edificio;
    private RepositorioEdificio() {}
    public static RepositorioEdificio getInstancia() {
        if (instancia == null) {
            instancia = new RepositorioEdificio();
        }
        return instancia;
    }
    @Override
    public void adicionarEdificio(Edificio novoEdificio) {
        this.edificio = novoEdificio;
    }

    @Override
    public void removerEdificio() {
        this.edificio = null;
    }

    @Override
    public void atualizarEdificio(Edificio novoEdificio) {
        this.edificio = novoEdificio;
    }

    @Override
    public int buscarProximoQuartoLivre() {
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
        return edificio;
    }
}