package dados.edificio;

import negocio.entidade.Edificio;

public interface IRepositorioEdificio {

    void adicionarEdificio(Edificio edificio);

    void removerEdificio();

    void atualizarEdificio(Edificio edificio);


}
