package negocio;

import dados.edificio.IRepositorioEdificio;
import dados.morador.IRepositorioMorador;
import dados.morador.RepositorioMorador;
import negocio.entidade.Edificio;
import negocio.entidade.ListaEspera;
import negocio.entidade.Morador;
import negocio.entidade.PessoaListaEspera;
import negocio.excecao.ListaEsperaVazia;
import negocio.excecao.NenhumQuartoLivreException;
import negocio.excecao.PessoaNaoEncontrada;

import java.util.ArrayList;
import java.util.List;

public class NegocioMorador {
    private final IRepositorioMorador repo;

    public NegocioMorador(IRepositorioMorador repo) {
        this.repo = repo;
    }


    public void cadastrarMorador(NegocioEdificio edificio,NegocioListaEspera listaEspera) {
        if(edificio.buscarProximoQuartoLivre() != 1){
            if(listaEspera.verProxima() != null){
                PessoaListaEspera pessoa = listaEspera.verProxima();
                Morador morador = new Morador(pessoa.getNome(), pessoa.getCpf(), pessoa.getContato());
                repo.cadastrarMorador(morador);
                edificio.preencherQuarto(morador);
            }else{
                throw new ListaEsperaVazia();
            }
        }else{
            throw new NenhumQuartoLivreException();
        }
    }

    public void removerMorador(String cpf) {
        ArrayList<Morador> moradores = repo.listar();

        if(moradores.size() > 0){

            if(buscarMorador(cpf) != null){
                repo.removerMorador(cpf);
            }else{
                throw new PessoaNaoEncontrada();
            }
        }else{
            throw new RuntimeException("Nenhum morador registrado");
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



    public void adicionarReclamacao(String cpf, String reclamacao) {
        Morador morador = buscarMorador(cpf);
        if (morador != null) {
            repo.adicionarReclamacao(morador);
        }else{
            throw new PessoaNaoEncontrada();
        }
    }
}
