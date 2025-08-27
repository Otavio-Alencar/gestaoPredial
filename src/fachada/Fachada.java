package fachada;

import negocio.NegocioEdificio;
import negocio.NegocioListaEspera;
import negocio.NegocioMorador;
import negocio.entidade.*;
import negocio.relatorio.RelatorioEdificio;

import java.util.List;

public class Fachada {

    private final NegocioEdificio negocioEdificio;
    private final NegocioMorador negocioMorador;
    private final NegocioListaEspera negocioListaEspera;

    public Fachada() {
        this.negocioEdificio = NegocioEdificio.getInstancia();
        this.negocioListaEspera = NegocioListaEspera.getInstancia();
        this.negocioMorador = new NegocioMorador();
    }

    // ==================== EDIFICIO ====================
    public void cadastrarEdificio(Sindico sindicoLogado, Edificio edificio) {
        negocioEdificio.adicionarEdificio(sindicoLogado, edificio);
    }

    public void removerEdificio(Sindico sindicoLogado) {
        negocioEdificio.removerEdificio(sindicoLogado);
    }

    public Edificio getEdificio() {
        return negocioEdificio.getEdificio();
    }

    public boolean temEdificio() {
        return negocioEdificio.getEdificio() != null;
    }

    /**
     * Gera um relatório em DOCX com os dados do edifício atual
     * @param caminhoArquivo caminho completo do arquivo, por exemplo: "relatorios/edificio.docx"
     */
    public void gerarRelatorioEdificio(String caminhoArquivo) {
        Edificio edificio = negocioEdificio.getEdificio();
        if (edificio == null) {
            throw new IllegalStateException("Nenhum edifício cadastrado para gerar relatório.");
        }
        RelatorioEdificio relatorio = new RelatorioEdificio(edificio, caminhoArquivo);
        relatorio.gerar();
    }

    // ==================== MORADOR ====================
    public void cadastrarProximoMorador() {
        negocioMorador.cadastrarMorador(negocioListaEspera);
    }

    public void removerMorador(String cpf) {
        negocioMorador.removerMorador(cpf);
    }

    public Morador buscarMorador(String cpf) {
        return negocioMorador.buscarMorador(cpf);
    }

    public void adicionarReclamacao(String cpf, String reclamacao) {
        negocioMorador.adicionarReclamacao(cpf, reclamacao);
    }

    public List<Morador> listarMoradores() {
        return negocioMorador.listarMoradores();
    }

    // ==================== LISTA DE ESPERA ====================
    public void adicionarPessoaListaEspera(String nome, String cpf, String contato,
                                           boolean ppi, boolean quilombola, boolean pcd,
                                           boolean escolaPublica, boolean baixaRenda) {
        negocioListaEspera.adicionarPessoa(nome, cpf, contato, ppi, quilombola, pcd, escolaPublica, baixaRenda);
    }

    public PessoaListaEspera verProximaPessoaListaEspera() {
        return negocioListaEspera.verProxima();
    }

    public PessoaListaEspera chamarProximaPessoaListaEspera() {
        return negocioListaEspera.chamarProxima();
    }

    public void removerPessoaListaEspera(String cpf) {
        negocioListaEspera.removerPessoa(cpf);
    }

    public List<PessoaListaEspera> listarFilaEspera() {
        return negocioListaEspera.listarFila();
    }

    public boolean temPessoasNaLista() {
        return negocioListaEspera.temPessoas();
    }
}
