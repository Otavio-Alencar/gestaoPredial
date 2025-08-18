package dados.listaEspera;

import negocio.entidade.PessoaListaEspera;

import java.util.PriorityQueue;

public class RepositorioListaEspera {

    private PriorityQueue<PessoaListaEspera> fila;
    private int contadorOrdem; // para controlar a ordem de chegada

    public RepositorioListaEspera() {
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
    }


    public  PessoaListaEspera chamarProxima() {
        return fila.poll();
    }


    public PessoaListaEspera verProxima() {
        return fila.peek();
    }


    public void listarFila() {
        fila.stream()
                .sorted((p1, p2) -> {
                    int diff = Integer.compare(p2.getTotalCotas(), p1.getTotalCotas());
                    if (diff != 0) return diff;
                    return Integer.compare(p1.getOrdemChegada(), p2.getOrdemChegada());
                })
                .forEach(p -> System.out.println(
                        p.getNome() + " | Cotas: " + p.getTotalCotas() + " | Ordem: " + p.getOrdemChegada()
                ));
    }


    public int tamanhoFila() {
        return fila.size();
    }

    public void removerPessoa(int ordemChegada) {
        PessoaListaEspera pessoaParaRemover = null;

        for (PessoaListaEspera p : fila) {
            if (p.getOrdemChegada() == ordemChegada) {
                pessoaParaRemover = p;
                break;
            }
        }

        if (pessoaParaRemover != null) {
            fila.remove(pessoaParaRemover);
        } else {
            System.out.println("Pessoa não encontrada na fila.");
        }
    }
}
