package fachada;

import negocio.NegocioListaEspera;
import negocio.NegocioMorador;
import negocio.entidade.Morador;
import negocio.entidade.PessoaListaEspera;
import negocio.excecao.ListaEsperaVazia;
import negocio.excecao.NenhumQuartoLivreException;
import negocio.excecao.PessoaNaoEncontrada;

import java.util.List;

public class FachadaMorador {

    private final NegocioMorador negocioMorador;
    private final NegocioListaEspera negocioListaEspera;

    public FachadaMorador() {
        this.negocioMorador = new NegocioMorador();
        this.negocioListaEspera = NegocioListaEspera.getInstancia();
    }

    // ==================== MORADOR ====================

    public void cadastrarProximoMorador() {
        try {
            negocioMorador.cadastrarMorador(negocioListaEspera);
        } catch (ListaEsperaVazia e) {
            throw new RuntimeException("Lista de espera vazia. Não há pessoas para cadastrar.", e);
        } catch (NenhumQuartoLivreException e) {
            throw new RuntimeException("Nenhum quarto disponível no edifício.", e);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao cadastrar morador: " + e.getMessage(), e);
        }
    }

    public void removerMorador(String cpf) {
        negocioMorador.removerMorador(cpf);
    }

    public List<Morador> listarMoradores() {
        return negocioMorador.listarMoradores();
    }

    public void adicionarReclamacao(String cpf, String reclamacao) {
        try {
            negocioMorador.adicionarReclamacao(cpf, reclamacao);
        } catch (PessoaNaoEncontrada e) {
            throw new RuntimeException("Morador não encontrado.", e);
        }
    }

    public Morador buscarMorador(String cpf) {
        return negocioMorador.buscarMorador(cpf);
    }

    // ==================== LISTA DE ESPERA ====================

    public List<PessoaListaEspera> listarFilaEspera() {
        return negocioListaEspera.listarFila();
    }

    public boolean temPessoasNaLista() {
        return negocioListaEspera.temPessoas();
    }
}
