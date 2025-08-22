package iu.main;

import dados.edificio.RepositorioEdificio;
import dados.sindico.SessaoSindico;
import negocio.NegocioEdificio;

import java.util.Scanner;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final NegocioEdificio negocioEdificio = NegocioEdificio.getInstancia();
    private final EdificioIU edificioIU = new EdificioIU();
    private final ListaEsperaIU listaEsperaIU = new ListaEsperaIU();
    private final MoradorIU moradorIU = new MoradorIU();
    public void menuPrincipal() {
        if (SessaoSindico.getSindicoLogado() == null) {
            System.out.println("Nenhum síndico logado. Acesse o login primeiro.");
            return;
        }

        String nomeSindico = SessaoSindico.getSindicoLogado().getNome();
        System.out.printf("\n=== Menu Principal ===\nBem-vindo, %s\n", nomeSindico);

        boolean sair = false;
        while (!sair) {
            System.out.println("\n=== O que deseja fazer? ===");

            if (!negocioEdificio.temEdificio(SessaoSindico.getSindicoLogado())) {
                System.out.println("[1] Cadastrar Edifício");
                System.out.println("[2] Sair");
            } else {
                System.out.println("[1] Editar Edifício");
                System.out.println("[2] Moradores");
                System.out.println("[3] Lista de espera");
                System.out.println("[4] Sair");
            }

            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            if (!negocioEdificio.temEdificio(SessaoSindico.getSindicoLogado())) {
                switch (opcao) {
                    case 1 -> edificioIU.cadastrarEdificio();
                    case 2 -> {
                        System.out.println("Saindo...");
                        SessaoSindico.logout();
                        sair = true;
                    }
                    default -> System.out.println("Opção inválida! Tente novamente.");
                }
            } else {
                switch (opcao) {
                    case 1 -> {
                        edificioIU.menuEdificio();
                    }
                    case 2 -> {
                        if (negocioEdificio.getEdificio() == null) {
                            System.out.println("⚠ Nenhum edifício cadastrado. Cadastre o edifício primeiro.");
                        } else {
                            moradorIU.menuMorador();
                        }
                    }
                    case 3 -> {
                        if (negocioEdificio.getEdificio() == null) {
                            System.out.println("⚠ Nenhum edifício cadastrado. Cadastre o edifício primeiro.");
                        } else {
                            listaEsperaIU.menuListaEspera();
                        }

                    }
                    case 4 -> {
                        System.out.println("Saindo...");
                        SessaoSindico.logout();
                        sair = true;
                    }
                    default -> System.out.println("Opção inválida! Tente novamente.");
                }
            }
        }
    }
}
