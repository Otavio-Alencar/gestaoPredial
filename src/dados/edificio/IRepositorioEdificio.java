package dados.edificio;

import dados.base.IRepositorioBase;
import negocio.entidade.Edificio;
import negocio.entidade.Morador;
import negocio.excecao.MoradorNaoEncontradoException;

public interface IRepositorioEdificio extends IRepositorioBase<Edificio> {
    void adicionarEdificio(Edificio edificio);
    void removerEdificio();
    void atualizarEdificio(Edificio edificio);

    int buscarProximoQuartoLivre();
    void preencherQuarto(Morador morador);
    void removerDoQuarto(Morador morador) throws MoradorNaoEncontradoException;
    Edificio getEdificio();
}