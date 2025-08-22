package dados.listaEspera;

import negocio.entidade.PessoaListaEspera;
import negocio.entidade.ListaEspera;

import java.util.ArrayList;
import java.util.List;

public class RepositorioListaEspera {

    private static RepositorioListaEspera instancia;
    private final ListaEspera listaEspera;
    private int contadorOrdem;

    private RepositorioListaEspera() {
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
        // Retorna uma cópia da lista para evitar manipulação externa
        return new ArrayList<>(listaEspera.getListaEspera());
    }
}
