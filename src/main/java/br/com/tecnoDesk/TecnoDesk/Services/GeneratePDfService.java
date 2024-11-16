package br.com.tecnoDesk.TecnoDesk.Services;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDiv;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.tecnoDesk.TecnoDesk.DTO.OS_EntradaDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Entities.OS_Entrada;
import br.com.tecnoDesk.TecnoDesk.Repository.EmpresaRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.OsRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Service
public class GeneratePDfService {
	
	@Autowired
	EmpresaService empresaService;
	
	@Autowired
	EmpresaRepository empresaRepository;
	
	@Autowired
	DecriptService decriptService;
	
	@Autowired
	OsRepository osRepository;
	
	@Autowired
	DocumentUtils documentUtils;

	public byte[] gerarPdfOsentrada(OS_Entrada os,String codEmpresa) throws Exception {
		
		try (ByteArrayOutputStream bytes = new ByteArrayOutputStream()) {
			
		Empresa empresa = empresaRepository.findEmpresaById(Long.valueOf(decriptService.decriptCodEmp(codEmpresa)));
			
		Document documento = new Document();
		PdfWriter.getInstance(documento, bytes);
		
		//CONFIGURAÇÕES PDF:
		documento.setMargins(20, 20, 20, 20);
		documento.setPageSize(PageSize.A4);
		
		//
		
		// DADOS EMPRESA FORMATADO
		
		
		
		//FONTE
		Font marcadorHeader = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
		Font labelDataHoraEmiss = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
		Font fonteNumOsFont = new Font(Font.FontFamily.COURIER,16);
		
		//MAIN DIV HEADER. 
		PdfPTable divHeader = new PdfPTable(3);
		divHeader.setWidthPercentage(100);
		
		/*
		 * divHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
		 * divHeader.setPaddingRight(80);
		 */
		
		
		PdfPCell imgDivHeaderDiv = new PdfPCell();
		imgDivHeaderDiv.setBorder(imgDivHeaderDiv.LEFT | imgDivHeaderDiv.TOP | 
				imgDivHeaderDiv.BOTTOM);
		imgDivHeaderDiv.setBorderColor(BaseColor.LIGHT_GRAY);
		
		
		PdfPCell dadosEmpresa = new PdfPCell();
		dadosEmpresa.setBorder(dadosEmpresa.BOTTOM | dadosEmpresa.TOP);
		dadosEmpresa.setBorderColor(BaseColor.LIGHT_GRAY);
		dadosEmpresa.setPadding(4);
		dadosEmpresa.setHorizontalAlignment(dadosEmpresa.ALIGN_LEFT);
		
		PdfPCell numOSCell  = new PdfPCell();
		numOSCell.setBorderColor(BaseColor.LIGHT_GRAY);
		numOSCell.setHorizontalAlignment(numOSCell.ALIGN_BASELINE);
		
		
		float[] columnWidths = {20f, 60f, 10f}; //30% | 40% | 30%
        divHeader.setWidths(columnWidths);
		
		
		//IMAGEM
		Paragraph imgText = new Paragraph("[LOGO EMPRESA]");
		imgText.setAlignment(imgText.ALIGN_BASELINE);
		imgDivHeaderDiv.addElement(imgText);
		
		/* imgDivHeaderDiv.setDisplay(DisplayType.INLINE); */
		
		//DADOS EMPRESA. 
		Paragraph rzSocial = new Paragraph(empresa.getNomEmpresa());
		rzSocial.setAlignment(rzSocial.ALIGN_RIGHT);
		rzSocial.setFont(marcadorHeader);
		
		Paragraph endereco = new Paragraph(empresa.getLogra() + " , " + empresa.getNum() + " - " + empresa.getBairro() + " - " 
		+ empresa.getMunicipio() + " - " + empresa.getUf() + " - " + "CEP: " + empresa.getCep());
		endereco.setAlignment(endereco.ALIGN_RIGHT);
		
		
		Paragraph dadosEmp = new Paragraph("CNPJ: " + empresa.getDocEmpresa() + " | "+"Tel ("+ empresa.getTel()+")");
		dadosEmp.setAlignment(rzSocial.ALIGN_RIGHT);
		
		Paragraph tituloPage = new Paragraph("Ordem de Serviço");
		tituloPage.setFont(marcadorHeader);
		tituloPage.setAlignment(tituloPage.ALIGN_CENTER);
		tituloPage.setSpacingAfter(5);
		
		dadosEmpresa.addElement(rzSocial); 
		dadosEmpresa.addElement(endereco);
		dadosEmpresa.addElement(dadosEmp);
		dadosEmpresa.addElement(tituloPage);
		
		//OS
		Paragraph NumOs = new Paragraph("O.S :");
		NumOs.setAlignment(NumOs.ALIGN_CENTER);
		NumOs.setFont(marcadorHeader);
		Paragraph codOs = new Paragraph(String.valueOf(os.getNumOs()));
		codOs.setFont(fonteNumOsFont);
		codOs.setAlignment(codOs.ALIGN_CENTER);
		codOs.setSpacingBefore(10);
		
		numOSCell.addElement(NumOs);
		numOSCell.addElement(codOs);
		
		divHeader.addCell(imgDivHeaderDiv);
		divHeader.addCell(dadosEmpresa);
		divHeader.addCell(numOSCell);
		
		//******* // ******
		
		//SEGUNDA LINHA:  
		PdfPTable row2 = new PdfPTable(2);
		row2.setWidthPercentage(100);
		
		//DATA & HORA: 		
		PdfPTable dataHora = new PdfPTable(1);
		PdfDiv tituloDataHoraEmiss = new PdfDiv();	
		
		//CELULAR PARA ABRIGAR DATA E HORA: 
		/* PdfPCell valorDataHora = new PdfPCell(); */
		/* valorDataHora.setBackgroundColor(BaseColor.DARK_GRAY); */	
		
		//TEXTO PARA DIV:
		Paragraph pdfDataHora = new Paragraph("Data & Hora de Emisssão: ");
		pdfDataHora.setFont(labelDataHoraEmiss);
		
		//ADICIONANDOD TITULO A DIV. 
		tituloDataHoraEmiss.addElement(pdfDataHora);
		
		//VALOR FIXO APENAS PARA TESTES...
		Paragraph vdataHora = new Paragraph(os.getDataAbertura());
		
		//INSERINdO VALOR NA CELULA:
		/*
		 * valorDataHora.addElement(tituloDataHoraEmiss);
		 * valorDataHora.addElement(vdataHora);
		 */
		
		//INSERINDO CELL NA TABLE
		/* dataHora.addCell(valorDataHora); */

		row2.addCell(dataHora); 	
		
			documento.open();
			
			documento.add(divHeader);
			/* documento.add(dataHora); */
			documento.add(pdfDataHora);
			
			
		      
           		 
			documento.close();
		
			return bytes.toByteArray();
	}
				
	}
	
	
}
