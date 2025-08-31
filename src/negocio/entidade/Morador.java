package negocio.entidade;

import negocio.enums.StatusMorador;

import java.util.ArrayList;

public class Morador extends Pessoa {
    private int numReclamacoes;
    private ArrayList<String> reclamacoes;
    private StatusMorador status;

    public Morador(String nome, String cpf, String contato) {
        super(nome, cpf, contato);
        reclamacoes = new ArrayList<>();
        this.status = StatusMorador.VALIDO;
    }

    public StatusMorador getStatus() {
        return status;
    }

    public void setStatus() {
        if(status == StatusMorador.VALIDO) {
            status = StatusMorador.EXCEDIDO;
        }
    }

    public ArrayList<String> getReclamacoes() {
        return reclamacoes;
    }



    public int getNumReclamacoes() {
        return numReclamacoes;
    }

    public void adicionarReclamacao(String reclamacao) {
        reclamacoes.add(reclamacao);
        this.numReclamacoes++;

    }
}
