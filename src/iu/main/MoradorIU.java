package iu.main;

import negocio.NegocioListaEspera;
import negocio.NegocioMorador;
import negocio.entidade.Morador;
import negocio.excecao.ListaEsperaVazia;
import negocio.excecao.NenhumQuartoLivreException;
import negocio.excecao.PessoaNaoEncontrada;
import dados.sindico.SessaoSindico;

import java.util.List;
import java.util.Scanner;

public class MoradorIU {

    private final Scanner scanner = new Scanner(System.in);
    private final NegocioListaEspera listaEspera;
    private final NegocioMorador negocioMorador;

    public MoradorIU() {
        this.listaEspera = NegocioListaEspera.getInstancia(); // usa singleton
        this.negocioMorador = new NegocioMorador(); // injeta lista de espera
    }

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
            negocioMorador.cadastrarMorador(listaEspera);
            System.out.println("Morador cadastrado e alocado em quarto com sucesso!");
        } catch (ListaEsperaVazia e) {
            System.out.println("⚠ Lista de espera vazia. Não há pessoas para cadastrar.");
        } catch (NenhumQuartoLivreException e) {
            System.out.println("⚠ Nenhum quarto disponível no edifício.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao cadastrar morador: " + e.getMessage());
        }
    }

    private void removerMorador() {
        System.out.print("Digite o CPF do morador a ser removido: ");
        String cpf = scanner.nextLine().trim();

        try {
            negocioMorador.removerMorador(cpf);
            System.out.println("Morador removido com sucesso!");
        } catch (PessoaNaoEncontrada e) {
            System.out.println("⚠ Morador não encontrado.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao remover morador: " + e.getMessage());
        }
    }

    private void listarMoradores() {
        try {
            List<Morador> moradores = negocioMorador.listarMoradores();
            System.out.println("\n=== Moradores Registrados ===");
            for (Morador m : moradores) {
                System.out.printf("Nome: %s | CPF: %s | Contato: %s | Reclamações: %d\n",
                        m.getNome(), m.getCpf(), m.getContato(), m.getNumReclamacoes());
            }
        } catch (RuntimeException e) {
            System.out.println("⚠ " + e.getMessage());
        }
    }

    private void adicionarReclamacao() {
        System.out.print("Digite o CPF do morador: ");
        String cpf = scanner.nextLine().trim();
        System.out.print("Digite a reclamação: ");
        String reclamacao = scanner.nextLine().trim();

        try {
            negocioMorador.adicionarReclamacao(cpf, reclamacao);
            System.out.println("Reclamação adicionada com sucesso!");
        } catch (PessoaNaoEncontrada e) {
            System.out.println("⚠ Morador não encontrado.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao adicionar reclamação: " + e.getMessage());
        }
    }
}
