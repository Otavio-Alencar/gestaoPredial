package dados.edificio;

import negocio.entidade.Edificio;
import negocio.entidade.Morador;
import negocio.excecao.MoradorNaoEncontradoException;
import negocio.excecao.NenhumQuartoLivreException;

import java.util.List;

public interface IRepositorioEdificio {
    void adicionarEdificio(Edificio e);
    void removerEdificio();
    void atualizarEdificio(Edificio e);
    Edificio getEdificio();
    int buscarProximoQuartoLivre();
    void preencherQuarto(Morador m) throws NenhumQuartoLivreException;
    void removerDoQuarto(Morador m) throws MoradorNaoEncontradoException;
    List<Morador> getMoradores();
}