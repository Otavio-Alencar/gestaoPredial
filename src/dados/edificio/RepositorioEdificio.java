package dados.edificio;

import negocio.entidade.Edificio;
import dados.sindico.SessaoSindico;
import negocio.entidade.Quarto;
import negocio.enums.StatusQuarto;
import negocio.excecao.SindicoJaTemEdificio;
import negocio.excecao.SindicoNaoLogado;
import negocio.excecao.SindicoNaoTemEdificio;

import java.util.ArrayList;

public class RepositorioEdificio implements IRepositorioEdificio {
    private Edificio edificio;
    @Override
    public void adicionarEdificio(Edificio edificio) {
        if(SessaoSindico.isLogado()){
            if(SessaoSindico.getSindicoLogado().getEdificio() == null){
                SessaoSindico.getSindicoLogado().setEdificio(edificio);
                this.edificio = edificio;
            }else{
                throw new SindicoJaTemEdificio();
            }
        }else{
            throw new SindicoNaoLogado();
        }
    }

    @Override
    public void removerEdificio() {
        if(SessaoSindico.isLogado()){
            SessaoSindico.getSindicoLogado().setEdificio(null);
            this.edificio = null;

        }else{
            throw new SindicoNaoLogado();
        }
    }

    @Override
    public void atualizarEdificio(Edificio edificio) {
        if(SessaoSindico.isLogado()){
            if(SessaoSindico.getSindicoLogado().getEdificio() != null){
                this.edificio = edificio;
                SessaoSindico.getSindicoLogado().setEdificio(edificio);
            }else{
                throw new SindicoNaoTemEdificio();
            }

        }else{
            throw new SindicoNaoLogado();
        }
    }

    @Override
    public int buscarProximoQuartoLivre(){
        for(Quarto q: edificio.getQuartos()){
            if(q.getStatus() == StatusQuarto.LIVRE){
                return q.getIdQuarto();
            }
        }

        return -1;
    }
}
