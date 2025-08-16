package negocio.entidade;

import utils.HashSenha;

public class Sindico {
    private String nome;
    private String senhaHash;

    // Construtor para cadastro (senha em texto puro)
    public Sindico(String nome, String senha) {
        this.nome = nome;
        this.senhaHash = HashSenha.gerarHash(senha);
    }

    // Construtor para leitura do arquivo (senha já em hash)
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

    // Método para validar senha digitada
    public boolean validarSenha(String senhaDigitada) {
        return HashSenha.gerarHash(senhaDigitada).equals(this.senhaHash);
    }
}
