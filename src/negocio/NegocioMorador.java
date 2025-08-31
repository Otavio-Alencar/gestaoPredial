package negocio;

import dados.edificio.RepositorioEdificio;
import dados.edificio.IRepositorioEdificio;
import negocio.entidade.Morador;
import negocio.entidade.PessoaListaEspera;
import negocio.excecao.ListaDeEsperaException.ListaEsperaVaziaException;
import negocio.excecao.NenhumQuartoLivreException;
import negocio.excecao.ListaDeEsperaException.PessoaNaoEncontradaException;
import negocio.excecao.MoradorNaoEncontradoException;

import java.util.List;

public class NegocioMorador {

    private final IRepositorioEdificio repoEdificio;

    public NegocioMorador() {
        this.repoEdificio = RepositorioEdificio.getInstancia();
    }

    // Cadastra próximo morador da fila no próximo quarto disponível
    public void cadastrarMorador(NegocioListaEspera listaEspera) throws ListaEsperaVaziaException, NenhumQuartoLivreException {
        if (repoEdificio.getEdificio() == null) {
            throw new RuntimeException("Nenhum edifício cadastrado para o síndico.");
        }

        int proxQuarto = repoEdificio.buscarProximoQuartoLivre();
        if (proxQuarto == -1) {
            throw new NenhumQuartoLivreException();
        }

        PessoaListaEspera pessoa = listaEspera.verProxima();
        if (pessoa == null) {
            throw new ListaEsperaVaziaException();
        }

        Morador morador = new Morador(pessoa.getNome(), pessoa.getCpf(), pessoa.getContato());

        repoEdificio.preencherQuarto(morador);

        listaEspera.removerPessoa(pessoa.getCpf());
    }

    // Remove morador do prédio
    public void removerMorador(String cpf) throws PessoaNaoEncontradaException {
        Morador morador = buscarMorador(cpf);
        if (morador == null) {
            throw new PessoaNaoEncontradaException();
        }

        try {
            repoEdificio.removerDoQuarto(morador);
            System.out.println("Quarto liberado com sucesso!");
        } catch (MoradorNaoEncontradoException e) {
            System.out.println("Aviso: " + e.getMessage() + " — prosseguindo com a remoção lógica.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro de entrada: " + e.getMessage());
        }
    }

    // Busca um morador pelo CPF
    public Morador buscarMorador(String cpf) {
        return repoEdificio.getMoradores()
                .stream()
                .filter(m -> m.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }

    // Adiciona uma reclamação a um morador
    public void adicionarReclamacao(String cpf, String reclamacao) throws PessoaNaoEncontradaException {
        Morador morador = buscarMorador(cpf);
        if (morador != null) {
            morador.adicionarReclamacao(reclamacao);
            if(morador.getNumReclamacoes() > 3){
                morador.setStatus();
            }
        } else {
            throw new PessoaNaoEncontradaException();
        }
    }

    // Lista todos os moradores
    public List<Morador> listarMoradores() {
        List<Morador> moradores = repoEdificio.getMoradores();
        if (moradores.isEmpty()) {
            throw new RuntimeException("Nenhum morador cadastrado no sistema.");
        }
        return moradores;
    }
}
