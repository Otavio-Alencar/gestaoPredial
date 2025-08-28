package negocio.entidade;

import negocio.enums.StatusQuarto;

public class Quarto {
    private int idQuarto;
    private StatusQuarto status;
    private Morador morador;
    public Quarto(int idQuarto) {
        this.idQuarto = idQuarto;
        this.status = StatusQuarto.LIVRE;
    }

    public StatusQuarto getStatus() {
        return status;
    }

    public void setStatus(StatusQuarto status) {
        this.status = status;
    }

    public int getIdQuarto() {
        return idQuarto;
    }


    public Morador getMorador() {
        return morador;
    }

    public void ocupar(Morador morador) {
        this.morador = morador;
        this.status = StatusQuarto.OCUPADO;
    }

    public void liberar(){
        this.morador = null;
        this.status = StatusQuarto.LIVRE;
    }

    public boolean isOcupado() {
        return this.status == StatusQuarto.OCUPADO;
    }
}
