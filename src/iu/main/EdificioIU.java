package iu.main;

import dados.sindico.SessaoSindico;
import fachada.Fachada;
import negocio.entidade.Edificio;
import negocio.entidade.Endereco;
import negocio.entidade.Sindico;
import negocio.excecao.SindicoJaTemEdificio;
import negocio.excecao.SindicoNaoLogado;

import java.io.File;
import java.util.Scanner;

public class EdificioIU {

    private final Fachada fachada = new Fachada();
    private final Scanner scanner = new Scanner(System.in);
    private final Sindico sindicoLogado = SessaoSindico.getSindicoLogado();
    public void cadastrarEdificio() {
        System.out.println("\n=== Cadastrar Edifício ===\n");

        String imovel = lerTextoObrigatorio("Digite o nome do imóvel");
        String descricao = lerTextoObrigatorio("Digite a descrição do edifício");
        int quantidadeDeQuartos = lerInteiroPositivo("Digite a quantidade de quartos");

        String cep = lerTextoObrigatorio("Digite o CEP (mínimo 8 caracteres)");
        String rua = lerTextoObrigatorio("Digite a rua");
        String bairro = lerTextoObrigatorio("Digite o bairro");
        String numero = lerTextoObrigatorio("Digite o número");
        System.out.print("Digite o complemento (opcional): ");
        String complemento = scanner.nextLine().trim();

        Endereco endereco = new Endereco(cep, rua, bairro, numero, complemento);
        Edificio novoEdificio = new Edificio(imovel, descricao, quantidadeDeQuartos, endereco);

        try {
            fachada.cadastrarEdificio(sindicoLogado, novoEdificio);
            System.out.println("✅ Edifício cadastrado com sucesso!");
        } catch (SindicoNaoLogado e) {
            System.out.println("⚠️ Erro: nenhum síndico logado.");
        } catch (SindicoJaTemEdificio e) {
            System.out.println("⚠️ Erro: o síndico já possui um edifício cadastrado.");
        } catch (RuntimeException e) {
            System.out.println("⚠️ Erro ao cadastrar edifício: " + e.getMessage());
        }
    }

    public void menuEdificio() {
        if (!fachada.temEdificio()) {
            System.out.println("Nenhum edifício cadastrado.");
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
                case 3 -> removerEdificio(sindicoLogado);
                case 4 -> gerarRelatorio();
                case 5 -> voltar = true;
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private void verInformacoesEdificio() {
        Edificio e = fachada.getEdificio();
        if (e == null) {
            System.out.println("Nenhum edifício cadastrado.");
            return;
        }

        System.out.println("\n=== Informações do Edifício ===");
        System.out.println("Nome: " + e.getImovel());
        System.out.println("Descrição: " + e.getDescricao());
        System.out.println("Quantidade de quartos: " + e.getQuantidadeDeQuartos());
        System.out.println("CEP: " + e.getEndereco().getCep());
        System.out.println("Rua: " + e.getEndereco().getRua());
        System.out.println("Bairro: " + e.getEndereco().getBairro());
        System.out.println("Número: " + e.getEndereco().getNumero());
        System.out.println("Complemento: " + e.getEndereco().getComplemento());
    }

    private void editarEdificio() {
        Edificio edificio = fachada.getEdificio();
        if (edificio == null) {
            System.out.println("Nenhum edifício cadastrado para edição.");
            return;
        }

        System.out.println("\n=== Editar Edifício ===");
        System.out.println("Deixe em branco para manter o valor atual.\n");

        // Nome
        System.out.print("Nome atual (" + edificio.getImovel() + "): ");
        String novoNome = scanner.nextLine().trim();
        if (!novoNome.isEmpty()) edificio.setImovel(novoNome);

        // Descrição
        System.out.print("Descrição atual (" + edificio.getDescricao() + "): ");
        String novaDescricao = scanner.nextLine().trim();
        if (!novaDescricao.isEmpty()) edificio.setDescricao(novaDescricao);

        // Quantidade de quartos
        System.out.print("Quantidade de quartos atual (" + edificio.getQuantidadeDeQuartos() + "): ");
        String qtdInput = scanner.nextLine().trim();
        if (!qtdInput.isEmpty()) {
            try {
                int novaQtd = Integer.parseInt(qtdInput);
                if (novaQtd > 0) edificio.setQuantidadeDeQuartos(novaQtd);
                else System.out.println("Quantidade inválida! Mantido valor atual.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Mantido valor atual.");
            }
        }

        // Endereço
        Endereco endereco = edificio.getEndereco();
        System.out.print("CEP atual (" + endereco.getCep() + "): ");
        String novoCep = scanner.nextLine().trim();
        if (!novoCep.isEmpty()) endereco.setCep(novoCep);

        System.out.print("Rua atual (" + endereco.getRua() + "): ");
        String novaRua = scanner.nextLine().trim();
        if (!novaRua.isEmpty()) endereco.setRua(novaRua);

        System.out.print("Bairro atual (" + endereco.getBairro() + "): ");
        String novoBairro = scanner.nextLine().trim();
        if (!novoBairro.isEmpty()) endereco.setBairro(novoBairro);

        System.out.print("Número atual (" + endereco.getNumero() + "): ");
        String novoNumero = scanner.nextLine().trim();
        if (!novoNumero.isEmpty()) endereco.setNumero(novoNumero);

        System.out.print("Complemento atual (" + endereco.getComplemento() + "): ");
        String novoComplemento = scanner.nextLine().trim();
        if (!novoComplemento.isEmpty()) endereco.setComplemento(novoComplemento);

        System.out.println("✅ Edifício atualizado com sucesso!");
    }

    private void removerEdificio(Sindico sindicoLogado) {
        try {
            fachada.removerEdificio(sindicoLogado);
            System.out.println("✅ Edifício removido com sucesso!");
        } catch (SindicoNaoLogado e) {
            System.out.println("⚠️ Erro: " + e.getMessage());
        }
    }

    private void gerarRelatorio() {
        Edificio e = fachada.getEdificio();
        if (e == null) {
            System.out.println("Nenhum edifício cadastrado para gerar relatório.");
        } else {
            try {
                String home = System.getProperty("user.home");
                File pasta = new File(home + "/DocumentosGestaoPredial");
                if (!pasta.exists()) {
                    pasta.mkdirs(); // cria a pasta se não existir
                }
                String caminho = pasta + "/relatorio_edificio.docx";
                fachada.gerarRelatorioEdificio(caminho);
                System.out.println("Relatório gerado com sucesso em: " + caminho);
            } catch (Exception ex) {
                System.out.println("Erro ao gerar o relatório: " + ex.getMessage());
            }
        }
    }

    // ================= Métodos auxiliares =================

    private String lerTextoObrigatorio(String mensagem) {
        String valor;
        do {
            System.out.print(mensagem + ": ");
            valor = scanner.nextLine().trim();
            if (valor.isEmpty()) System.out.println("⚠️ O campo não pode ficar vazio.");
        } while (valor.isEmpty());
        return valor;
    }

    private int lerInteiroPositivo(String mensagem) {
        int valor;
        do {
            System.out.print(mensagem + ": ");
            while (!scanner.hasNextInt()) {
                System.out.println("Digite um número válido.");
                scanner.next();
            }
            valor = scanner.nextInt();
            scanner.nextLine();
            if (valor <= 0) System.out.println("O valor deve ser maior que zero.");
        } while (valor <= 0);
        return valor;
    }
}
