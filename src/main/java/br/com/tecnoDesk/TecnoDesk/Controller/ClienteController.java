package br.com.tecnoDesk.TecnoDesk.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tecnoDesk.TecnoDesk.DTO.ClienteDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Cliente;
import br.com.tecnoDesk.TecnoDesk.Services.ClienteService;
@RestController
@Controller
@RequestMapping("api/v1/colaborador")
public class ClienteController {
	
	@Autowired
	ClienteService clienteService;
	
	@PostMapping("/adicionaNovoCliente")
	public ResponseEntity<Cliente> adicionaNovoCliente(ClienteDTO clienteDTO){
		clienteService.criaNovoCliente(clienteDTO);
		return ResponseEntity.ok().build();
	}

}
