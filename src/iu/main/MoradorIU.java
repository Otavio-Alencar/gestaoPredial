package iu.main;

import dados.morador.RepositorioMorador;
import dados.sindico.SessaoSindico;
import negocio.NegocioEdificio;
import negocio.NegocioListaEspera;
import negocio.NegocioMorador;
import negocio.entidade.Morador;
import negocio.excecao.ListaEsperaVazia;
import negocio.excecao.NenhumQuartoLivreException;
import negocio.excecao.PessoaNaoEncontrada;

import java.util.Scanner;

public class MoradorIU {

    private final NegocioMorador negocioMorador = new NegocioMorador(new RepositorioMorador());
    private final NegocioEdificio negocioEdificio = NegocioEdificio.getInstancia(); // singleton
    private final NegocioListaEspera negocioListaEspera = NegocioListaEspera.getInstancia();
    private final Scanner scanner = new Scanner(System.in);

    public void menuMorador() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n=== Menu de Morador ===");
            System.out.println("[1] Ver Moradores");
            System.out.println("[2] Adicionar Morador (da lista de espera)");
            System.out.println("[3] Remover Morador");
            System.out.println("[4] Adicionar Reclamação a Morador");
            System.out.println("[5] Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // consumir quebra de linha

            switch (opcao) {
                case 1 -> verMoradores();
                case 2 -> cadastrarMorador();
                case 3 -> removerMorador();
                case 4 -> adicionarReclamacao();
                case 5 -> voltar = true;
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private void verMoradores() {
        try {
            var moradores = negocioMorador.listarMoradores();
            System.out.println("\n=== Lista de Moradores ===");
            for (Morador m : moradores) {
                System.out.println("Nome: " + m.getNome()
                        + " | CPF: " + m.getCpf()
                        + " | Contato: " + m.getContato()
                        + " | Status: " + m.getStatus());
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void cadastrarMorador() {
        if (!negocioEdificio.temEdificio()) {
            System.out.println("⚠ Nenhum edifício cadastrado. Cadastre o edifício primeiro.");
            return;
        }
        try {
            negocioMorador.cadastrarMorador(negocioListaEspera);
            System.out.println("✅ Morador adicionado com sucesso!");
        } catch (ListaEsperaVazia e) {
            System.out.println("⚠ Não há pessoas na lista de espera.");
        } catch (NenhumQuartoLivreException e) {
            System.out.println("⚠ Não há quartos disponíveis.");
        }
    }

    private void removerMorador() {
        System.out.print("Digite o CPF do morador a ser removido: ");
        String cpf = scanner.nextLine();
        try {
            Morador m = negocioMorador.buscarMorador(cpf);
            if (m == null) {
                throw new PessoaNaoEncontrada();
            }
            negocioMorador.removerMorador(cpf);
            System.out.println("✅ Morador removido com sucesso!");
        } catch (PessoaNaoEncontrada e) {
            System.out.println("⚠ Morador não encontrado.");
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void adicionarReclamacao() {
        System.out.print("Digite o CPF do morador: ");
        String cpf = scanner.nextLine();
        System.out.print("Digite a reclamação: ");
        String reclamacao = scanner.nextLine();

        try {
            negocioMorador.adicionarReclamacao(cpf, reclamacao);
            System.out.println("✅ Reclamação registrada com sucesso!");
        } catch (PessoaNaoEncontrada e) {
            System.out.println("⚠ Morador não encontrado.");
        }
    }
}
