package br.com.tecnoDesk.TecnoDesk.Services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.stereotype.Service;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import br.com.tecnoDesk.TecnoDesk.Entities.OS;

@Service
public class GeneratePDfService {

	public byte[] gerarPdfOsentrada() throws DocumentException, IOException {
	try (ByteArrayOutputStream bytes = new ByteArrayOutputStream()) {	
		
		Document documento = new Document();
		
		PdfWriter.getInstance(documento, bytes);
			
			
			documento.open();
			
			PdfPTable headerTable = new PdfPTable(1);
            headerTable.setWidthPercentage(100);
			
			Font headerFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Font labelFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);

            
            Paragraph title = new Paragraph("Ordem de Serviço", headerFont);
            title.setAlignment(Element.ALIGN_CENTER);
            documento.add(title);

            documento.add(new Paragraph(" "));

            
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);

            
            addTableHeader(table, "Nome do Cliente", "João Silva", labelFont, normalFont);
            addTableHeader(table, "Número do Pedido", "12345", labelFont, normalFont);
            addTableHeader(table, "Telefone Cliente", "(11) 98765-4321", labelFont, normalFont);
            addTableHeader(table, "Ordem Recebida Por", "Maria Oliveira", labelFont, normalFont);
            addTableHeader(table, "Data do Pedido", "02/10/2024", labelFont, normalFont);
            addTableHeader(table, "Descrição do Trabalho", "Reparo de equipamento", labelFont, normalFont);

            
            documento.add(table);

            
            documento.add(new Paragraph(" ")); 
            documento.add(new Paragraph("Descrição do Trabalho:", labelFont));
            documento.add(new Paragraph("O equipamento foi diagnosticado e será realizado o reparo no sistema de refrigeração.", normalFont));

           		 
			documento.close();
		
			return bytes.toByteArray();
	}
				
	}
	
	private void addTableHeader(PdfPTable table, String header, String value, Font headerFont, Font valueFont) {
        PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        valueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(headerCell);
        table.addCell(valueCell);
    }
}
