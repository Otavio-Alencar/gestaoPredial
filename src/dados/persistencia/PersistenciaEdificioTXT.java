package dados.persistencia;

import negocio.entidade.Edificio;
import negocio.entidade.Endereco;
import negocio.entidade.Quarto;
import negocio.entidade.Morador;
import negocio.enums.StatusEdificio;
import negocio.enums.StatusQuarto;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

public class PersistenciaEdificioTXT extends PersistenciaTXTBase<Edificio> {

    public PersistenciaEdificioTXT(String caminhoArquivo) {
        super(caminhoArquivo);
    }

    @Override
    public Edificio carregar() {
        Map<String, String> mapa = lerArquivo();

        String imovel = mapa.get("imovel");
        String descricao = mapa.get("descricao");
        int quantidade = Integer.parseInt(mapa.getOrDefault("quantidadeDeQuartos", "0"));

        Endereco endereco = new Endereco(
                mapa.get("endereco.rua"),
                mapa.get("endereco.numero"),
                mapa.get("endereco.bairro"),
                mapa.get("endereco.cidade"),
                mapa.get("endereco.estado")
        );

        Edificio e = new Edificio(imovel, descricao, quantidade, endereco);
        e.setStatus(StatusEdificio.valueOf(mapa.getOrDefault("status", "ATIVO")));

        for (Quarto q : e.getQuartos()) {
            String prefixo = "quarto." + q.getIdQuarto();
            boolean ocupado = Boolean.parseBoolean(mapa.getOrDefault(prefixo + ".ocupado", "false"));
            q.setStatus(ocupado ? StatusQuarto.OCUPADO : StatusQuarto.LIVRE);

            if (ocupado) {
                Morador m = new Morador(
                        mapa.get(prefixo + ".morador.nome"),
                        mapa.get(prefixo + ".morador.cpf"),
                        mapa.get(prefixo + ".morador.contato")
                );

                int qtdReclamacoes = Integer.parseInt(mapa.getOrDefault(prefixo + ".morador.reclamacoes.quantidade", "0"));
                for (int i = 0; i < qtdReclamacoes; i++) {
                    m.adicionarReclamacao(mapa.get(prefixo + ".morador.reclamacoes." + i));
                }

                q.ocupar(m);
            }
        }

        return e;
    }

    @Override
    public void salvar(Edificio e) {
        try (BufferedWriter bw = abrirWriter()) {
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

                    bw.write(prefixo + ".morador.reclamacoes.quantidade=" + m.getReclamacoes().size() + "\n");
                    for (int i = 0; i < m.getReclamacoes().size(); i++) {
                        bw.write(prefixo + ".morador.reclamacoes." + i + "=" + m.getReclamacoes().get(i) + "\n");
                    }
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Erro ao salvar arquivo do edifício.");
        }
    }
}
