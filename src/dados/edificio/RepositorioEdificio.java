package dados.edificio;

import dados.persistencia.PersistenciaEdificioTXT;
import negocio.entidade.Edificio;
import negocio.entidade.Morador;
import negocio.entidade.Quarto;
import negocio.NegocioListaEspera;
import negocio.excecao.MoradorNaoEncontradoException;
import negocio.excecao.NenhumQuartoLivreException;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class RepositorioEdificio implements IRepositorioEdificio {

    private static RepositorioEdificio instancia;
    private final PersistenciaEdificioTXT persistencia;
    private Edificio edificio;
    private final String caminhoArquivo;
    private final NegocioListaEspera negocioListaEspera;
    private RepositorioEdificio() {
        this.negocioListaEspera = NegocioListaEspera.getInstancia();
        String baseDir = System.getProperty("user.dir");
        this.caminhoArquivo = baseDir + "/src/dados/edificio/edificio.txt";
        persistencia = new PersistenciaEdificioTXT(caminhoArquivo);

        Edificio e = persistencia.carregar();
        if (e != null) {
            edificio = e;
        }
    }

    public static RepositorioEdificio getInstancia() {
        if (instancia == null) {
            instancia = new RepositorioEdificio();
        }
        return instancia;
    }

    private boolean arquivoVazioOuInexistente() {
        try {
            if (!Files.exists(persistencia.getArquivoPath())) return true;
            return Files.size(persistencia.getArquivoPath()) == 0;
        } catch (IOException ex) {
            ex.printStackTrace();
            return true;
        }
    }

    @Override
    public void adicionarEdificio(Edificio novoEdificio) {
        if (novoEdificio == null) throw new IllegalArgumentException("Edifício não pode ser nulo.");
        edificio = novoEdificio;
        persistencia.salvar(edificio);
    }

    @Override
    public void removerEdificio() {
        edificio = null;
        try {
            Files.deleteIfExists(persistencia.getArquivoPath());
            negocioListaEspera.removerPersistencia();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void atualizarEdificio(Edificio novoEdificio) {
        if (edificio == null) throw new IllegalStateException("Nenhum edifício cadastrado.");
        edificio = novoEdificio;
        persistencia.salvar(edificio);
    }

    @Override
    public Edificio getEdificio() {
        if (edificio == null || arquivoVazioOuInexistente()) return null;
        return edificio;
    }

    @Override
    public int buscarProximoQuartoLivre() {
        if (getEdificio() == null) throw new IllegalStateException("Nenhum edifício cadastrado.");

        for (Quarto quarto : edificio.getQuartos()) {
            System.out.println("Quarto " + quarto.getIdQuarto() + " -> " + quarto.getStatus());
            if (!quarto.isOcupado()) {
                return quarto.getIdQuarto();
            }
        }
        return -1;
    }

    @Override
    public void preencherQuarto(Morador morador) throws NenhumQuartoLivreException {
        if (getEdificio() == null) throw new IllegalStateException("Nenhum edifício cadastrado.");
        for (Quarto q : edificio.getQuartos()) {
            if (!q.isOcupado()) {
                q.ocupar(morador);
                persistencia.salvar(edificio);
                return;
            }
        }
        throw new NenhumQuartoLivreException();
    }

    @Override
    public void removerDoQuarto(Morador morador) throws MoradorNaoEncontradoException {
        if (morador == null) throw new IllegalArgumentException("Morador não pode ser nulo.");
        if (getEdificio() == null) throw new IllegalStateException("Nenhum edifício cadastrado.");
        for (Quarto q : edificio.getQuartos()) {
            if (q.isOcupado() && q.getMorador().equals(morador)) {
                q.liberar();
                persistencia.salvar(edificio);
                return;
            }
        }
        throw new MoradorNaoEncontradoException("Morador não encontrado em nenhum quarto ocupado.");
    }

    @Override
    public List<Morador> getMoradores() {
        if (getEdificio() == null) throw new IllegalStateException("Nenhum edifício cadastrado.");

        List<Morador> moradores = new ArrayList<>();
        for (Quarto q : edificio.getQuartos()) {
            if (q.isOcupado() && q.getMorador() != null) {
                moradores.add(q.getMorador());
            }
        }
        return moradores;
    }
}
