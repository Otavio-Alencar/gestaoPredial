package main;

import dados.Sindico.RepositorioLogin;
import negocio.entidade.Sindico;

import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        RepositorioLogin repo = new RepositorioLogin();
        Scanner scanner = new Scanner(System.in);






        // Teste de autenticação
        System.out.println("\n=== Teste de Login ===");
        System.out.print("Digite o nome: ");
        String loginNome = scanner.nextLine();

        System.out.print("Digite a senha: ");
        String loginSenha = scanner.nextLine();

        if (repo.autenticar(loginNome, loginSenha)) {
            System.out.println("Login bem-sucedido!");
            System.out.println("Testando!");
        } else {
            System.out.println("Nome ou senha incorretos.");
        }

        scanner.close();

    }
}
