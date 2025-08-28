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

        for(int i = 1; i <= quantidadeDeQuartos; i++){
            quartos.add(new Quarto(i));
        }
    }

    public StatusEdificio getStatus() {
        return status;
    }

    public void setStatus(StatusEdificio status) {
        this.status = status;
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

    public Endereco getEndereco() {
        return endereco;
    }

    public int getQuantidadeDeQuartos() {
        return quantidadeDeQuartos;
    }

    public void setQuantidadeDeQuartos(int novaQuantidade) {
        if (novaQuantidade <= 0) {
            throw new IllegalArgumentException("A quantidade de quartos deve ser maior que 0.");
        }

        int atual = this.quantidadeDeQuartos;

        // Caso de aumento de quartos
        if (novaQuantidade > atual) {
            for (int i = atual + 1; i <= novaQuantidade; i++) {
                quartos.add(new Quarto(i));
            }
        }
        // Caso de redução de quartos
        else if (novaQuantidade < atual) {
            // Quartos que seriam removidos (do fim da lista para o começo)
            for (int i = atual; i > novaQuantidade; i--) {
                Quarto quarto = getQuartoPorId(i);
                if (quarto != null) {
                    if (quarto.isOcupado()) {
                        throw new IllegalStateException(
                                "Não é possível reduzir a quantidade de quartos. O quarto " + i + " está ocupado."
                        );
                    }
                    quartos.remove(quarto);
                }
            }
        }

        // Atualiza a quantidade registrada
        this.quantidadeDeQuartos = novaQuantidade;
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
