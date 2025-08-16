package dados.Sindico;

import negocio.entidade.Sindico;

import negocio.excecao.JaTemSindicoException;
import utils.HashSenha;

import java.io.*;

public class RepositorioLogin implements IRepositorioLogin {

    private final String caminhoArquivo;

    public RepositorioLogin() {
        String baseDir = System.getProperty("user.dir");
        this.caminhoArquivo = baseDir + "/src/dados/sindico/sindico.txt";
    }

    @Override
    public void cadastrarSindico(Sindico sindico) {
        if (NaoTemSindico()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
                writer.write(sindico.getNome() + "-" + sindico.getSenhaHash());
            } catch (IOException e) {
                throw new RuntimeException("Erro ao cadastrar síndico: " + e.getMessage(), e);
            }
        } else {
            throw new JaTemSindicoException();
        }
    }

    @Override
    public Boolean NaoTemSindico() {
        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha = leitor.readLine();
            return (linha == null || linha.trim().isEmpty());
        } catch (FileNotFoundException e) {
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }

    public Sindico buscarSindico() {
        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha = leitor.readLine();
            if (linha != null && !linha.trim().isEmpty()) {
                String[] partes = linha.split("-", 2);
                if (partes.length == 2) {
                    // Usamos o construtor que já aceita hash
                    return new Sindico(partes[0], partes[1], true);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean autenticar(String nome, String senha) {
        String baseDir = System.getProperty("user.dir");
        String caminho = baseDir + "/src/dados/sindico/sindico.txt";

        try (BufferedReader leitor = new BufferedReader(new FileReader(caminho))) {
            String linha = leitor.readLine();
            if (linha != null) {
                String[] partes = linha.split("-");
                String nomeSalvo = partes[0];
                String senhaHashSalva = partes[1];

                if (nome.equals(nomeSalvo) && HashSenha.gerarHash(senha).equals(senhaHashSalva)) {
                    SessaoSindico.login(new Sindico(nome, senhaHashSalva));
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public void removerSindico() {
        try {
            File file = new File(caminhoArquivo);
            if (file.exists()) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    // limpa conteúdo
                }
                System.out.println("Síndico removido com sucesso.");
            } else {
                System.out.println("Não há síndico para remover.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao remover síndico: " + e.getMessage());
        }
    }

    @Override
    public void alterarSindico() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            Sindico novoSindico = obterDadosNovoSindico(); // método placeholder
            writer.write(novoSindico.getNome() + "-" + novoSindico.getSenhaHash());
            System.out.println("Síndico alterado com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao alterar síndico: " + e.getMessage());
        }
    }

    // placeholder para pegar dados do novo síndico
    private Sindico obterDadosNovoSindico() {
        return new Sindico("Novo Nome", "novaSenha123");
    }
}
