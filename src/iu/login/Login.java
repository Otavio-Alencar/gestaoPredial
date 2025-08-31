package iu.login;

import dados.sindico.RepositorioLogin;
import dados.sindico.SessaoSindico;
import negocio.NegocioLogin;
import negocio.entidade.Sindico;
import negocio.excecao.SindicoException.JaTemSindicoException;

import java.util.Scanner;

public class Login {

    private final Scanner scanner = new Scanner(System.in);
    private final NegocioLogin negocio = new NegocioLogin(new RepositorioLogin());

    public void menu() {
        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("[1] Cadastrar Síndico");
            System.out.println("[2] Realizar Login");
            System.out.println("[0] Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarSindico();
                case 2 -> {
                    realizarLogin();
                    return;
                }
                case 0 -> {
                    System.out.println("Saindo...");
                    return;
                }
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private void cadastrarSindico() {
        System.out.println("\n=== Cadastro de Síndico ===");

        System.out.print("Digite o nome do síndico: ");
        String nome = scanner.nextLine();

        System.out.print("Digite a senha do síndico: ");
        String senha = scanner.nextLine();

        if (nome.trim().isEmpty() || senha.trim().isEmpty()) {
            System.out.println("Nome ou senha não podem ser vazios!");
            return;
        }

        Sindico sindico = new Sindico(nome, senha);

        try {
            negocio.cadastrarSindico(sindico);
            System.out.println("Síndico cadastrado com sucesso!");
        } catch (JaTemSindicoException e) {
            System.out.println("Já existe um síndico cadastrado.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
        }
    }

    private void realizarLogin() {
        System.out.println("\n=== Login ===");

        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();

        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        try {
            Sindico sindico = negocio.autenticarSindico(nome, senha);

            if (sindico != null) {
                SessaoSindico.login(sindico);
                System.out.println("Login bem-sucedido! Bem-vindo, " + sindico.getNome());
            } else {
                System.out.println("Nome ou senha incorretos.");
            }
        } catch (RuntimeException e) {
            System.out.println("Erro ao autenticar: " + e.getMessage());
        }
    }

    private void mostrarSindicoLogado() {
        if (SessaoSindico.isLogado()) {
            Sindico s = SessaoSindico.getSindicoLogado();
            System.out.println("Síndico logado: " + s.getNome());
        } else {
            System.out.println("Nenhum síndico logado no momento.");
        }
    }
}
