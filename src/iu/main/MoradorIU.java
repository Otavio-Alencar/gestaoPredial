package iu.main;

import negocio.entidade.Morador;
import java.util.List;
import java.util.Scanner;
import fachada.FachadaMorador;
import negocio.excecao.ListaDeEsperaException.PessoaNaoEncontradaException;

public class MoradorIU {

    private final Scanner scanner = new Scanner(System.in);
    private final FachadaMorador fachada = new FachadaMorador();

    public void menuMorador() {
        boolean voltar = false;

        while (!voltar) {
            System.out.println("\n=== Menu Morador ===");
            System.out.println("[1] Cadastrar morador a partir da lista de espera");
            System.out.println("[2] Remover morador");
            System.out.println("[3] Listar moradores");
            System.out.println("[4] Adicionar reclamação");
            System.out.println("[5] Voltar");

            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch (opcao) {
                case 1 -> cadastrarMorador();
                case 2 -> removerMorador();
                case 3 -> listarMoradores();
                case 4 -> adicionarReclamacao();
                case 5 -> voltar = true;
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private void cadastrarMorador() {
        try {
            fachada.cadastrarProximoMorador();
            System.out.println("Morador cadastrado e alocado em quarto com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("⚠ Erro ao cadastrar morador: " + e.getMessage());
        }
    }

    private void removerMorador() {
        String cpf = lerTextoObrigatorio("Digite o CPF do morador a ser removido");
        try {
            fachada.removerMorador(cpf);
            System.out.println("Morador removido com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("⚠ " + e.getMessage());
        } catch (PessoaNaoEncontradaException e) {
            throw new RuntimeException(e);
        }
    }

    private void listarMoradores() {
        List<Morador> moradores = fachada.listarMoradores();
        if (moradores.isEmpty()) {
            System.out.println("⚠ Nenhum morador cadastrado.");
            return;
        }

        System.out.println("\n=== Moradores Registrados ===");
        for (Morador m : moradores) {
            System.out.println("Nome:"+  m.getNome() +
                    "|" + "CPF: "+ m.getCpf() +"|" +
                    "Contato: "+m.getContato()+"|"+
                    "Reclamações: "+m.getNumReclamacoes()+ "|"+
                    "Status: "+ m.getStatus());
        }
    }

    private void adicionarReclamacao() {
        String cpf = lerTextoObrigatorio("Digite o CPF do morador");
        String reclamacao = lerTextoObrigatorio("Digite a reclamação");

        try {
            fachada.adicionarReclamacao(cpf, reclamacao);
            System.out.println("Reclamação adicionada com sucesso!");
        } catch (RuntimeException | PessoaNaoEncontradaException e) {
            System.out.println("⚠ " + e.getMessage());
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
}
