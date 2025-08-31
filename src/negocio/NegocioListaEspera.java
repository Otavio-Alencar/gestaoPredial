package negocio;

import dados.listaEspera.RepositorioListaEspera;
import negocio.entidade.PessoaListaEspera;
import negocio.estrategias.EstrategiaOrdenacao;
import negocio.estrategias.OrdenacaoPorCotasEOrdem;
import negocio.excecao.ListaDeEsperaException.PessoaNaoEncontradaException;

import java.util.List;
import java.util.PriorityQueue;

public class NegocioListaEspera {

    private static NegocioListaEspera instancia;

    private final RepositorioListaEspera repositorio;
    private final PriorityQueue<PessoaListaEspera> fila;
    private int contadorOrdem;
    private EstrategiaOrdenacao estrategia;

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

    public static NegocioListaEspera getInstancia() {
        if (instancia == null) {
            instancia = new NegocioListaEspera();
        }
        return instancia;
    }

    public void adicionarPessoa(String nome, String cpf, String contato,
                                boolean ppi, boolean quilombola, boolean pcd,
                                boolean escolaPublica, boolean baixaRenda) {

        PessoaListaEspera pessoa = new PessoaListaEspera(
                nome, cpf, contato, ppi, quilombola, pcd, escolaPublica, baixaRenda, contadorOrdem++
        );

        fila.add(pessoa);
        repositorio.adicionarPessoa(nome, cpf, contato, ppi, quilombola, pcd, escolaPublica, baixaRenda);
    }

    public PessoaListaEspera chamarProxima() {
        PessoaListaEspera proxima = fila.poll();
        if (proxima != null) {
            repositorio.removerPessoa(proxima.getCpf());
        }
        return proxima;
    }

    public PessoaListaEspera verProxima() {
        return fila.peek();
    }

    public List<PessoaListaEspera> listarFila() {
        return fila.stream()
                .sorted((p1, p2) -> {
                    int diff = Integer.compare(p2.getTotalCotas(), p1.getTotalCotas());
                    if (diff != 0) return diff;
                    return Integer.compare(p1.getOrdemChegada(), p2.getOrdemChegada());
                })
                .toList();
    }

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

    public boolean temPessoas() {
        return !fila.isEmpty();
    }
}
