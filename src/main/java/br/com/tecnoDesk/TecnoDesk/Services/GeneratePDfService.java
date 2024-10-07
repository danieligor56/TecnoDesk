package br.com.tecnoDesk.TecnoDesk.Services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.springframework.stereotype.Service;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class GeneratePDfService {

	public byte[] gerarPdfOsentrada() throws DocumentException, IOException {
	try (ByteArrayOutputStream bytes = new ByteArrayOutputStream()) {	
		
		Document documento = new Document();
		PdfWriter.getInstance(documento, bytes);
		
		//CONFIGURAÇÕES PDF:
		documento.setMargins(6, 6, 0, 0);
		//
		
		PdfPTable container = new PdfPTable(1);
				
		
		
		Paragraph title = new Paragraph("Ordem de serviço");
			
		container.addCell(null);
		
		
			documento.open();
			documento.add(container);
			documento.setPageSize(PageSize.A4);
			documento.add(title);
			
			
			
		
            
            
           		 
			documento.close();
		
			return bytes.toByteArray();
	}
				
	}
	
	
}
