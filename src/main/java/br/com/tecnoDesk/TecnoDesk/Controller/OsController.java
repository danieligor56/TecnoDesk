package br.com.tecnoDesk.TecnoDesk.Controller;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.tecnoDesk.TecnoDesk.DTO.OS_EntradaDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.OS_Entrada;
import br.com.tecnoDesk.TecnoDesk.Enuns.Ocupacao;
import br.com.tecnoDesk.TecnoDesk.Services.OsService;
import exception.BadRequest;

@Controller
@RequestMapping("/Os")
@RestController

public class OsController {

	@Autowired
	OsService osService;

	@PostMapping("/criarNovaOS")
	public ResponseEntity<OS_Entrada> criarOsEntrada(@RequestBody OS_EntradaDTO osDTO,@RequestHeader("CodEmpresa") String codEmpresa) throws BadRequestException {		
		return ResponseEntity.ok().body(osService.crianova(osDTO,codEmpresa));

	}

}
