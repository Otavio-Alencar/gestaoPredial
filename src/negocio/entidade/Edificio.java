package negocio.entidade;

import negocio.enums.StatusEdificio;

public class Edificio {
    private String imovel,descricao;
    private StatusEdificio status;
    private int quantidadeDeQuartos;
    private Endereco endereco;

    public Edificio(String imovel, String descricao, int quantidadeDeQuartos, Endereco endereco) {
        this.imovel = imovel;
        this.descricao = descricao;
        this.quantidadeDeQuartos = quantidadeDeQuartos;
        this.endereco = endereco;
        this.status = StatusEdificio.ATIVO;
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
}
