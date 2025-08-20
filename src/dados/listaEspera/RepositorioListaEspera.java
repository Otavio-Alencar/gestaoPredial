package dados.listaEspera;

import negocio.entidade.PessoaListaEspera;

import java.util.ArrayList;
import java.util.List;
import negocio.entidade.ListaEspera;

public class RepositorioListaEspera {

    private ListaEspera listaEspera;
    private int contadorOrdem;

    public RepositorioListaEspera() {
        this.listaEspera = new ListaEspera();
        contadorOrdem = 1;

    }

    public void adicionarPessoa(String nome, String cpf, String contato,
                                boolean ppi, boolean quilombola, boolean pcd,
                                boolean escolaPublica, boolean baixaRenda){
        PessoaListaEspera pessoa = new PessoaListaEspera(
                nome, cpf, contato, ppi, quilombola, pcd, escolaPublica, baixaRenda, contadorOrdem++
        );
        listaEspera.getListaEspera().add(pessoa);
    }
    public void removerPessoa(String cpf) {
        for (PessoaListaEspera p : listaEspera.getListaEspera()) {
            if (p.getCpf().equals(cpf)) {
                listaEspera.getListaEspera().remove(p);
                break;
            }
        }
    }

    public int tamanhoFila() {
        return listaEspera.getListaEspera().size();
    }


    public List<PessoaListaEspera> getPessoas(){ return new ArrayList<>(listaEspera.getListaEspera()); }
}
