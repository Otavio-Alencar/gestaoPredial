package negocio.entidade;

public class PessoaListaEspera {
    private String nome, cpf, contato;
    private boolean ppi,quilombola,pcd,escolaPublica,baixaRenda;
    private int ordemChegada;

    public PessoaListaEspera(String nome, String cpf, String contato,
                             boolean ppi, boolean quilombola, boolean pcd,
                             boolean escolaPublica, boolean baixaRenda,
                             int ordemChegada) {
        this.nome = nome;
        this.cpf = cpf;
        this.contato = contato;
        this.ppi = ppi;
        this.quilombola = quilombola;
        this.pcd = pcd;
        this.escolaPublica = escolaPublica;
        this.baixaRenda = baixaRenda;
        this.ordemChegada = ordemChegada;
    }

    public int getTotalCotas() {
        int total = 0;
        if (ppi) total++;
        if (quilombola) total++;
        if (pcd) total++;
        if (escolaPublica) total++;
        if (baixaRenda) total++;
        return total;
    }

    public int getOrdemChegada() {
        return ordemChegada;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public boolean isPpi() {
        return ppi;
    }

    public void setPpi(boolean ppi) {
        this.ppi = ppi;
    }

    public boolean isQuilombola() {
        return quilombola;
    }

    public void setQuilombola(boolean quilombola) {
        this.quilombola = quilombola;
    }

    public boolean isPcd() {
        return pcd;
    }

    public void setPcd(boolean pcd) {
        this.pcd = pcd;
    }

    public boolean isEscolaPublica() {
        return escolaPublica;
    }

    public void setEscolaPublica(boolean escolaPublica) {
        this.escolaPublica = escolaPublica;
    }

    public boolean isBaixaRenda() {
        return baixaRenda;
    }

    public void setBaixaRenda(boolean baixaRenda) {
        this.baixaRenda = baixaRenda;
    }

    public void setOrdemChegada(int ordemChegada) {
        this.ordemChegada = ordemChegada;
    }
}
