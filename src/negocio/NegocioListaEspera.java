package negocio;

import dados.listaEspera.RepositorioListaEspera;
import negocio.entidade.PessoaListaEspera;
import negocio.excecao.PessoaNaoEncontrada;

import java.util.List;
import java.util.PriorityQueue;

public class NegocioListaEspera {

    private static NegocioListaEspera instancia;

    private final RepositorioListaEspera repositorio;
    private final PriorityQueue<PessoaListaEspera> fila;
    private int contadorOrdem;

    private NegocioListaEspera() {
        repositorio = RepositorioListaEspera.getInstancia();

        // Inicializa contador baseado na última pessoa persistida
        contadorOrdem = repositorio.getPessoas().stream()
                .mapToInt(PessoaListaEspera::getOrdemChegada)
                .max()
                .orElse(0) + 1;

        // Inicializa fila com ordenação por total de cotas e ordem de chegada
        fila = new PriorityQueue<>(
                (p1, p2) -> {
                    int diff = Integer.compare(p2.getTotalCotas(), p1.getTotalCotas());
                    if (diff != 0) return diff;
                    return Integer.compare(p1.getOrdemChegada(), p2.getOrdemChegada());
                }
        );

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

    public void removerPessoa(String cpf) {
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
            throw new PessoaNaoEncontrada();
        }
    }

    public boolean temPessoas() {
        return !fila.isEmpty();
    }
}
