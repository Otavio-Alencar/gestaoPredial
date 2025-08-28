package negocio.entidade;

import negocio.entidade.PessoaListaEspera;
import java.util.List;
import java.util.ArrayList;

public class ListaEspera {
    private List<PessoaListaEspera> listaEspera;

    public ListaEspera() {
        this.listaEspera = new ArrayList<>();
    }

    public List<PessoaListaEspera> getListaEspera() {
        return listaEspera;
    }
}