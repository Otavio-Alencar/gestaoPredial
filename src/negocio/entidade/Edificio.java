package negocio.entidade;

import negocio.enums.StatusEdificio;

import java.util.ArrayList;

public class Edificio {
    private String imovel,descricao;
    private StatusEdificio status;
    private int quantidadeDeQuartos;
    private Endereco endereco;
    private ArrayList<Quarto> quartos;
    public Edificio(String imovel, String descricao, int quantidadeDeQuartos, Endereco endereco) {
        this.imovel = imovel;
        this.descricao = descricao;
        this.quantidadeDeQuartos = quantidadeDeQuartos;
        this.endereco = endereco;
        this.status = StatusEdificio.ATIVO;
        this.quartos = new ArrayList<>();

        for(int i = 1; i < quantidadeDeQuartos; i++){
            quartos.add(new Quarto(i));
        }
    }

    public String getImovel() {
        return imovel;
    }

    public void setImovel(String imovel) {
        this.imovel = imovel;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidadeDeQuartos() {
        return quantidadeDeQuartos;
    }

    public void setQuantidadeDeQuartos(int quantidadeDeQuartos) {
        this.quantidadeDeQuartos = quantidadeDeQuartos;
    }

    public ArrayList<Quarto> getQuartos() {
        return quartos;
    }

    public Quarto getQuartoPorId(int id){
        for(Quarto quarto: quartos){
            if(quarto.getIdQuarto() == id){
                return quarto;
            }
        }
        return null;
    }
}
