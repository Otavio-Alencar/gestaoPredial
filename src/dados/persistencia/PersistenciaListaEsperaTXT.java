package dados.persistencia;

import negocio.entidade.PessoaListaEspera;
import negocio.entidade.ListaEspera;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PersistenciaListaEsperaTXT {

    private final Path arquivo;

    public PersistenciaListaEsperaTXT(String caminhoArquivo) {
        this.arquivo = Paths.get(caminhoArquivo);
        try {
            Files.createDirectories(arquivo.getParent());
            if (!Files.exists(arquivo)) Files.createFile(arquivo);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Erro ao criar pasta/arquivo de persistência da lista de espera.");
        }
    }

    public Path getArquivoPath() {
        return arquivo;
    }

    public ListaEspera carregar() {
        if (!Files.exists(arquivo)) return new ListaEspera();

        ListaEspera lista = new ListaEspera();
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

            int total = Integer.parseInt(mapa.getOrDefault("total", "0"));
            for (int i = 1; i <= total; i++) {
                String prefixo = "pessoa." + i;
                String nome = mapa.get(prefixo + ".nome");
                String cpf = mapa.get(prefixo + ".cpf");
                String contato = mapa.get(prefixo + ".contato");
                boolean ppi = Boolean.parseBoolean(mapa.getOrDefault(prefixo + ".ppi", "false"));
                boolean quilombola = Boolean.parseBoolean(mapa.getOrDefault(prefixo + ".quilombola", "false"));
                boolean pcd = Boolean.parseBoolean(mapa.getOrDefault(prefixo + ".pcd", "false"));
                boolean escolaPublica = Boolean.parseBoolean(mapa.getOrDefault(prefixo + ".escolaPublica", "false"));
                boolean baixaRenda = Boolean.parseBoolean(mapa.getOrDefault(prefixo + ".baixaRenda", "false"));
                int ordem = Integer.parseInt(mapa.getOrDefault(prefixo + ".ordem", String.valueOf(i)));

                PessoaListaEspera pessoa = new PessoaListaEspera(
                        nome, cpf, contato, ppi, quilombola, pcd, escolaPublica, baixaRenda, ordem
                );
                lista.getListaEspera().add(pessoa);
            }

        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    public void salvar(ListaEspera lista) {
        try {
            Files.createDirectories(arquivo.getParent());
            try (BufferedWriter bw = Files.newBufferedWriter(arquivo)) {
                bw.write("# Arquivo de persistência da Lista de Espera\n");
                bw.write("total=" + lista.getListaEspera().size() + "\n");

                int i = 1;
                for (PessoaListaEspera p : lista.getListaEspera()) {
                    String prefixo = "pessoa." + i;
                    bw.write(prefixo + ".nome=" + p.getNome() + "\n");
                    bw.write(prefixo + ".cpf=" + p.getCpf() + "\n");
                    bw.write(prefixo + ".contato=" + p.getContato() + "\n");
                    bw.write(prefixo + ".ppi=" + p.isPpi() + "\n");
                    bw.write(prefixo + ".quilombola=" + p.isQuilombola() + "\n");
                    bw.write(prefixo + ".pcd=" + p.isPcd() + "\n");
                    bw.write(prefixo + ".escolaPublica=" + p.isEscolaPublica() + "\n");
                    bw.write(prefixo + ".baixaRenda=" + p.isBaixaRenda() + "\n");
                    bw.write(prefixo + ".ordem=" + p.getOrdemChegada() + "\n");
                    i++;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Erro ao salvar arquivo de persistência da lista de espera.");
        }
    }
}
