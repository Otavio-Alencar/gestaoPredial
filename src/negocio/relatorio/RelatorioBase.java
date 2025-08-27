//package negocio.relatorio;
//
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.apache.poi.xwpf.usermodel.XWPFParagraph;
//import org.apache.poi.xwpf.usermodel.XWPFRun;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//public abstract class RelatorioBase {
//
//    protected XWPFDocument documento;
//    protected String caminhoArquivo;
//
//    public RelatorioBase(String caminhoArquivo) {
//        this.caminhoArquivo = caminhoArquivo;
//        documento = new XWPFDocument();
//    }
//
//    // Método que cada relatório deve implementar
//    protected abstract void preencherDados();
//
//    // Cria o arquivo .docx
//    public void gerar() {
//        preencherDados(); // popula o documento
//        try (FileOutputStream out = new FileOutputStream(caminhoArquivo)) {
//            documento.write(out);
//            System.out.println("✅ Relatório gerado em: " + caminhoArquivo);
//        } catch (IOException e) {
//            System.out.println("❌ Erro ao gerar relatório: " + e.getMessage());
//        }
//    }
//
//    // Método auxiliar para adicionar título
//    protected void adicionarTitulo(String titulo) {
//        XWPFParagraph paragrafo = documento.createParagraph();
//        XWPFRun run = paragrafo.createRun();
//        run.setText(titulo);
//        run.setBold(true);
//        run.setFontSize(16);
//    }
//
//    // Método auxiliar para adicionar linha de texto
//    protected void adicionarLinha(String texto) {
//        XWPFParagraph paragrafo = documento.createParagraph();
//        XWPFRun run = paragrafo.createRun();
//        run.setText(texto);
//        run.setFontSize(12);
//    }
//}
