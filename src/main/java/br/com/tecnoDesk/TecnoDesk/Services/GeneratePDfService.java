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
import br.com.tecnoDesk.TecnoDesk.DTO.TotaisNotaDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Entities.OS_Entrada;
import br.com.tecnoDesk.TecnoDesk.Entities.Orcamento;
import br.com.tecnoDesk.TecnoDesk.Entities.OrcamentoItem;
import br.com.tecnoDesk.TecnoDesk.Entities.OsRapida;
import br.com.tecnoDesk.TecnoDesk.Enuns.Aparelhos;
import br.com.tecnoDesk.TecnoDesk.Enuns.ProdutoServicoEnum;
import br.com.tecnoDesk.TecnoDesk.Repository.EmpresaRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.OrcamentoItemRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.OrcamentoRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.OsRapidaRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.OsRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

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

	@Autowired
	OrcamentoRepository orcamentoRepository;

	@Autowired
	OrcamentoItemRepository orcamentoItemRepository;

	@Autowired
	OrcamentoService orcamentoService;

	@Autowired
	OsRapidaRepository osRapidaRepository;

	public byte[] gerarPdfOsentrada(OS_Entrada osFront, String codEmpresa) throws Exception {

		/* public byte[] gerarPdfOsentrada() throws Exception { */

		try (ByteArrayOutputStream bytes = new ByteArrayOutputStream()) {

			Empresa empresa = empresaRepository.findEmpresaById(Long.valueOf(decriptService.decriptCodEmp(
					codEmpresa)));

			OS_Entrada os = osRepository.findById(osFront.getId());

			Document documento = new Document();
			PdfWriter.getInstance(documento, bytes);

			// CONFIGURAÇÕES PDF:
			documento.setMargins(20, 20, 20, 20);
			documento.setPageSize(PageSize.A4);

			// FONTE
			Font fontEmpresa = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.DARK_GRAY);
			Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
			Font fontLabelBold = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
			Font fontNormal = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
			Font fontSmall = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
			Font fontSmallBold = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
			Font fonteNumOsFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);

			// MAIN DIV HEADER.
			PdfPTable divHeader = new PdfPTable(3);
			divHeader.setWidthPercentage(100);
			divHeader.setSpacingAfter(2);

			/*
			 * divHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
			 * divHeader.setPaddingRight(80);
			 */

			float[] columnWidths = { 25f, 55f, 20f };
			divHeader.setWidths(columnWidths);

			// IMAGEM/LOGO PLACEHOLDER
			PdfPCell logoCell = new PdfPCell();
			logoCell.setBorder(PdfPCell.BOTTOM);
			logoCell.setBorderColor(BaseColor.LIGHT_GRAY);
			logoCell.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
			Paragraph imgText = new Paragraph("[LOGO]");
			imgText.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			logoCell.addElement(imgText);

			// DADOS EMPRESA
			PdfPCell dadosEmpresaCell = new PdfPCell();
			dadosEmpresaCell.setBorder(PdfPCell.BOTTOM);
			dadosEmpresaCell.setBorderColor(BaseColor.LIGHT_GRAY);
			dadosEmpresaCell.setPadding(5);

			Paragraph rzSocial = new Paragraph(empresa.getNomEmpresa().toUpperCase(), fontEmpresa);
			rzSocial.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);

			Paragraph endereco = new Paragraph(
					empresa.getLogra() + ", " + empresa.getNum() + " - " + empresa.getBairro() + "\n"
							+ empresa.getMunicipio() + " - " + empresa.getUf() + " - CEP: " + empresa.getCep(),
					fontSmall);
			endereco.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);

			Paragraph dadosEmp = new Paragraph(
					"CNPJ: " + empresa.getDocEmpresa() + " | Tel: (" + empresa.getTel() + ")", fontSmallBold);
			dadosEmp.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);

			dadosEmpresaCell.addElement(rzSocial);
			dadosEmpresaCell.addElement(endereco);
			dadosEmpresaCell.addElement(dadosEmp);

			// OS NUMBER
			PdfPCell numOSCell = new PdfPCell();
			numOSCell.setBorder(PdfPCell.BOTTOM);
			numOSCell.setBorderColor(BaseColor.LIGHT_GRAY);
			numOSCell.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
			numOSCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);

			Paragraph pOsLabel = new Paragraph("ORDEM DE SERVIÇO", fontSmallBold);
			pOsLabel.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			Paragraph pOsNum = new Paragraph(String.format("%06d", os.getSequencial()), fonteNumOsFont);
			pOsNum.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);

			numOSCell.addElement(pOsLabel);
			numOSCell.addElement(pOsNum);

			divHeader.addCell(logoCell);
			divHeader.addCell(dadosEmpresaCell);
			divHeader.addCell(numOSCell);

			// ******* // ******

			// SEGUNDA LINHA: INFORMAÇÕES DE DATA E ATENDIMENTO
			PdfPTable rowInfo = new PdfPTable(2);
			rowInfo.setWidthPercentage(100);
			rowInfo.setSpacingBefore(10);
			rowInfo.setWidths(new float[] { 50f, 50f });

			PdfPCell cellAbertura = new PdfPCell();
			cellAbertura.setBorder(0);
			Paragraph pAbertura = new Paragraph();
			pAbertura.add(new Phrase("DATA DE ABERTURA: ", fontLabelBold));
			pAbertura.add(new Phrase(os.getDataAbertura(), fontNormal));
			cellAbertura.addElement(pAbertura);

			PdfPCell cellHorario = new PdfPCell();
			cellHorario.setBorder(0);
			cellHorario.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
			Paragraph pHorario = new Paragraph();
			pHorario.setAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
			pHorario.add(new Phrase("ATENDIMENTO: ", fontLabelBold));
			pHorario.add(new Phrase("Seg à Sex: 09h às 18h | Sáb: 09h às 13h", fontSmall));
			cellHorario.addElement(pHorario);

			rowInfo.addCell(cellAbertura);
			rowInfo.addCell(cellHorario);

			PdfPTable clienteDiv = new PdfPTable(1);
			clienteDiv.setWidthPercentage(100);
			clienteDiv.setSpacingBefore(15);

			PdfPCell clienteDivCel = new PdfPCell(new Phrase("DADOS DO CLIENTE", fontLabelBold));
			clienteDivCel.setBackgroundColor(BaseColor.LIGHT_GRAY);
			clienteDivCel.setBorder(PdfPCell.BOTTOM);
			clienteDivCel.setBorderColor(BaseColor.GRAY);
			clienteDivCel.setFixedHeight(18);
			clienteDivCel.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
			clienteDivCel.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			clienteDivCel.setPaddingLeft(5);
			clienteDiv.addCell(clienteDivCel);

			// TITULO DOS CAMPOS.

			PdfPTable divClienteTitulo = new PdfPTable(3);
			divClienteTitulo.setWidthPercentage(100);
			float[] widthTitulos = { 8f, 3f, 3f };
			divClienteTitulo.setWidths(widthTitulos);
			divClienteTitulo.setSpacingBefore(2);
			// titulos
			PdfPCell nomeTituloCliente = new PdfPCell(new Phrase("Nome do cliente"));
			nomeTituloCliente.setBorder(0);
			PdfPCell telefoneTituloCliente = new PdfPCell(new Phrase("Telefone"));
			telefoneTituloCliente.setBorder(0);
			PdfPCell documentoTituloCliente = new PdfPCell(new Phrase("CPF/CNPJ"));
			documentoTituloCliente.setBorder(0);
			/*
			 * PdfPCell ieTituloCliente = new PdfPCell(new Phrase("IE"));
			 * ieTituloCliente.setBorder(0);
			 */

			divClienteTitulo.addCell(nomeTituloCliente);
			divClienteTitulo.addCell(telefoneTituloCliente);
			divClienteTitulo.addCell(documentoTituloCliente);
			/* divClienteTitulo.addCell(ieTituloCliente); */

			// dados
			PdfPTable divDadosTitulo = new PdfPTable(5);
			divDadosTitulo.setWidthPercentage(100);
			float[] widthDados = { 8f, 0.1f, 3f, 0.1f, 3f };
			divDadosTitulo.setWidths(widthDados);
			divDadosTitulo.setSpacingBefore(2);

			PdfPCell space1 = new PdfPCell();
			space1.setBorder(0);
			PdfPCell space2 = new PdfPCell();
			space2.setBorder(0);
			PdfPCell space3 = new PdfPCell();
			space3.setBorder(0);

			PdfPCell nomeDadosCliente = new PdfPCell(new Phrase(os.getCliente().getNome()));
			nomeDadosCliente.setPadding(1);
			nomeDadosCliente.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			PdfPCell telefoneDadosCliente = new PdfPCell(new Phrase(os.getCliente().getCel1()));
			telefoneDadosCliente.setPadding(1);

			PdfPCell documentoDadosCliente = new PdfPCell(new Phrase(os.getCliente().getDocumento()));

			/* PdfPCell ieDadosCliente = new PdfPCell(new Phrase("7238471-12")); */

			divDadosTitulo.addCell(nomeDadosCliente);
			divDadosTitulo.addCell(space1);
			divDadosTitulo.addCell(telefoneDadosCliente);
			divDadosTitulo.addCell(space2);
			divDadosTitulo.addCell(documentoDadosCliente);
			/* divDadosTitulo.addCell(ieDadosCliente); */

			// ENDEREÇO CLIENTE.

			// TITULO DOS CAMPOS.

			PdfPTable divClienteEnderecoTitulo = new PdfPTable(7);
			divClienteEnderecoTitulo.setWidthPercentage(100);
			float[] widthTitulosEndereco = { 6f, 0.1f, 3f, 0.1f, 2f, 0.1f, 3f };
			divClienteEnderecoTitulo.setWidths(widthTitulosEndereco);
			divClienteEnderecoTitulo.setSpacingBefore(2);

			// titulos endereço

			PdfPCell EnderecoTituloCliente = new PdfPCell(new Phrase("Endereço")); // RUA + NUMERO
			EnderecoTituloCliente.setBorder(0);
			PdfPCell localidadeTituloCliente = new PdfPCell(new Phrase("Localidade")); // CIDADE + ESTADO
			localidadeTituloCliente.setBorder(0);
			PdfPCell cepTituloCliente = new PdfPCell(new Phrase("Cep"));
			cepTituloCliente.setBorder(0);
			PdfPCell obsTituloCliente = new PdfPCell(new Phrase("Observação"));
			obsTituloCliente.setBorder(0);

			divClienteEnderecoTitulo.addCell(EnderecoTituloCliente);
			divClienteEnderecoTitulo.addCell(space1);
			divClienteEnderecoTitulo.addCell(localidadeTituloCliente);
			divClienteEnderecoTitulo.addCell(space2);
			divClienteEnderecoTitulo.addCell(cepTituloCliente);
			divClienteEnderecoTitulo.addCell(space3);
			divClienteEnderecoTitulo.addCell(obsTituloCliente);

			// dados endereço

			PdfPTable divDadosEndereco = new PdfPTable(7);
			divDadosEndereco.setWidthPercentage(100);
			float[] widthDadosEndereco = { 6f, 0.1f, 3f, 0.1f, 2f, 0.1f, 3f };
			divDadosEndereco.setWidths(widthDadosEndereco);
			divDadosEndereco.setSpacingBefore(2);

			PdfPCell enderecoDadosCliente = new PdfPCell(
					new Phrase(os.getCliente().getLogradouro() + ", nº: " + os.getCliente().getNumero()));

			PdfPCell localidadeDadosCliente = new PdfPCell(
					new Phrase(os.getCliente().getCidade() + " - " + os.getCliente().getEstado()));
			localidadeDadosCliente.setFixedHeight(2f);
			PdfPCell cepDadosCliente = new PdfPCell(new Phrase(os.getCliente().getCep()));
			cepDadosCliente.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			PdfPCell obsDadosCliente = new PdfPCell(new Phrase(os.getCliente().getObs()));

			divDadosEndereco.addCell(enderecoDadosCliente);
			divDadosEndereco.addCell(space1);
			divDadosEndereco.addCell(localidadeDadosCliente);
			divDadosEndereco.addCell(space2);
			divDadosEndereco.addCell(cepDadosCliente);
			divDadosEndereco.addCell(space3);
			divDadosEndereco.addCell(obsDadosCliente);

			// DIVISÃO EQUIPAMENTO:

			PdfPTable equipamentoDiv = new PdfPTable(1);
			equipamentoDiv.setWidthPercentage(100);
			equipamentoDiv.setSpacingBefore(5);

			PdfPCell equipamentoDivCel = new PdfPCell(
					new Phrase("Aparelho", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
			equipamentoDivCel.setBackgroundColor(BaseColor.LIGHT_GRAY);
			equipamentoDivCel.setBorder(0);
			equipamentoDivCel.setFixedHeight(20);
			equipamentoDivCel.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			equipamentoDiv.addCell(equipamentoDivCel);

			// TITULOS EQUIPAMENTO

			PdfPTable divTituloEquipamento = new PdfPTable(2);
			divTituloEquipamento.setWidthPercentage(100);
			float[] widthTituloEquipamento = { 4f, 8f };
			divTituloEquipamento.setWidths(widthTituloEquipamento);
			divTituloEquipamento.setSpacingBefore(2);

			PdfPCell divAparelhoEquipamentoTitulo = new PdfPCell(new Phrase("Aparelho"));
			divAparelhoEquipamentoTitulo.setBorder(0);
			PdfPCell divModeloEquipamentoTitulo = new PdfPCell(new Phrase("Modelo"));
			divModeloEquipamentoTitulo.setBorder(0);
			PdfPCell divEstadoAparelhoEquipamentoTitulo = new PdfPCell(new Phrase("Estado aparelho | Observações"));
			divEstadoAparelhoEquipamentoTitulo.setBorder(0);
			divEstadoAparelhoEquipamentoTitulo.setPaddingBottom(6);
			divEstadoAparelhoEquipamentoTitulo.setPaddingTop(6);
			divEstadoAparelhoEquipamentoTitulo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);

			divTituloEquipamento.addCell(divAparelhoEquipamentoTitulo);
			divTituloEquipamento.addCell(divModeloEquipamentoTitulo);

			// DIVISÃO DADOS.

			PdfPTable divDadosEquipamento = new PdfPTable(3);
			divDadosEquipamento.setWidthPercentage(100);
			float[] widthDadosEquipamento = { 4f, 0.1f, 8f };
			divDadosEquipamento.setWidths(widthDadosEquipamento);
			divDadosEquipamento.setSpacingBefore(2);

			PdfPCell divAparelhoEquipamentoDados = new PdfPCell(new Phrase(os.getAparelhos().toString()));
			divAparelhoEquipamentoDados.setFixedHeight(5f);
			divAparelhoEquipamentoDados.setNoWrap(false);
			PdfPCell divModeloEquipamentoDados = new PdfPCell(new Phrase(os.getDescricaoModelo()));
			PdfPCell divEstadoAparelhoEquipamentoDados = new PdfPCell(new Phrase(os.getCheckList()));

			divDadosEquipamento.addCell(divAparelhoEquipamentoDados);
			divDadosEquipamento.addCell(space1);
			divDadosEquipamento.addCell(divModeloEquipamentoDados);
			// div para check-list do equipamento.
			PdfPTable divTituloCheckListEquipamento = new PdfPTable(1);
			divTituloCheckListEquipamento.setWidthPercentage(100);
			divTituloCheckListEquipamento.setSpacingBefore(1);
			divTituloCheckListEquipamento.addCell(divEstadoAparelhoEquipamentoTitulo);
			divTituloCheckListEquipamento.addCell(divEstadoAparelhoEquipamentoDados);

			// DIVISÃO PARA TESTE INCIAL E RELATO DO PROBLEMA.

			PdfPTable testeInicialERelatoDiv = new PdfPTable(1);
			testeInicialERelatoDiv.setWidthPercentage(100);
			testeInicialERelatoDiv.setSpacingBefore(5);

			PdfPCell testeInicialERelatoCel = new PdfPCell(
					new Phrase("Entrada do aparelho", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
			testeInicialERelatoCel.setBackgroundColor(BaseColor.LIGHT_GRAY);
			testeInicialERelatoCel.setBorder(0);
			testeInicialERelatoCel.setFixedHeight(20);
			testeInicialERelatoCel.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);

			testeInicialERelatoDiv.addCell(testeInicialERelatoCel);

			// titulos para entrada aparelho.

			PdfPTable entradaAparelhoTitulo = new PdfPTable(3);
			entradaAparelhoTitulo.setWidthPercentage(100);
			float[] widthentradaAparelhoTitulo = { 8f, 0.1f, 8f };
			entradaAparelhoTitulo.setWidths(widthentradaAparelhoTitulo);
			entradaAparelhoTitulo.setWidthPercentage(100);

			PdfPCell relatoDoClienteTitulo = new PdfPCell(new Phrase("Defeito informado pelo cliente"));
			relatoDoClienteTitulo.setBorder(0);
			relatoDoClienteTitulo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);

			PdfPCell resultadoTesteIncial = new PdfPCell(new Phrase("Resultado do teste inicial"));
			resultadoTesteIncial.setBorder(0);

			entradaAparelhoTitulo.addCell(relatoDoClienteTitulo);
			entradaAparelhoTitulo.addCell(space1);
			entradaAparelhoTitulo.addCell(resultadoTesteIncial);

			// DADOS ENTRADA DO APARELHO:

			PdfPTable entradaAparelhoDados = new PdfPTable(3);
			entradaAparelhoDados.setWidthPercentage(100);
			float[] entradaAparelhoDadosWidth = { 4f, 0.1f, 4f };
			entradaAparelhoDados.setWidths(entradaAparelhoDadosWidth);
			entradaAparelhoDados.setSpacingBefore(2);

			PdfPCell relatoDoClienteDados = new PdfPCell(new Phrase(os.getReclamacaoCliente()));

			PdfPCell resultadoTesteIncialDados = new PdfPCell(new Phrase(os.getInitTest()));

			entradaAparelhoDados.addCell(relatoDoClienteDados);
			entradaAparelhoDados.addCell(space1);
			entradaAparelhoDados.addCell(resultadoTesteIncialDados);

			// PDF FIM.

			PdfPTable pdfFinal = new PdfPTable(1);
			pdfFinal.setWidthPercentage(100);
			pdfFinal.setSpacingBefore(20);

			PdfPCell pdfFinalCell = new PdfPCell();
			pdfFinalCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			pdfFinalCell.setBorder(0);

			pdfFinal.addCell(pdfFinalCell);

			// TIPO DE ATENDIMENTO E ATENDENTE

			PdfPTable atendenteEPrioridadeTitulo = new PdfPTable(3);
			atendenteEPrioridadeTitulo.setWidthPercentage(100);
			float[] widthatendenteEPrioridadeTitulo = { 8f, 0.1f, 8f };
			atendenteEPrioridadeTitulo.setWidths(widthatendenteEPrioridadeTitulo);
			atendenteEPrioridadeTitulo.setWidthPercentage(100);

			PdfPCell atendendeTitulo = new PdfPCell(new Phrase("Atendente"));
			atendendeTitulo.setBorder(0);
			atendendeTitulo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);

			PdfPCell prioridadeTitulo = new PdfPCell(new Phrase("Prioridade do atendimento"));
			prioridadeTitulo.setBorder(0);

			atendenteEPrioridadeTitulo.addCell(atendendeTitulo);
			atendenteEPrioridadeTitulo.addCell(space1);
			atendenteEPrioridadeTitulo.addCell(prioridadeTitulo);

			// DADOS TIPO DE ATENDIMENTO E ATENDENTE DADOS

			PdfPTable atendenteEPrioridadeDados = new PdfPTable(3);
			atendenteEPrioridadeDados.setWidthPercentage(100);
			float[] atendenteEPrioridadeWidth = { 4f, 0.1f, 4f };
			atendenteEPrioridadeDados.setWidths(atendenteEPrioridadeWidth);
			atendenteEPrioridadeDados.setSpacingBefore(2);

			PdfPCell atendendeDados = new PdfPCell(new Phrase(os.getColaborador().getNome()));

			PdfPCell prioridadeTituloDados = new PdfPCell(new Phrase(os.getPrioridadeOS().toString()));

			atendenteEPrioridadeDados.addCell(atendendeDados);
			atendenteEPrioridadeDados.addCell(space1);
			atendenteEPrioridadeDados.addCell(prioridadeTituloDados);

			// ASSINATURAS.

			// TERMOS DE SERVIÇO
			PdfPTable termosDiv = new PdfPTable(1);
			termosDiv.setWidthPercentage(100);
			termosDiv.setSpacingBefore(15);

			PdfPCell termosHeader = new PdfPCell(new Phrase("TERMOS E CONDIÇÕES", fontSmallBold));
			termosHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
			termosHeader.setBorder(PdfPCell.BOTTOM);
			termosHeader.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
			termosHeader.setPadding(3);
			termosDiv.addCell(termosHeader);

			String textoTermos = "1. O orçamento tem validade de 07 dias após a data de emissão. "
					+ "2. A garantia de serviços é de 90 dias limitada exclusivamente às peças substituídas e serviços executados. "
					+ "3. Equipamentos não retirados em até 90 dias após notificação de conclusão estarão sujeitos à venda para cobertura de custos operacionais (Art. 1.275 CC). "
					+ "4. Não nos responsabilizamos por perda de dados. Recomendamos backup prévio. "
					+ "5. A abertura do equipamento poderá ocasionar a perda da garantia de fábrica.";

			PdfPCell termosContent = new PdfPCell(new Paragraph(textoTermos, fontSmall));
			termosContent.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.BOTTOM);
			termosContent.setBorderColor(BaseColor.LIGHT_GRAY);
			termosContent.setPadding(5);
			termosContent.setLeading(0, 1.2f);
			termosDiv.addCell(termosContent);

			// ASSINATURAS
			PdfPTable assinaturas = new PdfPTable(2);
			assinaturas.setWidthPercentage(100);
			assinaturas.setSpacingBefore(40);
			assinaturas.setWidths(new float[] { 45f, 45f });

			// Assinatura Empresa
			PdfPCell assEmpresaCel = new PdfPCell();
			assEmpresaCel.setBorder(0);
			assEmpresaCel.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			Paragraph pLineEmp = new Paragraph("__________________________________________", fontNormal);
			pLineEmp.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			Paragraph pNomeEmp = new Paragraph(empresa.getNomEmpresa(), fontSmallBold);
			pNomeEmp.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			assEmpresaCel.addElement(pLineEmp);
			assEmpresaCel.addElement(pNomeEmp);

			// Assinatura Cliente
			PdfPCell assClienteCel = new PdfPCell();
			assClienteCel.setBorder(0);
			assClienteCel.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			Paragraph pLineCli = new Paragraph("__________________________________________", fontNormal);
			pLineCli.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			Paragraph pNomeCli = new Paragraph(os.getCliente().getNome(), fontSmallBold);
			pNomeCli.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			assClienteCel.addElement(pLineCli);
			assClienteCel.addElement(pNomeCli);

			assinaturas.addCell(assEmpresaCel);
			assinaturas.addCell(assClienteCel);

			documento.open();

			documento.add(divHeader);
			documento.add(rowInfo);
			documento.add(clienteDiv);
			documento.add(divClienteTitulo);
			documento.add(divDadosTitulo);
			documento.add(divClienteEnderecoTitulo);
			documento.add(divDadosEndereco);
			documento.add(equipamentoDiv);
			documento.add(divTituloEquipamento);
			documento.add(divDadosEquipamento);
			documento.add(divTituloCheckListEquipamento);
			documento.add(testeInicialERelatoDiv);
			documento.add(entradaAparelhoTitulo);
			documento.add(entradaAparelhoDados);
			documento.add(atendenteEPrioridadeTitulo);
			documento.add(atendenteEPrioridadeDados);
			documento.add(termosDiv);
			documento.add(assinaturas);

			documento.close();

			return bytes.toByteArray();
		}

	}

	public byte[] gerarPdfOrcamento(long numOS, String codEmpresa) throws Exception {
		try (ByteArrayOutputStream bytes = new ByteArrayOutputStream()) {

			Empresa empresa = empresaRepository.findEmpresaById(Long.valueOf(decriptService.decriptCodEmp(codEmpresa)));

			OS_Entrada os = osRepository.findByNumOs(numOS, empresa.getId());
			if (os == null) {
				throw new Exception("OS não encontrada");
			}

			Orcamento orcamento = orcamentoRepository.encontrarOcamentoPorNumOS(empresa.getId(), os.getId());
			if (orcamento == null) {
				throw new Exception("Orçamento não encontrado para esta OS");
			}

			List<OrcamentoItem> itens = orcamentoItemRepository.listaItens(empresa.getId(), orcamento.getId());

			TotaisNotaDTO totais = orcamentoService.calcularValorOrcamento(orcamento.getId(), codEmpresa);

			Document documento = new Document();
			PdfWriter.getInstance(documento, bytes);

			// CONFIGURAÇÕES PDF:
			documento.setMargins(20, 20, 20, 20);
			documento.setPageSize(PageSize.A4);

			// FONTE
			Font marcadorHeader = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
			Font fonteNumOsFont = new Font(Font.FontFamily.HELVETICA, 16);
			Font fonteTabela = new Font(Font.FontFamily.HELVETICA, 9);
			Font fonteTabelaBold = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);

			// MAIN DIV HEADER - Cabeçalho com dados da empresa
			PdfPTable divHeader = new PdfPTable(3);
			divHeader.setWidthPercentage(100);
			divHeader.setSpacingAfter(2);

			PdfPCell imgDivHeaderDiv = new PdfPCell();
			imgDivHeaderDiv.setBorder(imgDivHeaderDiv.LEFT | imgDivHeaderDiv.TOP | imgDivHeaderDiv.BOTTOM);
			imgDivHeaderDiv.setBorderColor(BaseColor.LIGHT_GRAY);

			PdfPCell dadosEmpresa = new PdfPCell();
			dadosEmpresa.setBorder(dadosEmpresa.BOTTOM | dadosEmpresa.TOP);
			dadosEmpresa.setBorderColor(BaseColor.LIGHT_GRAY);
			dadosEmpresa.setPadding(4);
			dadosEmpresa.setHorizontalAlignment(dadosEmpresa.ALIGN_LEFT);

			PdfPCell numOSCell = new PdfPCell();
			numOSCell.setBorderColor(BaseColor.LIGHT_GRAY);
			numOSCell.setHorizontalAlignment(numOSCell.ALIGN_BASELINE);

			float[] columnWidths = { 20f, 60f, 10f };
			divHeader.setWidths(columnWidths);

			// IMAGEM
			Paragraph imgText = new Paragraph("[LOGO EMPRESA]");
			imgText.setAlignment(imgText.ALIGN_BASELINE);
			imgDivHeaderDiv.addElement(imgText);

			// DADOS EMPRESA
			Paragraph rzSocial = new Paragraph(empresa.getNomEmpresa());
			rzSocial.setAlignment(rzSocial.ALIGN_RIGHT);
			rzSocial.setFont(marcadorHeader);

			Paragraph endereco = new Paragraph(
					empresa.getLogra() + " , " + empresa.getNum() + " - " + empresa.getBairro() + " - "
							+ empresa.getMunicipio() + " - " + empresa.getUf() + " - " + "CEP: " + empresa.getCep());
			endereco.setAlignment(endereco.ALIGN_RIGHT);

			Paragraph dadosEmp = new Paragraph(
					"CNPJ: " + empresa.getDocEmpresa() + " | " + "Tel (" + empresa.getTel() + ")");
			dadosEmp.setAlignment(rzSocial.ALIGN_RIGHT);

			Paragraph tituloPage = new Paragraph("Orçamento Técnico");
			tituloPage.setFont(marcadorHeader);
			tituloPage.setAlignment(tituloPage.ALIGN_CENTER);
			tituloPage.setSpacingAfter(5);

			dadosEmpresa.addElement(rzSocial);
			dadosEmpresa.addElement(endereco);
			dadosEmpresa.addElement(dadosEmp);
			dadosEmpresa.addElement(tituloPage);

			// OS
			Paragraph NumOs = new Paragraph("O.S :");
			NumOs.setAlignment(NumOs.ALIGN_CENTER);
			NumOs.setFont(marcadorHeader);
			Paragraph codOs = new Paragraph(String.valueOf(os.getSequencial()));
			codOs.setFont(fonteNumOsFont);
			codOs.setAlignment(codOs.ALIGN_CENTER);
			codOs.setSpacingBefore(10);

			numOSCell.addElement(NumOs);
			numOSCell.addElement(codOs);

			divHeader.addCell(imgDivHeaderDiv);
			divHeader.addCell(dadosEmpresa);
			divHeader.addCell(numOSCell);

			// SEGUNDA LINHA: Data de emissão
			PdfPTable row2 = new PdfPTable(2);
			row2.setWidthPercentage(100);
			float[] row2float = { 3f, 8f };
			row2.setWidths(row2float);

			PdfPTable titleRow2 = new PdfPTable(2);
			titleRow2.setWidthPercentage(100);

			PdfPCell titleDataEmiss = new PdfPCell();
			Paragraph vTitleDataEmiss = new Paragraph("Data e hora de emissão: ");
			vTitleDataEmiss.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			vTitleDataEmiss.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD));
			titleDataEmiss.setBorder(0);
			titleDataEmiss.addElement(vTitleDataEmiss);

			PdfPCell titleFuncionamento = new PdfPCell();
			Paragraph vtitleFuncionamento = new Paragraph("Horário de atendimento: ");
			vtitleFuncionamento.setAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
			vtitleFuncionamento.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD));
			titleFuncionamento.addElement(vtitleFuncionamento);
			titleFuncionamento.setBorder(0);
			titleFuncionamento.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);

			titleRow2.addCell(titleDataEmiss);
			titleRow2.addCell(titleFuncionamento);

			// DATA E HORA DE CRIAÇÃO
			PdfPCell dataEhora = new PdfPCell();
			// TODO: Recuperar data de criação do orçamento dinamicamente do banco de dados
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Paragraph vDataHora = new Paragraph(sdf.format(new Date())); // Dado mockado - substituir pela data real do
																			// orçamento
			vDataHora.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			dataEhora.setPadding(0);
			dataEhora.addElement(vDataHora);
			row2.addCell(dataEhora);

			// HORARIO DE FUNCIONAMENTO
			PdfPCell horarioFuncionamento = new PdfPCell(new Phrase("Horario funcionamento: "));
			horarioFuncionamento.setBorder(0);
			// TODO: Recuperar horário de funcionamento da empresa dinamicamente
			Paragraph vHorarioFuncionamento = new Paragraph("segunda a sexta-feira:9h às 19\nsábados, das 9h às 14h"); // Dado
																														// mockado
			vHorarioFuncionamento.setAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
			vHorarioFuncionamento.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD));
			horarioFuncionamento.addElement(vHorarioFuncionamento);
			row2.addCell(horarioFuncionamento);

			// DIVISÃO CLIENTE - Dados do cliente
			PdfPTable clienteDiv = new PdfPTable(1);
			clienteDiv.setWidthPercentage(100);
			clienteDiv.setSpacingBefore(5);

			PdfPCell clienteDivCel = new PdfPCell(
					new Phrase("Cliente", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
			clienteDivCel.setBackgroundColor(BaseColor.LIGHT_GRAY);
			clienteDivCel.setBorder(0);
			clienteDivCel.setFixedHeight(20);
			clienteDivCel.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			clienteDiv.addCell(clienteDivCel);

			// TITULO DOS CAMPOS CLIENTE
			PdfPTable divClienteTitulo = new PdfPTable(3);
			divClienteTitulo.setWidthPercentage(100);
			float[] widthTitulos = { 8f, 3f, 3f };
			divClienteTitulo.setWidths(widthTitulos);
			divClienteTitulo.setSpacingBefore(2);

			PdfPCell nomeTituloCliente = new PdfPCell(new Phrase("Nome do cliente"));
			nomeTituloCliente.setBorder(0);
			PdfPCell telefoneTituloCliente = new PdfPCell(new Phrase("Telefone"));
			telefoneTituloCliente.setBorder(0);
			PdfPCell documentoTituloCliente = new PdfPCell(new Phrase("CPF/CNPJ"));
			documentoTituloCliente.setBorder(0);

			divClienteTitulo.addCell(nomeTituloCliente);
			divClienteTitulo.addCell(telefoneTituloCliente);
			divClienteTitulo.addCell(documentoTituloCliente);

			// DADOS CLIENTE
			PdfPTable divDadosTitulo = new PdfPTable(5);
			divDadosTitulo.setWidthPercentage(100);
			float[] widthDados = { 8f, 0.1f, 3f, 0.1f, 3f };
			divDadosTitulo.setWidths(widthDados);
			divDadosTitulo.setSpacingBefore(2);

			PdfPCell space1 = new PdfPCell();
			space1.setBorder(0);
			PdfPCell space2 = new PdfPCell();
			space2.setBorder(0);
			PdfPCell space3 = new PdfPCell();
			space3.setBorder(0);

			PdfPCell nomeDadosCliente = new PdfPCell(new Phrase(os.getCliente().getNome()));
			nomeDadosCliente.setPadding(1);
			nomeDadosCliente.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			PdfPCell telefoneDadosCliente = new PdfPCell(new Phrase(os.getCliente().getCel1()));
			telefoneDadosCliente.setPadding(1);
			PdfPCell documentoDadosCliente = new PdfPCell(new Phrase(os.getCliente().getDocumento()));

			divDadosTitulo.addCell(nomeDadosCliente);
			divDadosTitulo.addCell(space1);
			divDadosTitulo.addCell(telefoneDadosCliente);
			divDadosTitulo.addCell(space2);
			divDadosTitulo.addCell(documentoDadosCliente);

			// ENDEREÇO CLIENTE
			PdfPTable divClienteEnderecoTitulo = new PdfPTable(7);
			divClienteEnderecoTitulo.setWidthPercentage(100);
			float[] widthTitulosEndereco = { 6f, 0.1f, 3f, 0.1f, 2f, 0.1f, 3f };
			divClienteEnderecoTitulo.setWidths(widthTitulosEndereco);
			divClienteEnderecoTitulo.setSpacingBefore(2);

			PdfPCell EnderecoTituloCliente = new PdfPCell(new Phrase("Endereço"));
			EnderecoTituloCliente.setBorder(0);
			PdfPCell localidadeTituloCliente = new PdfPCell(new Phrase("Localidade"));
			localidadeTituloCliente.setBorder(0);
			PdfPCell cepTituloCliente = new PdfPCell(new Phrase("Cep"));
			cepTituloCliente.setBorder(0);
			PdfPCell obsTituloCliente = new PdfPCell(new Phrase("Observação"));
			obsTituloCliente.setBorder(0);

			divClienteEnderecoTitulo.addCell(EnderecoTituloCliente);
			divClienteEnderecoTitulo.addCell(space1);
			divClienteEnderecoTitulo.addCell(localidadeTituloCliente);
			divClienteEnderecoTitulo.addCell(space2);
			divClienteEnderecoTitulo.addCell(cepTituloCliente);
			divClienteEnderecoTitulo.addCell(space3);
			divClienteEnderecoTitulo.addCell(obsTituloCliente);

			// DADOS ENDEREÇO
			PdfPTable divDadosEndereco = new PdfPTable(7);
			divDadosEndereco.setWidthPercentage(100);
			float[] widthDadosEndereco = { 6f, 0.1f, 3f, 0.1f, 2f, 0.1f, 3f };
			divDadosEndereco.setWidths(widthDadosEndereco);
			divDadosEndereco.setSpacingBefore(2);

			PdfPCell enderecoDadosCliente = new PdfPCell(
					new Phrase(os.getCliente().getLogradouro() + ", nº: " + os.getCliente().getNumero()));
			PdfPCell localidadeDadosCliente = new PdfPCell(
					new Phrase(os.getCliente().getCidade() + " - " + os.getCliente().getEstado()));
			localidadeDadosCliente.setFixedHeight(2f);
			PdfPCell cepDadosCliente = new PdfPCell(new Phrase(os.getCliente().getCep()));
			cepDadosCliente.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			PdfPCell obsDadosCliente = new PdfPCell(
					new Phrase(os.getCliente().getObs() != null ? os.getCliente().getObs() : ""));

			divDadosEndereco.addCell(enderecoDadosCliente);
			divDadosEndereco.addCell(space1);
			divDadosEndereco.addCell(localidadeDadosCliente);
			divDadosEndereco.addCell(space2);
			divDadosEndereco.addCell(cepDadosCliente);
			divDadosEndereco.addCell(space3);
			divDadosEndereco.addCell(obsDadosCliente);

			// DIVISÃO SERVIÇOS E PRODUTOS
			PdfPTable servicosProdutosDiv = new PdfPTable(1);
			servicosProdutosDiv.setWidthPercentage(100);
			servicosProdutosDiv.setSpacingBefore(5);

			PdfPCell servicosProdutosDivCel = new PdfPCell(
					new Phrase("Serviços e Produtos", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
			servicosProdutosDivCel.setBackgroundColor(BaseColor.LIGHT_GRAY);
			servicosProdutosDivCel.setBorder(0);
			servicosProdutosDivCel.setFixedHeight(20);
			servicosProdutosDivCel.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			servicosProdutosDiv.addCell(servicosProdutosDivCel);

			// TABELA DE ITENS
			PdfPTable tabelaItens = new PdfPTable(6);
			tabelaItens.setWidthPercentage(100);
			float[] widthColunas = { 3f, 4f, 2f, 2f, 2f, 2f };
			tabelaItens.setWidths(widthColunas);
			tabelaItens.setSpacingBefore(2);

			// Cabeçalho da tabela
			PdfPCell cellTipo = new PdfPCell(new Phrase("Tipo", fonteTabelaBold));
			cellTipo.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cellTipo.setBorder(1);
			cellTipo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);

			PdfPCell cellNome = new PdfPCell(new Phrase("Nome/Descrição", fonteTabelaBold));
			cellNome.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cellNome.setBorder(1);
			cellNome.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);

			PdfPCell cellQtd = new PdfPCell(new Phrase("Qtd", fonteTabelaBold));
			cellQtd.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cellQtd.setBorder(1);
			cellQtd.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);

			PdfPCell cellValorUnit = new PdfPCell(new Phrase("Valor Unit.", fonteTabelaBold));
			cellValorUnit.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cellValorUnit.setBorder(1);
			cellValorUnit.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);

			PdfPCell cellDesconto = new PdfPCell(new Phrase("Desconto", fonteTabelaBold));
			cellDesconto.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cellDesconto.setBorder(1);
			cellDesconto.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);

			PdfPCell cellTotal = new PdfPCell(new Phrase("Total", fonteTabelaBold));
			cellTotal.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cellTotal.setBorder(1);
			cellTotal.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);

			tabelaItens.addCell(cellTipo);
			tabelaItens.addCell(cellNome);
			tabelaItens.addCell(cellQtd);
			tabelaItens.addCell(cellValorUnit);
			tabelaItens.addCell(cellDesconto);
			tabelaItens.addCell(cellTotal);

			// Adicionar itens
			double totalGeral = 0.0;
			for (OrcamentoItem item : itens) {
				// Tipo
				String tipo = item.getProdutoOuServico() == ProdutoServicoEnum.SERVICO ? "Serviço" : "Produto";
				PdfPCell cellTipoItem = new PdfPCell(new Phrase(tipo, fonteTabela));
				cellTipoItem.setBorder(1);
				cellTipoItem.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);

				// Nome/Descrição
				String nomeDesc = item.getNomeServicoAvulso() != null ? item.getNomeServicoAvulso() : "";
				if (item.getDescricaoServicoAvulso() != null && !item.getDescricaoServicoAvulso().isEmpty()) {
					nomeDesc += "\n" + item.getDescricaoServicoAvulso();
				}
				PdfPCell cellNomeItem = new PdfPCell(new Phrase(nomeDesc, fonteTabela));
				cellNomeItem.setBorder(1);
				cellNomeItem.setPadding(3);

				// Quantidade - TODO: Recuperar quantidade dinamicamente do item
				// Por enquanto usando 1 como padrão
				double qtd = 1.0; // Dado mockado - substituir pela quantidade real do item
				PdfPCell cellQtdItem = new PdfPCell(new Phrase(String.format("%.2f", qtd), fonteTabela));
				cellQtdItem.setBorder(1);
				cellQtdItem.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);

				// Valor Unitário
				double valorUnit = 0.0;
				if (item.getValorHoraAvulso() != null && item.getValorHoraAvulso() > 0) {
					valorUnit = item.getValorHoraAvulso();
				} else if (item.getValorUnidadeAvulso() != null && item.getValorUnidadeAvulso() > 0) {
					valorUnit = item.getValorUnidadeAvulso();
				}
				PdfPCell cellValorUnitItem = new PdfPCell(new Phrase(String.format("R$ %.2f", valorUnit), fonteTabela));
				cellValorUnitItem.setBorder(1);
				cellValorUnitItem.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);

				// Desconto
				double desconto = item.getDescontoServico() != null ? item.getDescontoServico() : 0.0;
				PdfPCell cellDescontoItem = new PdfPCell(new Phrase(String.format("R$ %.2f", desconto), fonteTabela));
				cellDescontoItem.setBorder(1);
				cellDescontoItem.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);

				// Total
				double totalItem = item.getValorTotal() != null ? item.getValorTotal() : (valorUnit * qtd - desconto);
				if (totalItem < 0)
					totalItem = 0.0;
				totalGeral += totalItem;
				PdfPCell cellTotalItem = new PdfPCell(new Phrase(String.format("R$ %.2f", totalItem), fonteTabela));
				cellTotalItem.setBorder(1);
				cellTotalItem.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);

				tabelaItens.addCell(cellTipoItem);
				tabelaItens.addCell(cellNomeItem);
				tabelaItens.addCell(cellQtdItem);
				tabelaItens.addCell(cellValorUnitItem);
				tabelaItens.addCell(cellDescontoItem);
				tabelaItens.addCell(cellTotalItem);
			}

			// TOTAL GERAL
			PdfPTable tabelaTotal = new PdfPTable(2);
			tabelaTotal.setWidthPercentage(100);
			tabelaTotal.setSpacingBefore(10);
			float[] widthTotal = { 8f, 2f };
			tabelaTotal.setWidths(widthTotal);

			PdfPCell cellLabelTotal = new PdfPCell(
					new Phrase("TOTAL GERAL:", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
			cellLabelTotal.setBorder(1);
			cellLabelTotal.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
			cellLabelTotal.setPadding(5);

			PdfPCell cellValorTotal = new PdfPCell(new Phrase(String.format("R$ %.2f", totais.valorTotalNota),
					new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
			cellValorTotal.setBorder(1);
			cellValorTotal.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
			cellValorTotal.setPadding(5);
			cellValorTotal.setBackgroundColor(BaseColor.LIGHT_GRAY);

			tabelaTotal.addCell(cellLabelTotal);
			tabelaTotal.addCell(cellValorTotal);

			// ASSINATURAS
			PdfPTable assinaturas = new PdfPTable(3);
			assinaturas.setWidthPercentage(100);
			float[] assinaturasWidth = { 7f, 1f, 7f };
			assinaturas.setWidths(assinaturasWidth);
			assinaturas.setSpacingBefore(50);

			PdfPCell assinaturaEmpresa = new PdfPCell(new Phrase(empresa.getNomEmpresa()));
			assinaturaEmpresa.setBorder(1);
			assinaturaEmpresa.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			assinaturaEmpresa.setFixedHeight(60);

			PdfPCell assinaturaCliente = new PdfPCell(new Phrase(os.getCliente().getNome()));
			assinaturaCliente.setBorder(1);
			assinaturaCliente.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			assinaturaCliente.setFixedHeight(60);

			assinaturas.addCell(assinaturaEmpresa);
			assinaturas.addCell(space1);
			assinaturas.addCell(assinaturaCliente);

			documento.open();

			documento.add(divHeader);
			documento.add(titleRow2);
			documento.add(row2);
			documento.add(clienteDiv);
			documento.add(divClienteTitulo);
			documento.add(divDadosTitulo);
			documento.add(divClienteEnderecoTitulo);
			documento.add(divDadosEndereco);
			documento.add(servicosProdutosDiv);
			documento.add(tabelaItens);
			documento.add(tabelaTotal);
			documento.add(assinaturas);

			documento.close();

			return bytes.toByteArray();
		}
	}

	public byte[] gerarPdfOsRapida(OsRapida osRapida, String codEmpresa) throws Exception {
		try (ByteArrayOutputStream bytes = new ByteArrayOutputStream()) {

			Empresa empresa = empresaRepository.findEmpresaById(Long.valueOf(decriptService.decriptCodEmp(codEmpresa)));

			Document documento = new Document();
			PdfWriter.getInstance(documento, bytes);

			// CONFIGURAÇÕES PDF:
			documento.setMargins(20, 20, 20, 20);
			documento.setPageSize(PageSize.A4);

			// FONTE
			Font marcadorHeader = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
			Font fonteNumOsFont = new Font(Font.FontFamily.HELVETICA, 16);

			// MAIN DIV HEADER.
			PdfPTable divHeader = new PdfPTable(3);
			divHeader.setWidthPercentage(100);
			divHeader.setSpacingAfter(2);

			PdfPCell imgDivHeaderDiv = new PdfPCell();
			imgDivHeaderDiv.setBorder(imgDivHeaderDiv.LEFT | imgDivHeaderDiv.TOP | imgDivHeaderDiv.BOTTOM);
			imgDivHeaderDiv.setBorderColor(BaseColor.LIGHT_GRAY);

			PdfPCell dadosEmpresa = new PdfPCell();
			dadosEmpresa.setBorder(dadosEmpresa.BOTTOM | dadosEmpresa.TOP);
			dadosEmpresa.setBorderColor(BaseColor.LIGHT_GRAY);
			dadosEmpresa.setPadding(4);
			dadosEmpresa.setHorizontalAlignment(dadosEmpresa.ALIGN_LEFT);

			PdfPCell numOSCell = new PdfPCell();
			numOSCell.setBorderColor(BaseColor.LIGHT_GRAY);
			numOSCell.setHorizontalAlignment(numOSCell.ALIGN_BASELINE);

			float[] columnWidths = { 20f, 60f, 10f };
			divHeader.setWidths(columnWidths);

			// IMAGEM
			Paragraph imgText = new Paragraph("[LOGO EMPRESA]");
			imgText.setAlignment(imgText.ALIGN_BASELINE);
			imgDivHeaderDiv.addElement(imgText);

			// DADOS EMPRESA.
			Paragraph rzSocial = new Paragraph(empresa.getNomEmpresa());
			rzSocial.setAlignment(rzSocial.ALIGN_RIGHT);
			rzSocial.setFont(marcadorHeader);

			Paragraph endereco = new Paragraph(
					empresa.getLogra() + " , " + empresa.getNum() + " - " + empresa.getBairro() + " - "
							+ empresa.getMunicipio() + " - " + empresa.getUf() + " - " + "CEP: " + empresa.getCep());
			endereco.setAlignment(endereco.ALIGN_RIGHT);

			Paragraph dadosEmp = new Paragraph(
					"CNPJ: " + empresa.getDocEmpresa() + " | " + "Tel (" + empresa.getTel() + ")");
			dadosEmp.setAlignment(rzSocial.ALIGN_RIGHT);

			Paragraph tituloPage = new Paragraph("OS Rápida");
			tituloPage.setFont(marcadorHeader);
			tituloPage.setAlignment(tituloPage.ALIGN_CENTER);
			tituloPage.setSpacingAfter(5);

			dadosEmpresa.addElement(rzSocial);
			dadosEmpresa.addElement(endereco);
			dadosEmpresa.addElement(dadosEmp);
			dadosEmpresa.addElement(tituloPage);

			// OS RÁPIDA - ID
			Paragraph NumOs = new Paragraph("OS Rápida:");
			NumOs.setAlignment(NumOs.ALIGN_CENTER);
			NumOs.setFont(marcadorHeader);
			Paragraph codOs = new Paragraph("R - " + osRapida.getSequencial());
			codOs.setFont(fonteNumOsFont);
			codOs.setAlignment(codOs.ALIGN_CENTER);
			codOs.setSpacingBefore(10);

			numOSCell.addElement(NumOs);
			numOSCell.addElement(codOs);

			divHeader.addCell(imgDivHeaderDiv);
			divHeader.addCell(dadosEmpresa);
			divHeader.addCell(numOSCell);

			// SEGUNDA LINHA: Data de criação
			PdfPTable row2 = new PdfPTable(2);
			row2.setWidthPercentage(100);
			float[] row2float = { 3f, 8f };
			row2.setWidths(row2float);

			PdfPTable titleRow2 = new PdfPTable(2);
			titleRow2.setWidthPercentage(100);

			PdfPCell titleDataEmiss = new PdfPCell();
			Paragraph vTitleDataEmiss = new Paragraph("Data de abertura: ");
			vTitleDataEmiss.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			vTitleDataEmiss.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD));
			titleDataEmiss.setBorder(0);
			titleDataEmiss.addElement(vTitleDataEmiss);

			PdfPCell titleFuncionamento = new PdfPCell();
			Paragraph vtitleFuncionamento = new Paragraph("Horário de atendimento: ");
			vtitleFuncionamento.setAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
			vtitleFuncionamento.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD));
			titleFuncionamento.addElement(vtitleFuncionamento);
			titleFuncionamento.setBorder(0);
			titleFuncionamento.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);

			titleRow2.addCell(titleDataEmiss);
			titleRow2.addCell(titleFuncionamento);

			// DATA DE ABERTURA
			PdfPCell dataEhora = new PdfPCell();
			Paragraph vDataHora = new Paragraph(osRapida.getDataAbertura());
			vDataHora.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			dataEhora.setPadding(0);
			dataEhora.addElement(vDataHora);
			row2.addCell(dataEhora);

			// HORARIO DE FUNCIONAMENTO
			PdfPCell horarioFuncionamento = new PdfPCell(new Phrase("Horario funcionamento: "));
			horarioFuncionamento.setBorder(0);
			Paragraph vHorarioFuncionamento = new Paragraph("segunda a sexta-feira: 9h às 19\nsábados, das 9h às 14h");
			vHorarioFuncionamento.setAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
			vHorarioFuncionamento.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD));
			horarioFuncionamento.addElement(vHorarioFuncionamento);
			row2.addCell(horarioFuncionamento);

			// DIVISÃO CLIENTE.
			PdfPTable clienteDiv = new PdfPTable(1);
			clienteDiv.setWidthPercentage(100);
			clienteDiv.setSpacingBefore(5);

			PdfPCell clienteDivCel = new PdfPCell(
					new Phrase("Cliente", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
			clienteDivCel.setBackgroundColor(BaseColor.LIGHT_GRAY);
			clienteDivCel.setBorder(0);
			clienteDivCel.setFixedHeight(20);
			clienteDivCel.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			clienteDiv.addCell(clienteDivCel);

			// TITULO DOS CAMPOS CLIENTE.
			PdfPTable divClienteTitulo = new PdfPTable(3);
			divClienteTitulo.setWidthPercentage(100);
			float[] widthTitulos = { 8f, 3f, 3f };
			divClienteTitulo.setWidths(widthTitulos);
			divClienteTitulo.setSpacingBefore(2);

			PdfPCell nomeTituloCliente = new PdfPCell(new Phrase("Nome do cliente"));
			nomeTituloCliente.setBorder(0);
			PdfPCell telefoneTituloCliente = new PdfPCell(new Phrase("Telefone"));
			telefoneTituloCliente.setBorder(0);
			PdfPCell statusTituloCliente = new PdfPCell(new Phrase("Status"));
			statusTituloCliente.setBorder(0);

			divClienteTitulo.addCell(nomeTituloCliente);
			divClienteTitulo.addCell(telefoneTituloCliente);
			divClienteTitulo.addCell(statusTituloCliente);

			// dados cliente
			PdfPTable divDadosCliente = new PdfPTable(5);
			divDadosCliente.setWidthPercentage(100);
			float[] widthDadosCliente = { 8f, 0.1f, 3f, 0.1f, 3f };
			divDadosCliente.setWidths(widthDadosCliente);
			divDadosCliente.setSpacingBefore(2);

			PdfPCell space1 = new PdfPCell();
			space1.setBorder(0);
			PdfPCell space2 = new PdfPCell();
			space2.setBorder(0);

			PdfPCell nomeDadosCliente = new PdfPCell(new Phrase(osRapida.getClienteNome()));
			nomeDadosCliente.setPadding(1);
			nomeDadosCliente.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);

			PdfPCell telefoneDadosCliente = new PdfPCell(new Phrase(
					osRapida.getClienteTelefone() != null ? osRapida.getClienteTelefone() : "Não informado"));
			telefoneDadosCliente.setPadding(1);

			PdfPCell statusDadosCliente = new PdfPCell(
					new Phrase(osRapida.getStatus() != null ? osRapida.getStatus().toString() : "NOVO"));

			divDadosCliente.addCell(nomeDadosCliente);
			divDadosCliente.addCell(space1);
			divDadosCliente.addCell(telefoneDadosCliente);
			divDadosCliente.addCell(space2);
			divDadosCliente.addCell(statusDadosCliente);

			// DIVISÃO SERVIÇO/EQUIPAMENTO
			PdfPTable servicoDiv = new PdfPTable(1);
			servicoDiv.setWidthPercentage(100);
			servicoDiv.setSpacingBefore(5);

			PdfPCell servicoDivCel = new PdfPCell(
					new Phrase("Serviço/Equipamento", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
			servicoDivCel.setBackgroundColor(BaseColor.LIGHT_GRAY);
			servicoDivCel.setBorder(0);
			servicoDivCel.setFixedHeight(20);
			servicoDivCel.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			servicoDiv.addCell(servicoDivCel);

			// TITULO CAMPOS SERVIÇO
			PdfPTable divServicoTitulo = new PdfPTable(3);
			divServicoTitulo.setWidthPercentage(100);
			float[] widthServico = { 8f, 0.1f, 8f };
			divServicoTitulo.setWidths(widthServico);
			divServicoTitulo.setSpacingBefore(2);

			PdfPCell equipamentoTitulo = new PdfPCell(new Phrase("Equipamento/Serviço"));
			equipamentoTitulo.setBorder(0);
			PdfPCell problemaTitulo = new PdfPCell(new Phrase("Problema Relatado"));
			problemaTitulo.setBorder(0);

			divServicoTitulo.addCell(equipamentoTitulo);
			divServicoTitulo.addCell(space1);
			divServicoTitulo.addCell(problemaTitulo);

			// DADOS SERVIÇO
			PdfPTable divDadosServico = new PdfPTable(3);
			divDadosServico.setWidthPercentage(100);
			float[] widthDadosServico = { 8f, 0.1f, 8f };
			divDadosServico.setWidths(widthDadosServico);
			divDadosServico.setSpacingBefore(2);

			PdfPCell equipamentoDados = new PdfPCell(new Phrase(osRapida.getEquipamentoServico()));
			equipamentoDados.setPadding(3);

			PdfPCell problemaDados = new PdfPCell(new Phrase(osRapida.getProblemaRelatado()));
			problemaDados.setPadding(3);

			divDadosServico.addCell(equipamentoDados);
			divDadosServico.addCell(space1);
			divDadosServico.addCell(problemaDados);

			// DIVISÃO VALORES E PRAZOS
			PdfPTable valoresDiv = new PdfPTable(1);
			valoresDiv.setWidthPercentage(100);
			valoresDiv.setSpacingBefore(5);

			PdfPCell valoresDivCel = new PdfPCell(
					new Phrase("Valores e Prazos", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
			valoresDivCel.setBackgroundColor(BaseColor.LIGHT_GRAY);
			valoresDivCel.setBorder(0);
			valoresDivCel.setFixedHeight(20);
			valoresDivCel.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			valoresDiv.addCell(valoresDivCel);

			// TITULO CAMPOS VALORES
			PdfPTable divValoresTitulo = new PdfPTable(3);
			divValoresTitulo.setWidthPercentage(100);
			float[] widthValores = { 4f, 0.1f, 4f };
			divValoresTitulo.setWidths(widthValores);
			divValoresTitulo.setSpacingBefore(2);

			PdfPCell valorEstimadoTitulo = new PdfPCell(new Phrase("Valor Estimado"));
			valorEstimadoTitulo.setBorder(0);
			PdfPCell prazoCombinadoTitulo = new PdfPCell(new Phrase("Prazo Combinado"));
			prazoCombinadoTitulo.setBorder(0);

			divValoresTitulo.addCell(valorEstimadoTitulo);
			divValoresTitulo.addCell(space1);
			divValoresTitulo.addCell(prazoCombinadoTitulo);

			// DADOS VALORES
			PdfPTable divDadosValores = new PdfPTable(3);
			divDadosValores.setWidthPercentage(100);
			float[] widthDadosValores = { 4f, 0.1f, 4f };
			divDadosValores.setWidths(widthDadosValores);
			divDadosValores.setSpacingBefore(2);

			String valorEstimadoStr = osRapida.getValorEstimado() != null
					? "R$ " + String.format("%.2f", osRapida.getValorEstimado())
					: "Não informado";
			PdfPCell valorEstimadoDados = new PdfPCell(new Phrase(valorEstimadoStr));
			valorEstimadoDados.setPadding(3);
			valorEstimadoDados.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);

			String prazoCombinadoStr = osRapida.getPrazoCombinado() != null ? osRapida.getPrazoCombinado()
					: "Não informado";
			PdfPCell prazoCombinadoDados = new PdfPCell(new Phrase(prazoCombinadoStr));
			prazoCombinadoDados.setPadding(3);
			prazoCombinadoDados.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);

			divDadosValores.addCell(valorEstimadoDados);
			divDadosValores.addCell(space1);
			divDadosValores.addCell(prazoCombinadoDados);

			// OBSERVAÇÕES
			PdfPTable observacoesDiv = new PdfPTable(1);
			observacoesDiv.setWidthPercentage(100);
			observacoesDiv.setSpacingBefore(5);

			PdfPCell observacoesDivCel = new PdfPCell(
					new Phrase("Observações Internas", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
			observacoesDivCel.setBackgroundColor(BaseColor.LIGHT_GRAY);
			observacoesDivCel.setBorder(0);
			observacoesDivCel.setFixedHeight(20);
			observacoesDivCel.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			observacoesDiv.addCell(observacoesDivCel);

			PdfPCell observacoesDados = new PdfPCell(
					new Phrase(osRapida.getObservacoes() != null ? osRapida.getObservacoes() : "Nenhuma observação"));
			observacoesDados.setPadding(8);
			observacoesDiv.addCell(observacoesDados);

			// TÉCNICO RESPONSÁVEL
			PdfPTable tecnicoDiv = new PdfPTable(1);
			tecnicoDiv.setWidthPercentage(100);
			tecnicoDiv.setSpacingBefore(5);

			PdfPCell tecnicoDivCel = new PdfPCell(
					new Phrase("Técnico Responsável", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
			tecnicoDivCel.setBackgroundColor(BaseColor.LIGHT_GRAY);
			tecnicoDivCel.setBorder(0);
			tecnicoDivCel.setFixedHeight(20);
			tecnicoDivCel.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			tecnicoDiv.addCell(tecnicoDivCel);

			PdfPCell tecnicoDados = new PdfPCell(new Phrase(
					osRapida.getTecnicoResponsavel() != null ? osRapida.getTecnicoResponsavel() : "Não informado"));
			tecnicoDados.setPadding(5);
			tecnicoDiv.addCell(tecnicoDados);

			// ASSINATURAS
			PdfPTable assinaturas = new PdfPTable(3);
			assinaturas.setWidthPercentage(100);
			float[] assinaturasWidth = { 7f, 1f, 7f };
			assinaturas.setWidths(assinaturasWidth);
			assinaturas.setSpacingBefore(50);

			PdfPCell assinaturaEmpresa = new PdfPCell(new Phrase(empresa.getNomEmpresa()));
			assinaturaEmpresa.setBorder(1);
			assinaturaEmpresa.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			assinaturaEmpresa.setFixedHeight(60);

			PdfPCell assinaturaCliente = new PdfPCell(new Phrase(osRapida.getClienteNome()));
			assinaturaCliente.setBorder(1);
			assinaturaCliente.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			assinaturaCliente.setFixedHeight(60);

			assinaturas.addCell(assinaturaEmpresa);
			assinaturas.addCell(space1);
			assinaturas.addCell(assinaturaCliente);

			documento.open();

			documento.add(divHeader);
			documento.add(titleRow2);
			documento.add(row2);
			documento.add(clienteDiv);
			documento.add(divClienteTitulo);
			documento.add(divDadosCliente);
			documento.add(servicoDiv);
			documento.add(divServicoTitulo);
			documento.add(divDadosServico);
			documento.add(valoresDiv);
			documento.add(divValoresTitulo);
			documento.add(divDadosValores);
			/* documento.add(observacoesDiv); */
			documento.add(tecnicoDiv);
			documento.add(assinaturas);

			documento.close();

			return bytes.toByteArray();
		}
	}

	public OsRapida buscarOsRapidaPorId(long id) {
		return osRapidaRepository.findById(id).orElse(null);
	}

}
