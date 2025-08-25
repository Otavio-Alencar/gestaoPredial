package dados.persistencia;

import negocio.entidade.Edificio;
import negocio.entidade.Endereco;
import negocio.entidade.Quarto;
import negocio.entidade.Morador;
import negocio.enums.StatusEdificio;

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

            // carregar ocupação dos quartos e associar moradores
            for (Quarto q : e.getQuartos()) {
                String ocupado = mapa.get("quarto." + q.getIdQuarto() + ".ocupado");
                String nomeMorador = mapa.get("quarto." + q.getIdQuarto() + ".morador");
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
                    bw.write("quarto." + q.getIdQuarto() + ".ocupado=" + q.isOcupado() + "\n");
                    if (q.isOcupado() && q.getMorador() != null) {
                        bw.write("quarto." + q.getIdQuarto() + ".morador=" + q.getMorador().getNome() + "\n");
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Erro ao salvar arquivo de persistência do edifício.");
        }
    }
}
