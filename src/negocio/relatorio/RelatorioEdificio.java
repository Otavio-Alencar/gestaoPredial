//package negocio.relatorio;
//
//import negocio.entidade.Edificio;
//
//public class RelatorioEdificio extends RelatorioBase {
//
//    private final Edificio edificio;
//
//    public RelatorioEdificio(Edificio edificio, String caminhoArquivo) {
//        super(caminhoArquivo);
//        this.edificio = edificio;
//    }
//
//    @Override
//    protected void preencherDados() {
//        adicionarTitulo("=== RELATÓRIO DO EDIFÍCIO ===");
//        adicionarLinha("Nome: " + edificio.getImovel());
//        adicionarLinha("Descrição: " + edificio.getDescricao());
//        adicionarLinha("Quantidade de quartos: " + edificio.getQuantidadeDeQuartos());
//        if (edificio.getEndereco() != null) {
//            adicionarLinha("Endereço:");
//            adicionarLinha("  CEP: " + edificio.getEndereco().getCep());
//            adicionarLinha("  Rua: " + edificio.getEndereco().getRua());
//            adicionarLinha("  Bairro: " + edificio.getEndereco().getBairro());
//            adicionarLinha("  Número: " + edificio.getEndereco().getNumero());
//            adicionarLinha("  Complemento: " + edificio.getEndereco().getComplemento());
//        }
//    }
//}
