package negocio;

import dados.morador.IRepositorioMorador;
import dados.edificio.RepositorioEdificio;
import negocio.entidade.Morador;
import negocio.entidade.PessoaListaEspera;
import negocio.excecao.ListaEsperaVazia;
import negocio.excecao.NenhumQuartoLivreException;
import negocio.excecao.PessoaNaoEncontrada;
import dados.edificio.IRepositorioEdificio;
import negocio.NegocioListaEspera;
import dados.sindico.SessaoSindico;

import java.util.ArrayList;

public class NegocioMorador {

    private final IRepositorioMorador repoMorador;
    private final IRepositorioEdificio repoEdificio;

    public NegocioMorador(IRepositorioMorador repoMorador) {
        this.repoMorador = repoMorador;
        this.repoEdificio = RepositorioEdificio.getInstancia();
    }

    public void cadastrarMorador(NegocioListaEspera listaEspera) {
        if (repoEdificio.getEdificio() == null) {
            throw new RuntimeException("Nenhum edifício cadastrado para o síndico.");
        }

        int proxQuarto = repoEdificio.buscarProximoQuartoLivre();
        if (proxQuarto == -1) {
            throw new NenhumQuartoLivreException();
        }

        PessoaListaEspera pessoa = listaEspera.verProxima();
        if (pessoa == null) {
            throw new ListaEsperaVazia();
        }

        Morador morador = new Morador(pessoa.getNome(), pessoa.getCpf(), pessoa.getContato());
        repoMorador.cadastrarMorador(morador);
        repoEdificio.preencherQuarto(morador);
        listaEspera.removerPessoa(pessoa.getCpf());
    }

    public void removerMorador(String cpf) {
        ArrayList<Morador> moradores = repoMorador.listar();
        if (moradores.isEmpty()) {
            throw new RuntimeException("Nenhum morador registrado");
        }

        Morador morador = buscarMorador(cpf);
        if (morador == null) {
            throw new PessoaNaoEncontrada();
        }

        // 1) Libera o quarto primeiro
        try {
            repoEdificio.removerDoQuarto(morador);
            System.out.println("Quarto liberado com sucesso!");
        } catch (negocio.excecao.MoradorNaoEncontradoException e) {
            // Se o morador não estava em nenhum quarto, seguimos apenas removendo o cadastro.
            System.out.println("Aviso: " + e.getMessage() + " — prosseguindo com a remoção do cadastro.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro de entrada: " + e.getMessage());
        }

        // 2) Agora remova do repositório de moradores
        repoMorador.removerMorador(cpf);
    }

    public Morador buscarMorador(String cpf) {
        for (Morador m : repoMorador.listar()) {
            if (m.getCpf().equals(cpf)) {
                return m;
            }
        }
        return null;
    }

    public void adicionarReclamacao(String cpf, String reclamacao) {
        Morador morador = buscarMorador(cpf);
        if (morador != null) {
            repoMorador.adicionarReclamacao(morador, reclamacao);
        } else {
            throw new PessoaNaoEncontrada();
        }
    }

    public ArrayList<Morador> listarMoradores() {
        ArrayList<Morador> moradores = repoMorador.listar();
        if (moradores.isEmpty()) {
            throw new RuntimeException("Nenhum morador cadastrado no sistema.");
        }
        return moradores;
    }
}
