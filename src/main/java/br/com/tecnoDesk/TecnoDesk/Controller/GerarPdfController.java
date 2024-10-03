package br.com.tecnoDesk.TecnoDesk.Controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;
import br.com.tecnoDesk.TecnoDesk.Services.GeneratePDfService;

@Controller
@RequestMapping("/gerarPdf")
@RestController
public class GerarPdfController {

	@Autowired
	GeneratePDfService pdfService;
	
	@GetMapping("osEntrada")
	public ResponseEntity<byte[]> gerarPdfEntrada() throws DocumentException, IOException {
		byte[] pdfOs = pdfService.gerarPdfOsentrada();
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=os.pdf");
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);
		
		return ResponseEntity.ok()
				.headers(headers)
				.body(pdfOs);
	}
	
}
