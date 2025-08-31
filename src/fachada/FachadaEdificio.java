package fachada;

import negocio.NegocioEdificio;
import negocio.entidade.Edificio;
import negocio.entidade.Sindico;
import negocio.excecao.SindicoException.SindicoJaTemEdificioException;
import negocio.excecao.SindicoException.SindicoNaoLogadoException;
import negocio.excecao.SindicoException.SindicoNaoTemEdificioException;
import negocio.relatorio.RelatorioEdificio;

public class FachadaEdificio {

    private final NegocioEdificio negocioEdificio;

    public FachadaEdificio() {
        this.negocioEdificio = NegocioEdificio.getInstancia();
    }

    public void cadastrarEdificio(Sindico sindicoLogado, Edificio edificio) {
        try {
            negocioEdificio.adicionarEdificio(sindicoLogado, edificio);
        } catch (SindicoNaoLogadoException | SindicoJaTemEdificioException e) {
            System.out.println(e.getMessage());

        }
    }

    public void removerEdificio(Sindico sindicoLogado) {
        try{
            negocioEdificio.removerEdificio(sindicoLogado);
        }catch(SindicoNaoLogadoException | SindicoNaoTemEdificioException e){
            System.out.println(e.getMessage());
        }
    }

    public Edificio getEdificio() {
        return negocioEdificio.getEdificio();
    }

    public boolean temEdificio() {
        return negocioEdificio.getEdificio() != null;
    }

    public void gerarRelatorioEdificio(String caminhoArquivo) {
        Edificio edificio = negocioEdificio.getEdificio();
        if (edificio == null) {
            throw new IllegalStateException("Nenhum edifício cadastrado para gerar relatório.");
        }
        RelatorioEdificio relatorio = new RelatorioEdificio(edificio, caminhoArquivo);
        relatorio.gerar();
    }
}
