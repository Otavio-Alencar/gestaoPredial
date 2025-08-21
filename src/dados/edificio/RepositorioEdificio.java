package dados.edificio;

import negocio.entidade.Edificio;
import negocio.entidade.Morador;
import negocio.entidade.Quarto;
import negocio.enums.StatusQuarto;
import negocio.excecao.MoradorNaoEncontradoException;

public class RepositorioEdificio implements IRepositorioEdificio {
    private Edificio edificio;

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
            return -1;
        }

        for (Quarto q : edificio.getQuartos()) {
            if (q.getStatus() == StatusQuarto.LIVRE) {
                return q.getIdQuarto();
            }
        }
        return -1;
    }

    public void preencherQuarto(Morador morador) {

        Quarto quarto = edificio.getQuartoPorId(buscarProximoQuartoLivre());
        quarto.setStatus(StatusQuarto.OCUPADO);
        quarto.setMorador(morador);


    }
    @Override
    public void removerDoQuarto(Morador morador) throws MoradorNaoEncontradoException {
        if (morador == null) {
            throw new IllegalArgumentException("Morador não pode ser nulo.");
        }

        for (Quarto q : edificio.getQuartos()) {
            if (q.getStatus() == StatusQuarto.OCUPADO && q.getMorador().equals(morador)) {
                q.setMorador(null);
                q.setStatus(StatusQuarto.LIVRE);
                return;
            }
        }

        throw new MoradorNaoEncontradoException("Morador não encontrado em nenhum quarto ocupado.");
    }

    public Edificio getEdificio() {
        return edificio;
    }
}