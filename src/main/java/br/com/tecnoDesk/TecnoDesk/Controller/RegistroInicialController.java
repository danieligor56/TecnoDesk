package br.com.tecnoDesk.TecnoDesk.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import br.com.tecnoDesk.TecnoDesk.DTO.EmpresaUsuarioDTO;
import br.com.tecnoDesk.TecnoDesk.Services.RegistroInicialService;


@Controller
@RestController
@RequestMapping("api/v1/primeiroPasso")
public class RegistroInicialController {

	@Autowired
	RegistroInicialService registroInicialService;
	
	@PostMapping
	public ResponseEntity enviarRegistroInical(@RequestBody EmpresaUsuarioDTO dto) {
			registroInicialService.iniciarRegistroInicial(dto);
		return ResponseEntity.ok().build();
	}
	
	
}
