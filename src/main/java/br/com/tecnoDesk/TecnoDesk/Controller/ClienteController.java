package br.com.tecnoDesk.TecnoDesk.Controller;

import java.util.List;
import org.apache.catalina.security.SecurityUtil;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.tecnoDesk.TecnoDesk.Component.EncryptionUtil;
import br.com.tecnoDesk.TecnoDesk.DTO.ClienteDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Cliente;
import br.com.tecnoDesk.TecnoDesk.Services.ClienteService;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@Controller
@RequestMapping("api/v1/cliente")

public class ClienteController {

	@Autowired
	ClienteService clienteService;
	
	@Autowired
	EncryptionUtil secUtil;

	@PostMapping("/adicionaNovoCliente")
	public ResponseEntity<Cliente> adicionaNovoCliente(@RequestBody ClienteDTO clienteDTO,@RequestHeader("CodEmpresa") String codEmpresa) throws Exception {
		clienteService.criaNovoCliente(clienteDTO,codEmpresa);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/listaClientes")
	public ResponseEntity<List<Cliente>> listaClientes(@RequestHeader("CodEmpresa") String codemp) throws Exception {
			if(codemp != null) {
			
			return ResponseEntity.ok().body(clienteService.listarClientes(Long.valueOf(secUtil.decrypt(codemp))));
		}	
			throw new BadRequestException("O Código, "+codemp+" não foi encontrado");
			
}

	@GetMapping("/buscarCliente/{id}")
	public Cliente buscarClientePorID(long id,@RequestHeader("CodEmpresa") String codemp) {
		return clienteService.buscarCliporId(id,codemp);
	}

	@GetMapping("/buscarCliente{nome}")
	public List<Cliente> buscarPorNome(@RequestParam String nome) {
		return clienteService.buscarPorNome(nome);
	}

	@PutMapping("/alterarCliente")
	public ResponseEntity<Cliente> alterarCliente(@RequestParam Long id, @RequestBody ClienteDTO clienteDTO,@RequestHeader("CodEmpresa") String codemp) {
		clienteService.alterarCliente(id, clienteDTO,codemp);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/buscarpordoc/{Doc}")
	public ResponseEntity<Cliente> encontrarPorDoc(String Doc) {
		return ResponseEntity.ok().body(clienteService.buscaPorDoc(Doc));
	}
	
	@DeleteMapping("deleteCliente/{id}")
	public void deletarCliente(@RequestParam long id,@RequestHeader("CodEmpresa") String codemp) throws Exception{
		clienteService.deletarCliente(id,codemp);
		ResponseEntity.ok().build();
	}

}
