package main;

import dados.Sindico.RepositorioLogin;
import dados.Sindico.SessaoSindico;
import negocio.entidade.Sindico;

import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        RepositorioLogin repo = new RepositorioLogin();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Teste de Cadastro de Síndico ===");

        // Verifica se já existe síndico
        if (repo.NaoTemSindico()) {
            System.out.print("Digite o nome do síndico: ");
            String nome = scanner.nextLine();

            System.out.print("Digite a senha do síndico: ");
            String senha = scanner.nextLine();

            Sindico s = new Sindico(nome, senha);
            repo.cadastrarSindico(s);
        } else {
            System.out.println("Já existe um síndico cadastrado.");
        }

        // Buscar e mostrar o nome
        Sindico sindico = repo.buscarSindico();
        if (sindico != null) {
            System.out.println("Síndico cadastrado: " + sindico.getNome());
        }

        // Teste de autenticação
        System.out.println("\n=== Teste de Login ===");
        System.out.print("Digite o nome: ");
        String loginNome = scanner.nextLine();

        System.out.print("Digite a senha: ");
        String loginSenha = scanner.nextLine();

        if (repo.autenticar(loginNome, loginSenha)) {
            System.out.println("Login bem-sucedido!");
        } else {
            System.out.println("Nome ou senha incorretos.");
        }


        scanner.close();
    }
}
