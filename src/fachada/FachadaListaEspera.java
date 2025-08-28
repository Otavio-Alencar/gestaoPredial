package fachada;

import negocio.NegocioListaEspera;
import negocio.entidade.PessoaListaEspera;
import negocio.relatorio.RelatorioListaEspera;

import java.util.List;

public class FachadaListaEspera {

    private final NegocioListaEspera negocioListaEspera;

    public FachadaListaEspera() {
        this.negocioListaEspera = NegocioListaEspera.getInstancia();
    }

    public void adicionarPessoa(String nome, String cpf, String contato,
                                boolean ppi, boolean quilombola, boolean pcd,
                                boolean escolaPublica, boolean baixaRenda) {
        negocioListaEspera.adicionarPessoa(nome, cpf, contato, ppi, quilombola, pcd, escolaPublica, baixaRenda);
    }

    public PessoaListaEspera verProxima() {
        return negocioListaEspera.verProxima();
    }

    public PessoaListaEspera chamarProxima() {
        return negocioListaEspera.chamarProxima();
    }

    public void removerPessoa(String cpf) {
        negocioListaEspera.removerPessoa(cpf);
    }

    public List<PessoaListaEspera> listarFila() {
        return negocioListaEspera.listarFila();
    }

    public boolean temPessoas() {
        return negocioListaEspera.temPessoas();
    }

    public void gerarRelatorioListaEspera(String caminhoArquivo) {
        if (!negocioListaEspera.temPessoas()) {
            throw new IllegalStateException("A lista de espera está vazia. Nenhum relatório a gerar.");
        }
        RelatorioListaEspera relatorio = new RelatorioListaEspera(negocioListaEspera, caminhoArquivo);
        relatorio.gerar();
    }
}
