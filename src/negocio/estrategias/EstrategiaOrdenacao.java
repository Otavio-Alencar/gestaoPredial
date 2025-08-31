package negocio.estrategias;

import negocio.entidade.PessoaListaEspera;
import java.util.Comparator;

public interface EstrategiaOrdenacao {
    Comparator<PessoaListaEspera> getComparator();
}
