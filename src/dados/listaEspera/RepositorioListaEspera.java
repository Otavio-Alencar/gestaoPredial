package dados.listaEspera;

import dados.persistencia.PersistenciaListaEsperaTXT;
import negocio.entidade.PessoaListaEspera;
import negocio.entidade.ListaEspera;

import java.util.ArrayList;
import java.util.List;

public class RepositorioListaEspera {

    private static RepositorioListaEspera instancia;
    private final ListaEspera listaEspera;
    private final PersistenciaListaEsperaTXT persistencia;
    private int contadorOrdem;

    private RepositorioListaEspera() {
        String baseDir = System.getProperty("user.dir");
        String caminhoArquivo = baseDir + "/src/dados/listaEspera/lista_espera.txt";
        persistencia = new PersistenciaListaEsperaTXT(caminhoArquivo);

        // Carrega do arquivo ou cria nova lista se inexistente
        listaEspera = persistencia.carregar();
        // Define contadorOrdem baseado na última pessoa adicionada
        contadorOrdem = listaEspera.getListaEspera().stream()
                .mapToInt(PessoaListaEspera::getOrdemChegada)
                .max()
                .orElse(0) + 1;
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
        persistencia.salvar(listaEspera);
    }

    public void removerPessoa(String cpf) {
        listaEspera.getListaEspera().removeIf(p -> p.getCpf().equals(cpf));
        persistencia.salvar(listaEspera);
    }

    public int tamanhoFila() {
        return listaEspera.getListaEspera().size();
    }

    public List<PessoaListaEspera> getPessoas() {
        return new ArrayList<>(listaEspera.getListaEspera());
    }
}
