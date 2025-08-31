package negocio;

import dados.edificio.RepositorioEdificio;
import dados.edificio.IRepositorioEdificio;
import negocio.entidade.Morador;
import negocio.entidade.PessoaListaEspera;
import negocio.excecao.ListaDeEsperaException.ListaEsperaVaziaException;
import negocio.excecao.NenhumQuartoLivreException;
import negocio.excecao.ListaDeEsperaException.PessoaNaoEncontradaException;
import negocio.excecao.MoradorNaoEncontradoException;

import java.util.List;

/**
 * Classe de regras de negócio responsável por gerenciar os moradores
 * do edifício, incluindo cadastro, remoção, busca, registro de reclamações
 * e listagem.
 *
 * Essa classe interage diretamente com o repositório de edifícios
 * para manipular os dados dos moradores.
 *
 * @author Otavio Alencar & Erik Nascimento
 * @version 1.0
 */
public class NegocioMorador {

    /** Repositório responsável por armazenar dados do edifício. */
    private final IRepositorioEdificio repoEdificio;

    /**
     * Construtor que inicializa o repositório de edifícios.
     */
    public NegocioMorador() {
        this.repoEdificio = RepositorioEdificio.getInstancia();
    }

    /**
     * Cadastra o próximo morador disponível na lista de espera
     * no próximo quarto livre do edifício.
     *
     * @param listaEspera Objeto que gerencia a fila de espera de moradores.
     * @throws ListaEsperaVaziaException Caso a lista de espera esteja vazia.
     * @throws NenhumQuartoLivreException Caso não haja quartos disponíveis.
     * @throws PessoaNaoEncontradaException Caso a pessoa não possa ser encontrada na lista.
     * @throws RuntimeException Caso não exista um edifício cadastrado.
     */
    public void cadastrarMorador(NegocioListaEspera listaEspera)
            throws ListaEsperaVaziaException, NenhumQuartoLivreException, PessoaNaoEncontradaException {
        if (repoEdificio.getEdificio() == null) {
            throw new RuntimeException("Nenhum edifício cadastrado para o síndico.");
        }

        int proxQuarto = repoEdificio.buscarProximoQuartoLivre();
        if (proxQuarto == -1) {
            throw new NenhumQuartoLivreException();
        }

        PessoaListaEspera pessoa = listaEspera.verProxima();
        if (pessoa == null) {
            throw new ListaEsperaVaziaException();
        }

        Morador morador = new Morador(pessoa.getNome(), pessoa.getCpf(), pessoa.getContato());

        repoEdificio.preencherQuarto(morador);

        listaEspera.removerPessoa(pessoa.getCpf());
    }

    /**
     * Remove um morador do edifício pelo CPF.
     *
     * @param cpf CPF do morador a ser removido.
     * @throws PessoaNaoEncontradaException Caso não exista um morador com o CPF informado.
     */
    public void removerMorador(String cpf) throws PessoaNaoEncontradaException {
        Morador morador = buscarMorador(cpf);
        if (morador == null) {
            throw new PessoaNaoEncontradaException();
        }

        try {
            repoEdificio.removerDoQuarto(morador);
            System.out.println("Quarto liberado com sucesso!");
        } catch (MoradorNaoEncontradoException e) {
            System.out.println("Aviso: " + e.getMessage() + " — prosseguindo com a remoção lógica.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro de entrada: " + e.getMessage());
        }
    }

    /**
     * Busca um morador pelo CPF.
     *
     * @param cpf CPF do morador a ser buscado.
     * @return Objeto {@link Morador} correspondente, ou {@code null} se não encontrado.
     */
    public Morador buscarMorador(String cpf) {
        return repoEdificio.getMoradores()
                .stream()
                .filter(m -> m.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }

    /**
     * Adiciona uma reclamação a um morador específico pelo CPF.
     * Caso o número de reclamações ultrapasse 3, o status do morador é alterado.
     *
     * @param cpf CPF do morador.
     * @param reclamacao Descrição da reclamação.
     * @throws PessoaNaoEncontradaException Caso o morador não seja encontrado.
     */
    public void adicionarReclamacao(String cpf, String reclamacao) throws PessoaNaoEncontradaException {
        Morador morador = buscarMorador(cpf);
        if (morador != null) {
            morador.adicionarReclamacao(reclamacao);
            if(morador.getNumReclamacoes() > 3){
                morador.setStatus();
            }
        } else {
            throw new PessoaNaoEncontradaException();
        }
    }

    /**
     * Lista todos os moradores cadastrados no edifício.
     *
     * @return Lista de objetos {@link Morador}.
     * @throws RuntimeException Caso não existam moradores cadastrados.
     */
    public List<Morador> listarMoradores() {
        List<Morador> moradores = repoEdificio.getMoradores();
        if (moradores.isEmpty()) {
            throw new RuntimeException("Nenhum morador cadastrado no sistema.");
        }
        return moradores;
    }
}
