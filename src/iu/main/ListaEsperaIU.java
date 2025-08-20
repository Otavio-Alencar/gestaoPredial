package iu.main;


import negocio.NegocioListaEspera;
import negocio.entidade.PessoaListaEspera;

import java.util.List;
import java.util.Scanner;

public class ListaEsperaIU {
    private final NegocioListaEspera negocioListaEspera = new NegocioListaEspera();
    private final Scanner scanner = new Scanner(System.in);
    public void menuListaEspera(){
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n=== Menu da Lista de Espera ===");
            System.out.println("[1] Ver Lista de espera");
            System.out.println("[2] Adicionar Pessoa");
            System.out.println("[3] Remover Pessoa");
            System.out.println("[4] Gerar relatório");
            System.out.println("[5] Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> verListaEspera();
                case 2 -> cadastrarPessoa();
                case 3 -> removerPessoa();
                case 4 -> gerarRelatorio();
                case 5 -> voltar = true;
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private void verListaEspera(){
        List<PessoaListaEspera> lista = negocioListaEspera.listarFila();

        lista.forEach(p->System.out.println(p.getNome() + " | Cotas: " + p.getTotalCotas() + " | Ordem: " + p.getOrdemChegada()));
    }

    private void cadastrarPessoa() {
        System.out.println("\n=== Cadastro de Pessoa na Lista de Espera ===");

        String nome = lerTextoObrigatorio("Nome");
        String cpf = lerTextoObrigatorio("CPF");
        String contato = lerTextoObrigatorio("Contato (telefone/email)");

        boolean ppi = lerBoolean("É PPI (Preto, Pardo ou Indígena)? (s/n)");
        boolean quilombola = lerBoolean("É Quilombola? (s/n)");
        boolean pcd = lerBoolean("É PCD (Pessoa com Deficiência)? (s/n)");
        boolean escolaPublica = lerBoolean("Estudou em Escola Pública? (s/n)");
        boolean baixaRenda = lerBoolean("É de Baixa Renda? (s/n)");

        // chama o negócio para cadastrar
        negocioListaEspera.adicionarPessoa(
                nome, cpf, contato, ppi, quilombola, pcd, escolaPublica, baixaRenda
        );

        System.out.println("\n✅ Pessoa adicionada com sucesso à lista de espera!");
    }


    private String lerTextoObrigatorio(String campo) {
        String valor;
        do {
            System.out.print(campo + ": ");
            valor = scanner.nextLine().trim();
            if (valor.isEmpty()) {
                System.out.println("⚠️  O campo '" + campo + "' não pode ficar vazio. Tente novamente.");
            }
        } while (valor.isEmpty());
        return valor;
    }

    private boolean lerBoolean(String pergunta) {
        String resposta;
        while (true) {
            System.out.print(pergunta + " ");
            resposta = scanner.nextLine().trim().toLowerCase();

            if (resposta.equals("s")) return true;
            if (resposta.equals("n")) return false;

            System.out.println("⚠️  Resposta inválida. Digite apenas 's' para sim ou 'n' para não.");
        }
    }

    private void removerPessoa(){
        System.out.println("\n=== Remover Pessoa na Lista de Espera ===");
        String cpf = lerTextoObrigatorio("Qual o cpf da pessoa que deseja remover");
        negocioListaEspera.removerPessoa(cpf);
        System.out.println("Removido com sucesso!");
    }

    private void gerarRelatorio(){
        System.out.println("\n=== Relatorio de Pessoas ===");
    }
}
