package br.com.tecnoDesk.TecnoDesk.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.tecnoDesk.TecnoDesk.Entities.OS_Entrada;
import br.com.tecnoDesk.TecnoDesk.Services.GeneratePDfService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Controller
@RequestMapping("/gerarPdf")
@RestController
public class GerarPdfController {

	@Autowired
	GeneratePDfService pdfService;
	
	@GetMapping("/osEntrada")
	/*public ResponseEntity<byte[]> gerarPdfEntrada(@org.springframework.web.bind.annotation.RequestBody OS_Entrada os, @RequestHeader("CodEmpresa") String codEmpresa) throws Exception {
		byte[] pdfOs = pdfService.gerarPdfOsentrada(os,codEmpresa);*/
	
	public ResponseEntity<byte[]> gerarPdfEntrada() throws Exception {
		byte[] pdfOs = pdfService.gerarPdfOsentrada();
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=os.pdf");
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);
		
		return ResponseEntity.ok()
				.headers(headers)
				.body(pdfOs);
	}
	
}