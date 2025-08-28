package iu.main;

import negocio.entidade.PessoaListaEspera;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import fachada.FachadaListaEspera;
public class ListaEsperaIU {

    private final FachadaListaEspera fachada = new FachadaListaEspera();
    private final Scanner scanner = new Scanner(System.in);

    public void menuListaEspera() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n=== Menu da Lista de Espera ===");
            System.out.println("[1] Ver Lista de Espera");
            System.out.println("[2] Adicionar Pessoa");
            System.out.println("[3] Remover Pessoa");
            System.out.println("[4] Gerar Relatório");
            System.out.println("[5] Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // consumir quebra de linha

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

    private void verListaEspera() {
        List<PessoaListaEspera> lista = fachada.listarFila();
        if (lista.isEmpty()) {
            System.out.println("⚠️ A lista de espera está vazia.");
            return;
        }

        System.out.println("\n=== Pessoas na Lista de Espera ===");
        lista.forEach(p -> System.out.println(
                p.getNome() + " | Cotas: " + p.getTotalCotas() + " | Ordem: " + p.getOrdemChegada()
        ));
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

        fachada.adicionarPessoa(nome, cpf, contato, ppi, quilombola, pcd, escolaPublica, baixaRenda);

        System.out.println("\n✅ Pessoa adicionada com sucesso à lista de espera!");
    }

    private void removerPessoa() {
        System.out.println("\n=== Remover Pessoa na Lista de Espera ===");
        String cpf = lerTextoObrigatorio("Qual o CPF da pessoa que deseja remover");

        try {
            fachada.removerPessoa(cpf);
            System.out.println("✅ Removido com sucesso!");
        } catch (Exception e) {
            System.out.println("⚠️ Pessoa não encontrada na lista de espera.");
        }
    }

    private void gerarRelatorio() {
        System.out.println("\n=== Gerando Relatório da Lista de Espera (Excel) ===");

        if (!fachada.temPessoas()) {
            System.out.println("⚠️ A lista está vazia. Nenhum relatório gerado.");
            return;
        }

        try {
            String home = System.getProperty("user.home");
            File pasta = new File(home, "DocumentosGestaoPredial");
            if (!pasta.exists() && !pasta.mkdirs()) {
                System.out.println("❌ Não foi possível criar a pasta para salvar o relatório.");
                return;
            }

            String caminho = new File(pasta, "ListaEspera.xlsx").getAbsolutePath();
            fachada.gerarRelatorioListaEspera(caminho);

            System.out.println("✅ Relatório Excel gerado com sucesso em: " + caminho);

        } catch (Exception e) {
            System.out.println("❌ Erro ao gerar o relatório Excel: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ================= Métodos auxiliares =================

    private String lerTextoObrigatorio(String campo) {
        String valor;
        do {
            System.out.print(campo + ": ");
            valor = scanner.nextLine().trim();
            if (valor.isEmpty()) {
                System.out.println("⚠️ O campo '" + campo + "' não pode ficar vazio. Tente novamente.");
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

            System.out.println("⚠️ Resposta inválida. Digite apenas 's' para sim ou 'n' para não.");
        }
    }
}
