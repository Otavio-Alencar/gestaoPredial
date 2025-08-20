package iu.main;

import dados.edificio.RepositorioEdificio;
import dados.morador.RepositorioMorador;
import negocio.NegocioEdificio;
import negocio.NegocioMorador;

import java.util.Scanner;

public class MoradorIU {
    private final NegocioMorador negocioMorador= new NegocioMorador(new RepositorioMorador());
    private final Scanner scanner = new Scanner(System.in);

    public void menuMorador() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n=== Menu de morador ===");
            System.out.println("[1] Ver Moradores");
            System.out.println("[2] Adicionar Morador");
            System.out.println("[3] Remover Morador");
            System.out.println("[4] Enviar Notificação para morador");
            System.out.println("[4] Gerar relatório");
            System.out.println("[5] Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
//                case 1 -> verListaEspera();
//                case 2 -> cadastrarPessoa();
//                case 3 -> removerPessoa();
//                case 4 -> gerarRelatorio();
                case 5 -> voltar = true;
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }
}
