package dados.persistencia;

import java.io.*;
import java.nio.file.*;
import java.util.Map;
import java.util.LinkedHashMap;

public abstract class PersistenciaTXTBase<T> {

    protected final Path arquivo;

    public PersistenciaTXTBase(String caminhoArquivo) {
        this.arquivo = Paths.get(caminhoArquivo);
        try {
            Files.createDirectories(arquivo.getParent()); // cria pasta se não existir
            if (!Files.exists(arquivo)) Files.createFile(arquivo); // cria arquivo se não existir
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Erro ao criar pasta/arquivo de persistência.");
        }
    }

    public Path getArquivoPath() {
        return arquivo;
    }

    // Cada classe concreta precisa implementar como carregar o objeto T
    public abstract T carregar();

    // Cada classe concreta precisa implementar como salvar o objeto T
    public abstract void salvar(T objeto);

    // Métodos auxiliares para ler o arquivo em mapa chave=valor
    protected Map<String, String> lerArquivo() {
        Map<String, String> mapa = new LinkedHashMap<>();
        if (!Files.exists(arquivo)) return mapa;

        try (BufferedReader br = Files.newBufferedReader(arquivo)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) continue;
                int i = line.indexOf('=');
                if (i > 0) {
                    mapa.put(line.substring(0, i).trim(), line.substring(i + 1).trim());
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return mapa;
    }

    // Método auxiliar para abrir BufferedWriter
    protected BufferedWriter abrirWriter() throws IOException {
        Files.createDirectories(arquivo.getParent());
        return Files.newBufferedWriter(arquivo);
    }
}
