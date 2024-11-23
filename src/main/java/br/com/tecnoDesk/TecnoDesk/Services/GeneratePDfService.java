package br.com.tecnoDesk.TecnoDesk.Services;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
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

	/*
	 * public byte[] gerarPdfOsentrada(OS_Entrada os,String codEmpresa) throws
	 * Exception {
		-- COMITADO PARA TESTES; 
	 */
		public byte[] gerarPdfOsentrada() throws Exception {
		
		try (ByteArrayOutputStream bytes = new ByteArrayOutputStream()) {
			
			/*
			 * Empresa empresa =
			 * empresaRepository.findEmpresaById(Long.valueOf(decriptService.decriptCodEmp(
			 * codEmpresa)));
			 */
			OS_Entrada os = osRepository.findById(2202);
			Empresa empresa = empresaRepository.findEmpresaById(3);
			
		Document documento = new Document();
		PdfWriter.getInstance(documento, bytes);
		
		//CONFIGURAÇÕES PDF:
		documento.setMargins(20, 20, 20, 20);
		documento.setPageSize(PageSize.A4);
		
		//
		//FONTE
		Font marcadorHeader = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
		Font labelDataHoraEmiss = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
		Font fonteNumOsFont = new Font(Font.FontFamily.HELVETICA,16);
		
		
		//MAIN DIV HEADER. 
		PdfPTable divHeader = new PdfPTable(3);
		divHeader.setWidthPercentage(100);
		divHeader.setSpacingAfter(2);
		
		
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
		
		//SEGUNDA LINHA:  //
		PdfPTable row2 = new PdfPTable(2);
		row2.setWidthPercentage(100);
		float[] row2float = {3f,8f}; 
		row2.setWidths(row2float);

		PdfPTable titleRow2 = new PdfPTable(2);
		titleRow2.setWidthPercentage(100);
		
		PdfPCell titleDataEmiss = new PdfPCell();
		Paragraph vTitleDataEmiss = new Paragraph("Data e hora de emissão: ");
		vTitleDataEmiss.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		vTitleDataEmiss.setFont(new Font(Font.FontFamily.HELVETICA,10,Font.BOLD));
		titleDataEmiss.setBorder(0);
		titleDataEmiss.addElement(vTitleDataEmiss);
		
		PdfPCell titleFuncionamento = new PdfPCell();
		Paragraph vtitleFuncionamento = new Paragraph("Horário de atendimento: ");
		vtitleFuncionamento.setAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
		
		vtitleFuncionamento.setFont(new Font(Font.FontFamily.HELVETICA,10,Font.BOLD));
		titleFuncionamento.addElement(vtitleFuncionamento);
		
		titleFuncionamento.setBorder(0);
		titleFuncionamento.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
		
		
		titleRow2.addCell(titleDataEmiss);
		titleRow2.addCell(titleFuncionamento);
	
		//DATA E HORA DE CRIAÇÃO
		PdfPCell dataEhora = new PdfPCell();
		Paragraph vDataHora = new Paragraph(os.getDataAbertura());
		vDataHora.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		dataEhora.setFixedHeight(4);
		dataEhora.setPadding(0);
		dataEhora.addElement(vDataHora);
		
		row2.addCell(dataEhora);
		
		//***
			
		//HORARIO DE  DE FUNCIONAMENTO 
		PdfPCell horarioFuncionamento = new PdfPCell(new Phrase("Horario funcionamento: "));
		horarioFuncionamento.setBorder(0);
		Paragraph vHorarioFuncionamento = new Paragraph("Nossa loja está aberta de segunda a sexta-feira, das 9h às 19h, e aos sábados, das 9h às 14h. Aos domingos e feriados, permanecemos fechados.");
		vHorarioFuncionamento.setAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
		vHorarioFuncionamento.setFont(new Font(Font.FontFamily.HELVETICA,8,Font.BOLD));
		horarioFuncionamento.addElement(vHorarioFuncionamento);
		
		row2.addCell(horarioFuncionamento);
		
		// DIVISÃO CLIENTE.
		PdfPTable clienteDiv = new PdfPTable(1);
		clienteDiv.setWidthPercentage(100);
		clienteDiv.setSpacingBefore(5);
		
		PdfPCell clienteDivCel = new PdfPCell(new Phrase("Cliente",new Font(Font.FontFamily.HELVETICA,12,Font.BOLD)));
		clienteDivCel.setBackgroundColor(BaseColor.LIGHT_GRAY);
		clienteDivCel.setBorder(0);
		clienteDivCel.setFixedHeight(20);
		clienteDivCel.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		
		
		/*
		 * Paragraph tituloClienteDivCel = new Paragraph("Cliente");
		 * tituloClienteDivCel.setFont(new
		 * Font(Font.FontFamily.HELVETICA,12,Font.BOLD));
		 * tituloClienteDivCel.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		 */
		
	
			
		/* clienteDivCel.addElement(tituloClienteDivCel); */
		clienteDiv.addCell(clienteDivCel);
		
		
		
		
			documento.open();
			
			documento.add(divHeader);
			documento.add(titleRow2);
			documento.add(row2);
			documento.add(clienteDiv);
			
  		 
			documento.close();
		
			return bytes.toByteArray();
	}
				
	}
	
	
}
