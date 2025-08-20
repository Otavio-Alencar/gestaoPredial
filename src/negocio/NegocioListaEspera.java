package negocio;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import negocio.entidade.PessoaListaEspera;
import dados.listaEspera.RepositorioListaEspera;
import negocio.excecao.PessoaNaoEncontrada;

public class NegocioListaEspera {
    private RepositorioListaEspera repositorio;
    private PriorityQueue<PessoaListaEspera> fila;
    int contadorOrdem;

    public NegocioListaEspera() {
        repositorio = new RepositorioListaEspera();
        contadorOrdem = 1;

        fila = new PriorityQueue<>(
                (p1, p2) -> {
                    int diff = Integer.compare(p2.getTotalCotas(), p1.getTotalCotas());
                    if (diff != 0) return diff;
                    return Integer.compare(p1.getOrdemChegada(), p2.getOrdemChegada());
                }
        );
    }

    public void adicionarPessoa(String nome, String cpf, String contato,
                                boolean ppi, boolean quilombola, boolean pcd,
                                boolean escolaPublica, boolean baixaRenda) {

        PessoaListaEspera pessoa = new PessoaListaEspera(
                nome, cpf, contato, ppi, quilombola, pcd, escolaPublica, baixaRenda, contadorOrdem++
        );

        fila.add(pessoa);
        repositorio.adicionarPessoa(
                nome, cpf, contato, ppi, quilombola, pcd, escolaPublica, baixaRenda);
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

}
