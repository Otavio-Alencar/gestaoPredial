package fachada;

import negocio.NegocioListaEspera;
import negocio.NegocioMorador;
import negocio.entidade.Morador;
import negocio.entidade.PessoaListaEspera;

import negocio.excecao.ListaDeEsperaException.ListaEsperaVaziaException;
import negocio.excecao.NenhumQuartoLivreException;
import negocio.excecao.ListaDeEsperaException.PessoaNaoEncontradaException;

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
        } catch (ListaEsperaVaziaException | NenhumQuartoLivreException e) {
            System.out.println(e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao cadastrar morador: " + e.getMessage(), e);
        }
    }

    public void removerMorador(String cpf) throws PessoaNaoEncontradaException {
        negocioMorador.removerMorador(cpf);
    }

    public List<Morador> listarMoradores() {
        return negocioMorador.listarMoradores();
    }

    public void adicionarReclamacao(String cpf, String reclamacao) throws PessoaNaoEncontradaException {
        negocioMorador.adicionarReclamacao(cpf, reclamacao);
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
