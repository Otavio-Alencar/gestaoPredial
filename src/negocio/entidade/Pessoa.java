package negocio.entidade;

public abstract class Pessoa {
    protected String nome;
    protected String cpf;
    protected String contato;

    public Pessoa(String nome, String cpf, String contato) {
        this.nome = nome;
        this.cpf = cpf;
        this.contato = contato;
    }

    // Getters e setters comuns
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getContato() { return contato; }
}