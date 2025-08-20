package negocio.entidade;

import negocio.enums.StatusMorador;

import java.util.ArrayList;

public class Morador {
    private String nome,cpf,contato;
    private int numReclamacoes;
    private ArrayList<String> reclamacoes;
    private StatusMorador status;

    public Morador(String nome, String cpf, String contato) {
        this.nome = nome;
        this.cpf = cpf;
        this.contato = contato;
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

    public int getNumReclamacoes() {
        return numReclamacoes;
    }

    public void adicionarReclamacao() {
        this.numReclamacoes++;
    }
}
