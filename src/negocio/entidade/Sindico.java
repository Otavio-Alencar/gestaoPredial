package negocio.entidade;

import utils.HashSenha;

public class Sindico {
    private String nome;
    private String senhaHash;
    private Edificio edificio;

    public Sindico(String nome, String senha) {
        this.nome = nome;
        this.senhaHash = HashSenha.gerarHash(senha);
    }


    public Sindico(String nome, String senhaHash, boolean isHash) {
        this.nome = nome;
        this.senhaHash = senhaHash;
    }

    public String getNome() {
        return nome;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public boolean validarSenha(String senhaDigitada) {
        return HashSenha.gerarHash(senhaDigitada).equals(this.senhaHash);
    }

    public Edificio getEdificio() {
        return edificio;
    }

    public void setEdificio(Edificio edificio) {
        this.edificio = edificio;
    }
}
