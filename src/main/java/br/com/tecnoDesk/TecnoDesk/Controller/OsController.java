package br.com.tecnoDesk.TecnoDesk.Controller;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.tecnoDesk.TecnoDesk.DTO.OsDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.OS;
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
	public ResponseEntity<OS> criarOS(@RequestBody OsDTO osDTO) throws BadRequestException {
		osService.crianova(osDTO);
		return ResponseEntity.ok().build();

	}

}
