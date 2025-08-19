package dados.edificio;

import negocio.entidade.Edificio;


public interface IRepositorioEdificio {
    /**
     * Cadastra um edificio caso o sindico ainda não tenha registrado um.
     * <p>
     * O método verifica se já existe um edificio cadastrado.
     * Caso não exista, o cadastro será realizado.
     *
     * @param edificio Objeto {@link Edificio} contendo as informações do edificio.
     * @throws RuntimeException se já houver um edificio registrado.
     */
    void adicionarEdificio(Edificio edificio);

    void removerEdificio();

    void atualizarEdificio(Edificio edificio);

    int buscarProximoQuartoLivre();
}
