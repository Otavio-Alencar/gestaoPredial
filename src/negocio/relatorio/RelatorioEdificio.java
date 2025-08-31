package negocio.relatorio;

import negocio.entidade.Edificio;
import negocio.entidade.Quarto;

public class RelatorioEdificio extends RelatorioBase {

    private final Edificio edificio;

    public RelatorioEdificio(Edificio edificio, String caminhoArquivo) {
        super(caminhoArquivo);
        this.edificio = edificio;
    }

    @Override
    protected void preencherDados() {
        // Título
        adicionarTitulo("RELATÓRIO DO EDIFÍCIO");
        adicionarEspaco(1);

        // Informações do edifício
        adicionarSubtitulo("Informações do Edifício");
        adicionarLinha("Nome: " + edificio.getImovel());
        adicionarLinha("Descrição: " + edificio.getDescricao());
        adicionarLinha("Quantidade de quartos: " + edificio.getQuantidadeDeQuartos());
        adicionarEspaco(1);

        // Endereço
        if (edificio.getEndereco() != null) {
            adicionarSubtitulo("Endereço");
            adicionarLinha("CEP: " + edificio.getEndereco().getCep());
            adicionarLinha("Rua: " + edificio.getEndereco().getRua());
            adicionarLinha("Bairro: " + edificio.getEndereco().getBairro());
            adicionarLinha("Número: " + edificio.getEndereco().getNumero());
            adicionarLinha("Complemento: " + edificio.getEndereco().getComplemento());
            adicionarEspaco(1);
        }

        // Quartos
        if (edificio.getQuartos() != null && !edificio.getQuartos().isEmpty()) {
            adicionarSubtitulo("Quartos e Moradores");
            for (Quarto quarto : edificio.getQuartos()) {
                String moradorInfo;
                if (quarto.getMorador() != null) {
                    moradorInfo = "Morador: " + quarto.getMorador().getNome() +
                            " | CPF: " + quarto.getMorador().getCpf() +
                            " | Reclamações: " + quarto.getMorador().getNumReclamacoes()+
                            " | Status: " + quarto.getMorador().getStatus();
                } else {
                    moradorInfo = "Morador: Disponível";
                }

                adicionarLinha("Quarto ID: " + quarto.getIdQuarto());
                adicionarLinha(moradorInfo);
                adicionarEspaco(1); // espaçamento entre quartos
            }
        }

        // Rodapé
        adicionarLinhaItalico("Este relatório foi gerado automaticamente pelo sistema de gestão predial.");
    }
}
