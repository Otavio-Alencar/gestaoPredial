package dados.listaEspera;

import negocio.entidade.PessoaListaEspera;
import negocio.entidade.ListaEspera;

import java.util.ArrayList;
import java.util.List;

public class RepositorioListaEspera {

    private static RepositorioListaEspera instancia;
    private ListaEspera listaEspera;
    private int contadorOrdem;

    private RepositorioListaEspera() { // construtor privado
        this.listaEspera = new ListaEspera();
        this.contadorOrdem = 1;
    }

    public static RepositorioListaEspera getInstancia() {
        if (instancia == null) {
            instancia = new RepositorioListaEspera();
        }
        return instancia;
    }

    public void adicionarPessoa(String nome, String cpf, String contato,
                                boolean ppi, boolean quilombola, boolean pcd,
                                boolean escolaPublica, boolean baixaRenda) {
        PessoaListaEspera pessoa = new PessoaListaEspera(
                nome, cpf, contato, ppi, quilombola, pcd, escolaPublica, baixaRenda, contadorOrdem++
        );
        listaEspera.getListaEspera().add(pessoa);
    }

    public void removerPessoa(String cpf) {
        listaEspera.getListaEspera().removeIf(p -> p.getCpf().equals(cpf));
    }

    public int tamanhoFila() {
        return listaEspera.getListaEspera().size();
    }

    public List<PessoaListaEspera> getPessoas() {
        return new ArrayList<>(listaEspera.getListaEspera());
    }
}
