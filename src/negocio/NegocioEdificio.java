package negocio;

import dados.edificio.IRepositorioEdificio;
import negocio.entidade.Edificio;
import negocio.entidade.Sindico;
import negocio.excecao.SindicoJaTemEdificio;
import negocio.excecao.SindicoNaoLogado;
import negocio.excecao.SindicoNaoTemEdificio;

public class NegocioEdificio {

    private final IRepositorioEdificio repo;

    public NegocioEdificio(IRepositorioEdificio repo) {
        this.repo = repo;
    }

    public void adicionarEdificio(Sindico sindicoLogado, Edificio edificio) {
        if (sindicoLogado == null) {
            throw new SindicoNaoLogado();
        }

        if (sindicoLogado.getEdificio() != null) {
            throw new SindicoJaTemEdificio();
        }

        repo.adicionarEdificio(edificio);
        sindicoLogado.setEdificio(edificio);
    }

    public void removerEdificio(Sindico sindicoLogado) {
        if (sindicoLogado == null) {
            throw new SindicoNaoLogado();
        }

        if (sindicoLogado.getEdificio() == null) {
            throw new SindicoNaoTemEdificio();
        }

        repo.removerEdificio();
        sindicoLogado.setEdificio(null);
    }

    public void atualizarEdificio(Sindico sindicoLogado, Edificio novoEdificio) {
        if (sindicoLogado == null) {
            throw new SindicoNaoLogado();
        }

        if (sindicoLogado.getEdificio() == null) {
            throw new SindicoNaoTemEdificio();
        }

        repo.atualizarEdificio(novoEdificio);
        sindicoLogado.setEdificio(novoEdificio);
    }

    public int buscarProximoQuartoLivre() {
        return repo.buscarProximoQuartoLivre();
    }
}
