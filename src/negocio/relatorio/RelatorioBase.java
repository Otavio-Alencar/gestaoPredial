package negocio.relatorio;

import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;

public abstract class RelatorioBase {

    protected XSSFWorkbook workbook;
    protected XSSFSheet sheet;
    protected String caminhoArquivo;
    protected int linhaAtual;

    public RelatorioBase(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
        this.workbook = new XSSFWorkbook();
        this.sheet = workbook.createSheet("Relatório");
        this.linhaAtual = 0;
    }

    // Cada relatório implementa sua própria lógica
    protected abstract void preencherDados();

    // Cria o arquivo .xlsx
    public void gerar() {
        preencherDados();
        try (FileOutputStream out = new FileOutputStream(caminhoArquivo)) {
            workbook.write(out);
            System.out.println("✅ Relatório gerado em: " + caminhoArquivo);
        } catch (IOException e) {
            System.out.println("❌ Erro ao gerar relatório: " + e.getMessage());
        } finally {
            try {
                workbook.close();
            } catch (IOException ignored) {}
        }
    }

    // ==================== MÉTODOS DE FORMATAÇÃO ====================

    // Adiciona título (linha única, negrito, centralizado)
    protected void adicionarTitulo(String titulo) {
        XSSFRow row = sheet.createRow(linhaAtual++);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue(titulo);

        CellStyle estilo = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 20);
        estilo.setFont(font);
        estilo.setAlignment(HorizontalAlignment.CENTER);
        cell.setCellStyle(estilo);

        // Mescla células para centralizar
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(
                row.getRowNum(), row.getRowNum(), 0, 7
        ));
    }

    // Adiciona subtítulo (negrito, tamanho médio)
    protected void adicionarSubtitulo(String subtitulo) {
        XSSFRow row = sheet.createRow(linhaAtual++);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue(subtitulo);

        CellStyle estilo = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        estilo.setFont(font);
        estilo.setAlignment(HorizontalAlignment.LEFT);
        cell.setCellStyle(estilo);

        // Mescla células para centralizar na linha
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(
                row.getRowNum(), row.getRowNum(), 0, 7
        ));
    }

    // Adiciona linha normal
    protected void adicionarLinha(String texto) {
        XSSFRow row = sheet.createRow(linhaAtual++);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue(texto);

        CellStyle estilo = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        estilo.setFont(font);
        cell.setCellStyle(estilo);

        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(
                row.getRowNum(), row.getRowNum(), 0, 7
        ));
    }

    // Adiciona linha em itálico
    protected void adicionarLinhaItalico(String texto) {
        XSSFRow row = sheet.createRow(linhaAtual++);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue(texto);

        CellStyle estilo = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setItalic(true);
        font.setFontHeightInPoints((short) 12);
        estilo.setFont(font);
        cell.setCellStyle(estilo);

        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(
                row.getRowNum(), row.getRowNum(), 0, 7
        ));
    }

    // Espaço em branco
    protected void adicionarEspaco(int linhas) {
        linhaAtual += linhas;
    }
}
