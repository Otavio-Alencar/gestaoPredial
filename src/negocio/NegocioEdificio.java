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

/**
 * Classe responsável por gerenciar as regras de negócio relacionadas a um edifício.
 * Implementa o padrão Singleton, garantindo que apenas uma instância de {@code NegocioEdificio} exista.
 *
 * @author Otavio Alencar & Erik Nascimento
 * @version 1.0
 */
public class NegocioEdificio {

    /** Instância única da classe (Singleton). */
    private static NegocioEdificio instancia;

    /** Repositório utilizado para persistência dos dados do edifício. */
    private final IRepositorioEdificio repo;

    /**
     * Construtor privado para o padrão Singleton.
     * Inicializa o repositório de edifícios.
     */
    private NegocioEdificio() {
        repo = RepositorioEdificio.getInstancia(); // Polimorfismo: tratado como interface
    }

    /**
     * Obtém a instância única da classe {@code NegocioEdificio}.
     *
     * @return instância única de {@code NegocioEdificio}.
     */
    public static NegocioEdificio getInstancia() {
        if (instancia == null) {
            instancia = new NegocioEdificio();
        }
        return instancia;
    }

    /**
     * Adiciona um novo edifício ao sistema e associa-o ao síndico logado.
     *
     * @param sindicoLogado o síndico atualmente logado.
     * @param edificio      o edifício a ser adicionado.
     * @throws SindicoNaoLogadoException    se nenhum síndico estiver logado.
     * @throws SindicoJaTemEdificioException se o síndico já possuir um edifício cadastrado.
     */
    public void adicionarEdificio(Sindico sindicoLogado, Edificio edificio)
            throws SindicoJaTemEdificioException, SindicoNaoLogadoException {
        if (sindicoLogado == null) throw new SindicoNaoLogadoException();
        if (sindicoLogado.getEdificio() != null) throw new SindicoJaTemEdificioException();

        repo.adicionarEdificio(edificio);
        sindicoLogado.setEdificio(edificio);
    }

    /**
     * Remove o edifício associado ao síndico logado.
     *
     * @param sindicoLogado o síndico atualmente logado.
     * @throws SindicoNaoLogadoException     se nenhum síndico estiver logado.
     * @throws SindicoNaoTemEdificioException se o síndico não tiver um edifício cadastrado.
     */
    public void removerEdificio(Sindico sindicoLogado)
            throws SindicoNaoLogadoException, SindicoNaoTemEdificioException {
        if (sindicoLogado == null) throw new SindicoNaoLogadoException();
        if (sindicoLogado.getEdificio() == null) throw new SindicoNaoTemEdificioException();

        repo.removerEdificio();
        sindicoLogado.setEdificio(null);
    }

    /**
     * Atualiza os dados do edifício associado ao síndico logado.
     *
     * @param sindicoLogado o síndico atualmente logado.
     * @param novoEdificio  os novos dados do edifício.
     * @throws SindicoNaoLogadoException     se nenhum síndico estiver logado.
     * @throws SindicoNaoTemEdificioException se o síndico não tiver um edifício cadastrado.
     */
    public void atualizarEdificio(Sindico sindicoLogado, Edificio novoEdificio)
            throws SindicoNaoLogadoException, SindicoNaoTemEdificioException {
        if (sindicoLogado == null) throw new SindicoNaoLogadoException();
        if (sindicoLogado.getEdificio() == null) throw new SindicoNaoTemEdificioException();

        repo.atualizarEdificio(novoEdificio);
        sindicoLogado.setEdificio(novoEdificio);
    }

    /**
     * Busca o número do próximo quarto disponível no edifício.
     *
     * @return o número do próximo quarto livre, ou -1 se não houver quartos disponíveis.
     */
    public int buscarProximoQuartoLivre() {
        return repo.buscarProximoQuartoLivre();
    }

    /**
     * Verifica se existe um edifício cadastrado no sistema.
     *
     * @return {@code true} se houver um edifício cadastrado, {@code false} caso contrário.
     */
    public boolean temEdificio() {
        return repo.getEdificio() != null;
    }

    /**
     * Obtém os dados do edifício cadastrado.
     *
     * @return o edifício atual, ou {@code null} se nenhum estiver cadastrado.
     */
    public Edificio getEdificio() {
        return repo.getEdificio();
    }

    /**
     * Preenche um quarto livre com os dados do morador informado.
     *
     * @param morador o morador a ser alocado em um quarto.
     * @throws NenhumQuartoLivreException se não houver quartos disponíveis no edifício.
     */
    public void preencherQuarto(Morador morador) throws NenhumQuartoLivreException {
        if (buscarProximoQuartoLivre() != -1) {
            repo.preencherQuarto(morador);
        } else {
            throw new NenhumQuartoLivreException();
        }
    }

    /**
     * Remove um morador do quarto, liberando-o.
     * Exibe mensagens no console em caso de sucesso ou erro.
     *
     * @param morador o morador a ser removido.
     */
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
