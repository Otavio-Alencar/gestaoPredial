package negocio;

import dados.edificio.IRepositorioEdificio;
import dados.edificio.RepositorioEdificio;
import negocio.entidade.Edificio;
import negocio.entidade.Morador;
import negocio.entidade.Sindico;
import negocio.excecao.*;
import negocio.excecao.SindicoException.SindicoJaTemEdificioException;
import negocio.excecao.SindicoException.SindicoNaoLogadoException;
import negocio.excecao.SindicoException.SindicoNaoTemEdificioException;

public class NegocioEdificio {

    private static NegocioEdificio instancia; // singleton do próprio edificio

    private final IRepositorioEdificio repo;

    private NegocioEdificio() {
        repo = RepositorioEdificio.getInstancia(); // Polimorfismo: trata como interface
    }

    public static NegocioEdificio getInstancia() {
        if (instancia == null) {
            instancia = new NegocioEdificio();
        }
        return instancia;
    }

    public void adicionarEdificio(Sindico sindicoLogado, Edificio edificio) throws SindicoJaTemEdificioException, SindicoNaoLogadoException {
        if (sindicoLogado == null) throw new SindicoNaoLogadoException();
        if (sindicoLogado.getEdificio() != null) throw new SindicoJaTemEdificioException();

        repo.adicionarEdificio(edificio);
        sindicoLogado.setEdificio(edificio);
    }

    public void removerEdificio(Sindico sindicoLogado) throws SindicoNaoLogadoException, SindicoNaoTemEdificioException {
        if (sindicoLogado == null) throw new SindicoNaoLogadoException();
        if (sindicoLogado.getEdificio() == null) throw new SindicoNaoTemEdificioException();

        repo.removerEdificio();
        sindicoLogado.setEdificio(null);
    }

    public void atualizarEdificio(Sindico sindicoLogado, Edificio novoEdificio) throws SindicoNaoLogadoException, SindicoNaoTemEdificioException {
        if (sindicoLogado == null) throw new SindicoNaoLogadoException();
        if (sindicoLogado.getEdificio() == null) throw new SindicoNaoTemEdificioException();

        repo.atualizarEdificio(novoEdificio);
        sindicoLogado.setEdificio(novoEdificio);
    }

    public int buscarProximoQuartoLivre() {
        return repo.buscarProximoQuartoLivre();
    }

    public boolean temEdificio() {
        return repo.getEdificio() != null;
    }

    public Edificio getEdificio() {
        return repo.getEdificio();
    }

    public void preencherQuarto(Morador morador) throws NenhumQuartoLivreException {
        if (buscarProximoQuartoLivre() != -1) {
            repo.preencherQuarto(morador);
        } else {
            throw new NenhumQuartoLivreException();
        }
    }

    public void removerDoQuarto(Morador morador) {
        try {
            repo.removerDoQuarto(morador);
            System.out.println("Quarto liberado com sucesso!");
        } catch (MoradorNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro de entrada: " + e.getMessage());
        }
    }
}
