package negocio.relatorio;

import org.apache.poi.xwpf.usermodel.*;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class RelatorioBase {

    protected XWPFDocument documento;
    protected String caminhoArquivo;

    public RelatorioBase(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
        documento = new XWPFDocument();
    }

    // Método que cada relatório deve implementar
    protected abstract void preencherDados();

    // Cria o arquivo .docx
    public void gerar() {
        preencherDados(); // popula o documento
        try (FileOutputStream out = new FileOutputStream(caminhoArquivo)) {
            documento.write(out);
            System.out.println("✅ Relatório gerado em: " + caminhoArquivo);
        } catch (IOException e) {
            System.out.println("❌ Erro ao gerar relatório: " + e.getMessage());
        }
    }

    // ==================== MÉTODOS DE FORMATAÇÃO ====================

    // Título principal (centralizado, grande e negrito)
    protected void adicionarTitulo(String titulo) {
        XWPFParagraph paragrafo = documento.createParagraph();
        paragrafo.setAlignment(ParagraphAlignment.CENTER);
        paragrafo.setSpacingAfter(200); // espaço após o parágrafo

        XWPFRun run = paragrafo.createRun();
        run.setText(titulo);
        run.setBold(true);
        run.setFontSize(20);
    }

    // Subtítulo (negrito, tamanho médio, alinhamento à esquerda)
    protected void adicionarSubtitulo(String subtitulo) {
        XWPFParagraph paragrafo = documento.createParagraph();
        paragrafo.setAlignment(ParagraphAlignment.LEFT);
        paragrafo.setSpacingBefore(150);
        paragrafo.setSpacingAfter(100);

        XWPFRun run = paragrafo.createRun();
        run.setText(subtitulo);
        run.setBold(true);
        run.setFontSize(14);
    }

    // Linha de texto normal
    protected void adicionarLinha(String texto) {
        XWPFParagraph paragrafo = documento.createParagraph();
        paragrafo.setAlignment(ParagraphAlignment.LEFT);
        paragrafo.setSpacingAfter(100);

        XWPFRun run = paragrafo.createRun();
        run.setText(texto);
        run.setFontSize(12);
    }

    // Linha de texto em itálico (opcional)
    protected void adicionarLinhaItalico(String texto) {
        XWPFParagraph paragrafo = documento.createParagraph();
        paragrafo.setAlignment(ParagraphAlignment.LEFT);
        paragrafo.setSpacingAfter(100);

        XWPFRun run = paragrafo.createRun();
        run.setText(texto);
        run.setItalic(true);
        run.setFontSize(12);
    }

    // Espaço em branco (para separar seções)
    protected void adicionarEspaco(int pontos) {
        XWPFParagraph paragrafo = documento.createParagraph();
        paragrafo.setSpacingAfter(pontos);
    }
}
