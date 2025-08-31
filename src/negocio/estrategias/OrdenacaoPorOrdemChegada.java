package negocio.estrategias;

import negocio.entidade.PessoaListaEspera;
import java.util.Comparator;

public class OrdenacaoPorOrdemChegada implements EstrategiaOrdenacao {
    @Override
    public Comparator<PessoaListaEspera> getComparator() {
        return Comparator.comparingInt(PessoaListaEspera::getOrdemChegada);
    }
}
