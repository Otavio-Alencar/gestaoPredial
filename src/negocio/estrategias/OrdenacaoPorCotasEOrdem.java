package negocio.estrategias;

import negocio.entidade.PessoaListaEspera;
import java.util.Comparator;

public class OrdenacaoPorCotasEOrdem implements EstrategiaOrdenacao {

    @Override
    public Comparator<PessoaListaEspera> getComparator() {
        return (p1, p2) -> {
            int diff = Integer.compare(p2.getTotalCotas(), p1.getTotalCotas());
            if (diff != 0) return diff;
            return Integer.compare(p1.getOrdemChegada(), p2.getOrdemChegada());
        };
    }
}
