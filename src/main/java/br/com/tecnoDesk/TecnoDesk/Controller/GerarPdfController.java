package br.com.tecnoDesk.TecnoDesk.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.tecnoDesk.TecnoDesk.Entities.OS_Entrada;
import br.com.tecnoDesk.TecnoDesk.Entities.OsRapida;
import br.com.tecnoDesk.TecnoDesk.Services.GeneratePDfService;

@Controller
@RequestMapping("/gerarPdf")
@RestController
public class GerarPdfController {

	@Autowired
	GeneratePDfService pdfService;
	
	@PostMapping("/osEntrada")
	public ResponseEntity<byte[]> gerarPdfEntrada(@org.springframework.web.bind.annotation.RequestBody OS_Entrada os, @RequestHeader("CodEmpresa") String codEmpresa) throws Exception {
		byte[] pdfOs = pdfService.gerarPdfOsentrada(os,codEmpresa);
	
	/*public ResponseEntity<byte[]> gerarPdfEntrada() throws Exception {
		byte[] pdfOs = pdfService.gerarPdfOsentrada();*/
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=os.pdf");
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);
		
		return ResponseEntity.ok()
				.headers(headers)
				.body(pdfOs);
	}
	
	@PostMapping("/orcamento")
	public ResponseEntity<byte[]> gerarPdfOrcamento(@RequestParam long numOS, @RequestHeader("CodEmpresa") String codEmpresa) throws Exception {
		byte[] pdfOrcamento = pdfService.gerarPdfOrcamento(numOS, codEmpresa);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=orcamento.pdf");
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);

		return ResponseEntity.ok()
				.headers(headers)
				.body(pdfOrcamento);
	}

	@PostMapping("/osRapida")
	public ResponseEntity<byte[]> gerarPdfOsRapida(@RequestParam long id, @RequestHeader("CodEmpresa") String codEmpresa) throws Exception {
		OsRapida osRapida = pdfService.buscarOsRapidaPorId(id);
		if (osRapida == null) {
			throw new Exception("OS Rápida não encontrada");
		}

		byte[] pdfOsRapida = pdfService.gerarPdfOsRapida(osRapida, codEmpresa);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=os-rapida-" + id + ".pdf");
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);

		return ResponseEntity.ok()
				.headers(headers)
				.body(pdfOsRapida);
	}

}
