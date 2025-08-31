package negocio;

import dados.listaEspera.RepositorioListaEspera;
import dados.persistencia.PersistenciaListaEsperaTXT;
import negocio.entidade.PessoaListaEspera;
import negocio.estrategias.EstrategiaOrdenacao;
import negocio.estrategias.OrdenacaoPorCotasEOrdem;
import negocio.excecao.ListaDeEsperaException.PessoaNaoEncontradaException;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Classe responsável por gerenciar a lógica de negócio da lista de espera.
 * Controla a adição, remoção, ordenação e visualização das pessoas na fila.
 *
 * <p>
 * Utiliza um {@link PriorityQueue} para manter a prioridade da fila
 * com base na {@link EstrategiaOrdenacao} definida.
 * </p>
 *
 * <p>
 * Implementa o padrão Singleton para garantir que exista apenas
 * uma instância ativa da lógica de lista de espera.
 * </p>
 *
 * @author Otavio Alencar & Erik Nascimento
 * @version 1.0
 */
public class NegocioListaEspera {

    private static NegocioListaEspera instancia;

    private final RepositorioListaEspera repositorio;
    private final PriorityQueue<PessoaListaEspera> fila;
    private int contadorOrdem;
    private EstrategiaOrdenacao estrategia;

    /**
     * Construtor privado para implementação do Singleton.
     * Inicializa a fila com base nas pessoas já cadastradas no repositório
     * e define a estratégia padrão de ordenação por cotas e ordem de chegada.
     */
    private NegocioListaEspera() {
        repositorio = RepositorioListaEspera.getInstancia();

        contadorOrdem = repositorio.getPessoas().stream()
                .mapToInt(PessoaListaEspera::getOrdemChegada)
                .max()
                .orElse(0) + 1;

        estrategia = new OrdenacaoPorCotasEOrdem();
        this.fila = new PriorityQueue<>(estrategia.getComparator());
        fila.addAll(repositorio.getPessoas());
    }

    /**
     * Retorna a instância única da classe (Singleton).
     *
     * @return instância de {@code NegocioListaEspera}.
     */
    public static NegocioListaEspera getInstancia() {
        if (instancia == null) {
            instancia = new NegocioListaEspera();
        }
        return instancia;
    }

    /**
     * Adiciona uma nova pessoa à lista de espera.
     *
     * @param nome           Nome completo da pessoa.
     * @param cpf            CPF da pessoa.
     * @param contato        Contato da pessoa.
     * @param ppi            Indica se a pessoa é PPI.
     * @param quilombola     Indica se a pessoa é quilombola.
     * @param pcd            Indica se a pessoa é PCD.
     * @param escolaPublica  Indica se estudou em escola pública.
     * @param baixaRenda     Indica se é de baixa renda.
     */
    public void adicionarPessoa(String nome, String cpf, String contato,
                                boolean ppi, boolean quilombola, boolean pcd,
                                boolean escolaPublica, boolean baixaRenda) {

        PessoaListaEspera pessoa = new PessoaListaEspera(
                nome, cpf, contato, ppi, quilombola, pcd, escolaPublica, baixaRenda, contadorOrdem++
        );

        fila.add(pessoa);
        repositorio.adicionarPessoa(nome, cpf, contato, ppi, quilombola, pcd, escolaPublica, baixaRenda);
    }

    /**
     * Remove e retorna a próxima pessoa da fila, seguindo a prioridade da estratégia.
     *
     * @return pessoa removida da fila, ou {@code null} se a fila estiver vazia.
     */
    public PessoaListaEspera chamarProxima() {
        PessoaListaEspera proxima = fila.poll();
        if (proxima != null) {
            repositorio.removerPessoa(proxima.getCpf());
        }
        return proxima;
    }

    /**
     * Retorna a próxima pessoa da fila sem removê-la.
     *
     * @return próxima pessoa na fila, ou {@code null} se a fila estiver vazia.
     */
    public PessoaListaEspera verProxima() {
        return fila.peek();
    }

    /**
     * Retorna uma lista ordenada com todas as pessoas na fila,
     * respeitando a estratégia atual de ordenação.
     *
     * @return lista ordenada de pessoas na fila.
     */
    public List<PessoaListaEspera> listarFila() {
        return fila.stream()
                .sorted((p1, p2) -> {
                    int diff = Integer.compare(p2.getTotalCotas(), p1.getTotalCotas());
                    if (diff != 0) return diff;
                    return Integer.compare(p1.getOrdemChegada(), p2.getOrdemChegada());
                })
                .toList();
    }

    /**
     * Remove uma pessoa específica da fila com base no CPF.
     *
     * @param cpf CPF da pessoa a ser removida.
     * @throws PessoaNaoEncontradaException se o CPF informado não estiver na fila.
     */
    public void removerPessoa(String cpf) throws PessoaNaoEncontradaException {
        PessoaListaEspera pessoaParaRemover = null;
        for (PessoaListaEspera p : fila) {
            if (p.getCpf().equals(cpf)) {
                pessoaParaRemover = p;
                break;
            }
        }

        if (pessoaParaRemover != null) {
            fila.remove(pessoaParaRemover);
            repositorio.removerPessoa(pessoaParaRemover.getCpf());
        } else {
            throw new PessoaNaoEncontradaException();
        }
    }

    /**
     * Verifica se ainda há pessoas na fila.
     *
     * @return {@code true} se a fila não estiver vazia, caso contrário {@code false}.
     */
    public boolean temPessoas() {
        return !fila.isEmpty();
    }
    /**
     * remove a persistencia de informações da lista de espera
     *
     * @throws IOException se houver algum problema na remoção
     */
    public void removerPersistencia() {
        try {
            String baseDir = System.getProperty("user.dir");
            String caminhoArquivo = baseDir + "/src/dados/listaEspera/lista_espera.txt";
            PersistenciaListaEsperaTXT persistencia = new PersistenciaListaEsperaTXT(caminhoArquivo);
            Files.deleteIfExists(persistencia.getArquivoPath());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao remover persistência da lista de espera.", e);
        }
    }
}
