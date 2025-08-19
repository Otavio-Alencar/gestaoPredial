package dados.sindico;

import negocio.entidade.Sindico;
import utils.HashSenha;

import java.io.*;

public class RepositorioLogin implements IRepositorioLogin {

    private final String caminhoArquivo;

    public RepositorioLogin() {
        String baseDir = System.getProperty("user.dir");
        this.caminhoArquivo = baseDir + "/src/dados/sindico/sindico.txt";
    }

    @Override
    public void cadastrarSindico(Sindico sindico) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            writer.write(sindico.getNome() + "-" + sindico.getSenhaHash());
        }
    }


    public boolean naoTemSindico() throws IOException {
        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha = leitor.readLine();
            return (linha == null || linha.trim().isEmpty());
        } 
    }


    public Sindico buscarSindico() throws IOException {
        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha = leitor.readLine();
            if (linha != null && !linha.trim().isEmpty()) {
                String[] partes = linha.split("-", 2);
                if (partes.length == 2) {
                    return new Sindico(partes[0], partes[1], true);
                }
            }
        }
        return null;
    }

    @Override
    public Sindico autenticar(String nome, String senha) throws IOException {
        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha = leitor.readLine();
            if (linha != null) {
                String[] partes = linha.split("-", 2);
                String nomeSalvo = partes[0];
                String senhaHashSalva = partes[1];

                if (nome.equals(nomeSalvo) && HashSenha.gerarHash(senha).equals(senhaHashSalva)) {
                    return new Sindico(nomeSalvo, senhaHashSalva, true);
                }
            }
        }
        return null;
    }

    @Override
    public void removerSindico() throws IOException {
        File file = new File(caminhoArquivo);
        if (file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // limpa conteúdo (arquivo fica vazio)
            }
        }
    }

    @Override
    public void alterarSindico(Sindico novoSindico) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            writer.write(novoSindico.getNome() + "-" + novoSindico.getSenhaHash());
        }
    }
}
