package dados.persistencia;

import negocio.entidade.Edificio;
import negocio.entidade.Endereco;
import negocio.entidade.Quarto;
import negocio.entidade.Morador;
import negocio.enums.StatusEdificio;
import negocio.enums.StatusQuarto;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PersistenciaEdificioTXT {

    private final Path arquivo;

    public PersistenciaEdificioTXT(String caminhoArquivo) {
        this.arquivo = Paths.get(caminhoArquivo);
        try {
            Files.createDirectories(arquivo.getParent()); // cria pasta se não existir
            if (!Files.exists(arquivo)) Files.createFile(arquivo); // cria arquivo se não existir
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Erro ao criar pasta/arquivo de persistência do edifício.");
        }
    }

    public Path getArquivoPath() {
        return arquivo;
    }

    public Edificio carregar() {
        if (!Files.exists(arquivo)) return null;

        try (BufferedReader br = Files.newBufferedReader(arquivo)) {
            Map<String, String> mapa = new LinkedHashMap<>();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) continue;
                int i = line.indexOf('=');
                if (i > 0) {
                    mapa.put(line.substring(0, i).trim(), line.substring(i + 1).trim());
                }
            }

            String imovel = mapa.get("imovel");
            String descricao = mapa.get("descricao");
            int quantidade = Integer.parseInt(mapa.getOrDefault("quantidadeDeQuartos", "0"));

            String rua = mapa.get("endereco.rua");
            String numero = mapa.get("endereco.numero");
            String bairro = mapa.get("endereco.bairro");
            String cidade = mapa.get("endereco.cidade");
            String estado = mapa.get("endereco.estado");
            Endereco endereco = new Endereco(rua, numero, bairro, cidade, estado);

            Edificio e = new Edificio(imovel, descricao, quantidade, endereco);
            String status = mapa.getOrDefault("status", "ATIVO");
            e.setStatus(StatusEdificio.valueOf(status));

            // carregar detalhes dos quartos e moradores
            for (Quarto q : e.getQuartos()) {
                String prefixo = "quarto." + q.getIdQuarto();
                boolean ocupado = Boolean.parseBoolean(mapa.getOrDefault(prefixo + ".ocupado", "false"));
                q.setStatus(ocupado ? StatusQuarto.OCUPADO : StatusQuarto.LIVRE);

                if (ocupado) {
                    String nome = mapa.get(prefixo + ".morador.nome");
                    String cpf = mapa.get(prefixo + ".morador.cpf");
                    String contato = mapa.get(prefixo + ".morador.contato");
                    Morador morador = new Morador(nome, cpf, contato);

                    // carregar reclamações
                    int qtdReclamacoes = Integer.parseInt(mapa.getOrDefault(prefixo + ".morador.reclamacoes.quantidade", "0"));
                    for (int i = 0; i < qtdReclamacoes; i++) {
                        String reclamacao = mapa.get(prefixo + ".morador.reclamacoes." + i);
                        morador.adicionarReclamacao(reclamacao);
                    }

                    q.ocupar(morador);
                }
            }

            return e;

        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
            return null;
        }
    }



    public void salvar(Edificio e) {
        try {
            Files.createDirectories(arquivo.getParent());
            try (BufferedWriter bw = Files.newBufferedWriter(arquivo)) {
                bw.write("# Arquivo de persistência do Edificio\n");
                bw.write("imovel=" + e.getImovel() + "\n");
                bw.write("descricao=" + e.getDescricao() + "\n");
                bw.write("quantidadeDeQuartos=" + e.getQuantidadeDeQuartos() + "\n");
                bw.write("status=" + e.getStatus() + "\n");

                Endereco end = e.getEndereco();
                bw.write("endereco.rua=" + end.getRua() + "\n");
                bw.write("endereco.numero=" + end.getNumero() + "\n");
                bw.write("endereco.bairro=" + end.getBairro() + "\n");

                for (Quarto q : e.getQuartos()) {
                    String prefixo = "quarto." + q.getIdQuarto();
                    bw.write(prefixo + ".ocupado=" + q.isOcupado() + "\n");

                    if (q.isOcupado() && q.getMorador() != null) {
                        Morador m = q.getMorador();
                        bw.write(prefixo + ".morador.nome=" + m.getNome() + "\n");
                        bw.write(prefixo + ".morador.cpf=" + m.getCpf() + "\n");
                        bw.write(prefixo + ".morador.contato=" + m.getContato() + "\n");

                        // salvar reclamações
                        bw.write(prefixo + ".morador.reclamacoes.quantidade=" + m.getReclamacoes().size() + "\n");
                        for (int i = 0; i < m.getReclamacoes().size(); i++) {
                            bw.write(prefixo + ".morador.reclamacoes." + i + "=" + m.getReclamacoes().get(i) + "\n");
                        }
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Erro ao salvar arquivo de persistência do edifício.");
        }
    }

}
