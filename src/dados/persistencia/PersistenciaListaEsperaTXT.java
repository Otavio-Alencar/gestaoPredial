package dados.persistencia;

import negocio.entidade.PessoaListaEspera;
import negocio.entidade.ListaEspera;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PersistenciaListaEsperaTXT extends PersistenciaTXTBase<ListaEspera> {

    public PersistenciaListaEsperaTXT(String caminhoArquivo) {
        super(caminhoArquivo);
    }

    @Override
    public ListaEspera carregar() {
        Map<String, String> mapa = lerArquivo();
        ListaEspera lista = new ListaEspera();

        int total = Integer.parseInt(mapa.getOrDefault("total", "0"));
        for (int i = 1; i <= total; i++) {
            String prefixo = "pessoa." + i;
            PessoaListaEspera p = new PessoaListaEspera(
                    mapa.get(prefixo + ".nome"),
                    mapa.get(prefixo + ".cpf"),
                    mapa.get(prefixo + ".contato"),
                    Boolean.parseBoolean(mapa.getOrDefault(prefixo + ".ppi", "false")),
                    Boolean.parseBoolean(mapa.getOrDefault(prefixo + ".quilombola", "false")),
                    Boolean.parseBoolean(mapa.getOrDefault(prefixo + ".pcd", "false")),
                    Boolean.parseBoolean(mapa.getOrDefault(prefixo + ".escolaPublica", "false")),
                    Boolean.parseBoolean(mapa.getOrDefault(prefixo + ".baixaRenda", "false")),
                    Integer.parseInt(mapa.getOrDefault(prefixo + ".ordem", String.valueOf(i)))
            );
            lista.getListaEspera().add(p);
        }
        return lista;
    }

    @Override
    public void salvar(ListaEspera lista) {
        try (BufferedWriter bw = abrirWriter()) {
            bw.write("# Arquivo de persistência da Lista de Espera\n");
            List<PessoaListaEspera> pessoas = lista.getListaEspera();
            bw.write("total=" + pessoas.size() + "\n");

            for (int i = 0; i < pessoas.size(); i++) {
                PessoaListaEspera p = pessoas.get(i);
                String prefixo = "pessoa." + (i + 1);
                bw.write(prefixo + ".nome=" + p.getNome() + "\n");
                bw.write(prefixo + ".cpf=" + p.getCpf() + "\n");
                bw.write(prefixo + ".contato=" + p.getContato() + "\n");
                bw.write(prefixo + ".ppi=" + p.isPpi() + "\n");
                bw.write(prefixo + ".quilombola=" + p.isQuilombola() + "\n");
                bw.write(prefixo + ".pcd=" + p.isPcd() + "\n");
                bw.write(prefixo + ".escolaPublica=" + p.isEscolaPublica() + "\n");
                bw.write(prefixo + ".baixaRenda=" + p.isBaixaRenda() + "\n");
                bw.write(prefixo + ".ordem=" + p.getOrdemChegada() + "\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Erro ao salvar arquivo da lista de espera.");
        }
    }
}
