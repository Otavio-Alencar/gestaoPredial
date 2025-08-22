package negocio;

import dados.edificio.IRepositorioEdificio;
import dados.edificio.RepositorioEdificio;
import negocio.entidade.Edificio;
import negocio.entidade.Morador;
import negocio.entidade.Sindico;
import negocio.excecao.*;

public class NegocioEdificio {

    private static NegocioEdificio instancia; // atributo estático para o singleton
    private final IRepositorioEdificio repo;

    // Construtor privado para impedir múltiplas instâncias
    private NegocioEdificio() {
        this.repo = RepositorioEdificio.getInstancia();
    }

    // Método público para acessar a única instância
    public static NegocioEdificio getInstancia() {
        if (instancia == null) {
            instancia = new NegocioEdificio();
        }
        return instancia;
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

    public boolean temEdificio(Sindico sindicoLogado) {
        return sindicoLogado.getEdificio() != null;
    }

    public Edificio getEdificio() {
        return repo.getEdificio();
    }

    public void preencherQuarto(Morador morador) {
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
