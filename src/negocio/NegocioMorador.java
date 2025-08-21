package negocio;

import dados.morador.IRepositorioMorador;
import negocio.entidade.Morador;
import negocio.entidade.PessoaListaEspera;
import negocio.excecao.ListaEsperaVazia;
import negocio.excecao.NenhumQuartoLivreException;
import negocio.excecao.PessoaNaoEncontrada;

import java.util.ArrayList;

public class NegocioMorador {
    private final IRepositorioMorador repo;

    public NegocioMorador(IRepositorioMorador repo) {
        this.repo = repo;
    }

    public void cadastrarMorador(NegocioEdificio edificio, NegocioListaEspera listaEspera) {
        if (edificio.buscarProximoQuartoLivre() != -1) {
            PessoaListaEspera pessoa = listaEspera.verProxima();
            if (pessoa != null) {
                Morador morador = new Morador(pessoa.getNome(), pessoa.getCpf(), pessoa.getContato());
                repo.cadastrarMorador(morador);
                edificio.preencherQuarto(morador);
                listaEspera.removerPessoa(pessoa.getCpf());
            } else {
                throw new ListaEsperaVazia();
            }
        } else {
            throw new NenhumQuartoLivreException();
        }
    }

    public void removerMorador(NegocioEdificio edificio, String cpf) {
        ArrayList<Morador> moradores = repo.listar();

        if (moradores.isEmpty()) {
            throw new RuntimeException("Nenhum morador registrado");
        }

        Morador morador = buscarMorador(cpf);
        if (morador != null) {
            repo.removerMorador(cpf);
            edificio.removerDoQuarto(morador); // Passa o objeto encontrado ANTES da remoção
        } else {
            throw new PessoaNaoEncontrada();
        }
    }

    public Morador buscarMorador(String cpf) {
        for (Morador m : repo.listar()) {
            if (m.getCpf().equals(cpf)) {
                return m;
            }
        }
        return null;
    }

    public void adicionarReclamacao(String cpf,String reclamacao) {
        Morador morador = buscarMorador(cpf);
        if (morador != null) {
            repo.adicionarReclamacao(morador,reclamacao);
        } else {
            throw new PessoaNaoEncontrada();
        }
    }

    public ArrayList<Morador> listarMoradores() {
        ArrayList<Morador> moradores = repo.listar();
        if (moradores.isEmpty()) {
            throw new RuntimeException("Nenhum morador cadastrado no sistema.");
        }
        return moradores;
    }
}
