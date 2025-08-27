package negocio.relatorio;

import negocio.entidade.Edificio;

public class RelatorioEdificio extends RelatorioBase {

    private final Edificio edificio;

    public RelatorioEdificio(Edificio edificio, String caminhoArquivo) {
        super(caminhoArquivo);
        this.edificio = edificio;
    }

    @Override
    protected void preencherDados() {
        adicionarTitulo("RELATÓRIO DO EDIFÍCIO");
        adicionarEspaco(200);

        adicionarSubtitulo("Informações do Edifício");
        adicionarLinha("Nome: " + edificio.getImovel());
        adicionarLinha("Descrição: " + edificio.getDescricao());
        adicionarLinha("Quantidade de quartos: " + edificio.getQuantidadeDeQuartos());
        adicionarEspaco(150);

        if (edificio.getEndereco() != null) {
            adicionarSubtitulo("Endereço");
            adicionarLinha("CEP: " + edificio.getEndereco().getCep());
            adicionarLinha("Rua: " + edificio.getEndereco().getRua());
            adicionarLinha("Bairro: " + edificio.getEndereco().getBairro());
            adicionarLinha("Número: " + edificio.getEndereco().getNumero());
            adicionarLinha("Complemento: " + edificio.getEndereco().getComplemento());
            adicionarEspaco(150);
        }

        if (edificio.getQuartos() != null && !edificio.getQuartos().isEmpty()) {
            adicionarSubtitulo("Quartos e Moradores");
            for (var quarto : edificio.getQuartos()) {
                StringBuilder info = new StringBuilder();
                info.append("Quarto ID: ").append(quarto.getIdQuarto()).append("\n");

                if (quarto.getMorador() != null) {
                    info.append("Morador: ").append(quarto.getMorador().getNome()).append("\n")
                            .append("CPF: ").append(quarto.getMorador().getCpf()).append("\n")
                            .append("Reclamações: ").append(quarto.getMorador().getNumReclamacoes());
                } else {
                    info.append("Morador: Disponível");
                }

                adicionarLinha(info.toString());
                adicionarEspaco(100); // Espaço entre quartos
            }
        }

        adicionarLinhaItalico("Este relatório foi gerado automaticamente pelo sistema de gestão predial.");
    }
}
