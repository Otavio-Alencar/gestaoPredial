package iu.main;

import dados.edificio.RepositorioEdificio;
import dados.sindico.SessaoSindico;
import negocio.NegocioEdificio;
import negocio.entidade.Edificio;
import negocio.entidade.Endereco;
import negocio.excecao.SindicoJaTemEdificio;
import negocio.excecao.SindicoNaoLogado;

import java.util.Scanner;

public class EdificioIU {
    private final NegocioEdificio negocioEdificio = new NegocioEdificio(new RepositorioEdificio());
    private final Scanner scanner = new Scanner(System.in);
    public void cadastrarEdificio() {
        System.out.println("\n=== Cadastrar Edifício ===\n");

        String imovel;
        do {
            System.out.print("Digite o nome do imóvel: ");
            imovel = scanner.nextLine().trim();
            if (imovel.isEmpty()) {
                System.out.println("O nome do imóvel não pode ficar vazio.");
            }
        } while (imovel.isEmpty());

        String descricao;
        do {
            System.out.print("Digite a descrição do edifício: ");
            descricao = scanner.nextLine().trim();
            if (descricao.isEmpty()) {
                System.out.println("A descrição não pode ficar vazia.");
            }
        } while (descricao.isEmpty());

        int quantidadeDeQuartos;
        do {
            System.out.print("Digite a quantidade de quartos: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Digite um número válido.");
                scanner.next(); // descarta entrada inválida
            }
            quantidadeDeQuartos = scanner.nextInt();
            scanner.nextLine(); // limpar buffer
            if (quantidadeDeQuartos <= 0) {
                System.out.println("A quantidade de quartos deve ser maior que zero.");
            }
        } while (quantidadeDeQuartos <= 0);

        String cep;
        do {
            System.out.print("Digite o CEP: ");
            cep = scanner.nextLine().trim();
            if (cep.isEmpty() || cep.length() < 8) {
                System.out.println("Digite um CEP válido (mínimo 8 caracteres).");
            }
        } while (cep.isEmpty() || cep.length() < 8);

        String rua;
        do {
            System.out.print("Digite a rua: ");
            rua = scanner.nextLine().trim();
            if (rua.isEmpty()) {
                System.out.println("A rua não pode ficar vazia.");
            }
        } while (rua.isEmpty());

        String bairro;
        do {
            System.out.print("Digite o bairro: ");
            bairro = scanner.nextLine().trim();
            if (bairro.isEmpty()) {
                System.out.println("O bairro não pode ficar vazio.");
            }
        } while (bairro.isEmpty());

        String numero;
        do {
            System.out.print("Digite o número: ");
            numero = scanner.nextLine().trim();
            if (numero.isEmpty()) {
                System.out.println("O número não pode ficar vazio.");
            }
        } while (numero.isEmpty());

        System.out.print("Digite o complemento (opcional): ");
        String complemento = scanner.nextLine().trim();

        Endereco endereco = new Endereco(cep, rua, bairro, numero, complemento);
        Edificio novoEdificio = new Edificio(imovel, descricao, quantidadeDeQuartos, endereco);

        try {
            negocioEdificio.adicionarEdificio(SessaoSindico.getSindicoLogado(), novoEdificio);
            System.out.println("Edifício cadastrado com sucesso!");
        } catch (SindicoNaoLogado e) {
            System.out.println("Erro: nenhum síndico logado.");
        } catch (SindicoJaTemEdificio e) {
            System.out.println("Erro: o síndico já possui um edifício cadastrado.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao cadastrar edifício: " + e.getMessage());
        }
    }



    public void menuEdificio() {
        if (SessaoSindico.getSindicoLogado() == null) {
            System.out.println("Erro: nenhum síndico logado.");
            return;
        }

        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n=== Menu do Edifício ===");
            System.out.println("[1] Ver informações do edifício");
            System.out.println("[2] Editar edifício");
            System.out.println("[3] Remover edifício");
            System.out.println("[4] Gerar relatório");
            System.out.println("[5] Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch (opcao) {
                case 1 -> verInformacoesEdificio();
                case 2 -> editarEdificio();
                case 3 -> removerEdificio();
                case 4 -> gerarRelatorio();
                case 5 -> voltar = true;
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private void verInformacoesEdificio() {
        try {
            Edificio e = SessaoSindico.getSindicoLogado().getEdificio();
            if (e == null) {
                System.out.println("Nenhum edifício cadastrado.");
                return;
            }
            System.out.println("\n=== Informações do Edifício ===");
            System.out.println("Nome: " + e.getImovel());
            System.out.println("Descrição: " + e.getDescricao());
            System.out.println("Quantidade de quartos: " + e.getQuantidadeDeQuartos());
            System.out.println("CEP" + e.getEndereco().getCep());
            System.out.println("Rua: " + e.getEndereco().getRua());
            System.out.println("Bairro: " + e.getEndereco().getBairro());
            System.out.println("Numero: " + e.getEndereco().getNumero());
            System.out.println("Complemento: " + e.getEndereco().getComplemento());
        } catch (Exception ex) {
            System.out.println("Erro ao exibir informações: " + ex.getMessage());
        }
    }

    private void editarEdificio() {
        try {
            System.out.println("\n=== Editar Edifício ===");
            // Aqui você pode reaproveitar a lógica do cadastro, pedindo novos valores
            // e chamando negocioEdificio.atualizarEdificio(...)
            System.out.println("Funcionalidade de edição ainda não implementada.");
        } catch (SindicoNaoLogado | SindicoJaTemEdificio e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void removerEdificio() {
        try {
            negocioEdificio.removerEdificio(SessaoSindico.getSindicoLogado());
            System.out.println("Edifício removido com sucesso!");
        } catch (SindicoNaoLogado e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void gerarRelatorio() {
    }

}
