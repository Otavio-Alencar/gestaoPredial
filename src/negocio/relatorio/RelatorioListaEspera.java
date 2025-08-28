package negocio.relatorio;

import negocio.entidade.PessoaListaEspera;
import negocio.NegocioListaEspera;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.util.List;

public class RelatorioListaEspera extends RelatorioBase {

    private final NegocioListaEspera listaEspera;

    public RelatorioListaEspera(NegocioListaEspera listaEspera, String caminhoArquivo) {
        super(caminhoArquivo);
        this.listaEspera = listaEspera;
    }

    @Override
    protected void preencherDados() {
        // Título
        adicionarTitulo("RELATÓRIO DA LISTA DE ESPERA");
        adicionarEspaco(1);

        List<PessoaListaEspera> pessoas = listaEspera.listarFila();
        if (pessoas == null || pessoas.isEmpty()) {
            adicionarLinha("Nenhuma pessoa cadastrada na lista de espera.");
            return;
        }

        // Cabeçalho da tabela
        String[] colunas = {"Nome", "CPF", "Contato", "PPI", "Quilombola", "PCD", "Escola Pública", "Baixa Renda"};
        XSSFRow cabecalho = sheet.createRow(linhaAtual++);
        for (int i = 0; i < colunas.length; i++) {
            XSSFCell cell = cabecalho.createCell(i);
            cell.setCellValue(colunas[i]);

            CellStyle estilo = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            font.setFontHeightInPoints((short) 12);
            estilo.setFont(font);
            estilo.setAlignment(HorizontalAlignment.CENTER);
            estilo.setVerticalAlignment(VerticalAlignment.CENTER);
            estilo.setWrapText(true); // quebra de linha automática
            cell.setCellStyle(estilo);

            sheet.setColumnWidth(i, 5000); // largura das colunas
        }

        // Preenche os dados
        for (PessoaListaEspera p : pessoas) {
            XSSFRow row = sheet.createRow(linhaAtual++);

            String[] dados = {
                    p.getNome(),
                    p.getCpf(),
                    p.getContato(),
                    p.isPpi() ? "Sim" : "Não",
                    p.isQuilombola() ? "Sim" : "Não",
                    p.isPcd() ? "Sim" : "Não",
                    p.isEscolaPublica() ? "Sim" : "Não",
                    p.isBaixaRenda() ? "Sim" : "Não"
            };

            for (int i = 0; i < dados.length; i++) {
                XSSFCell cell = row.createCell(i);
                cell.setCellValue(dados[i]);

                CellStyle estilo = workbook.createCellStyle();
                XSSFFont font = workbook.createFont();
                font.setFontHeightInPoints((short) 12);
                estilo.setFont(font);
                estilo.setAlignment(HorizontalAlignment.CENTER);
                estilo.setVerticalAlignment(VerticalAlignment.CENTER);
                estilo.setWrapText(true); // quebra de linha automática

                // Adiciona padding visual: preenchimento interno
                estilo.setBorderTop(BorderStyle.THIN);
                estilo.setBorderBottom(BorderStyle.THIN);
                estilo.setBorderLeft(BorderStyle.THIN);
                estilo.setBorderRight(BorderStyle.THIN);

                cell.setCellStyle(estilo);
            }
        }

        // Rodapé
        adicionarEspaco(1);
        adicionarLinhaItalico("Este relatório foi gerado automaticamente pelo sistema de gestão predial.");
    }
}
