package br.com.tecnoDesk.TecnoDesk.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.tecnoDesk.TecnoDesk.DTO.ClienteDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Cliente;
import br.com.tecnoDesk.TecnoDesk.Services.ClienteService;
import org.springframework.web.bind.annotation.RequestParam;


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
	
	@GetMapping("/listaClientes")
	public ResponseEntity<List<Cliente>> listaClientes() {
		return ResponseEntity.ok().body(clienteService.listarClientes());
	}
	
	@GetMapping("/buscarCliente/{id}")
	public Cliente buscarClientePorID(long id) {
		return clienteService.buscarCliporId(id);
	}
	
	
	 @GetMapping("/buscarCliente{nome}") 
	 public List<Cliente>buscarPorNome(@RequestParam String nome){ 
		 return clienteService.buscarPorNome(nome);
		 }
	 
	

}