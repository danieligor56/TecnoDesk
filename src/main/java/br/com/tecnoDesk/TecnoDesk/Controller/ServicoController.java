package br.com.tecnoDesk.TecnoDesk.Controller;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.tecnoDesk.TecnoDesk.Component.EncryptionUtil;
import br.com.tecnoDesk.TecnoDesk.DTO.ClienteDTO;
import br.com.tecnoDesk.TecnoDesk.DTO.ServicoDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Cliente;
import br.com.tecnoDesk.TecnoDesk.Entities.Servico;
import br.com.tecnoDesk.TecnoDesk.Services.DecriptService;
import br.com.tecnoDesk.TecnoDesk.Services.ServicoItemService;
import br.com.tecnoDesk.TecnoDesk.Services.ServicoService;

@RestController
@Controller
@RequestMapping("api/v1/servico")
public class ServicoController {
	
	@Autowired
	ServicoService servicoService;
	
	@Autowired
	EncryptionUtil secUtil;

	@PostMapping("/adicionarServico")
	public ResponseEntity<Servico> adicionaNovoServico(@RequestBody ServicoDTO servicoDTO,@RequestHeader("CodEmpresa") String codEmpresa) throws Exception {
		servicoService.criarNovoServico(servicoDTO,codEmpresa);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/listaServico")
	public ResponseEntity<List<Servico>> listaClientes(@RequestHeader("CodEmpresa") String codemp) throws Exception {
			if(codemp != null) {
			
			return ResponseEntity.ok().body(servicoService.listarServico(Long.valueOf(secUtil.decrypt(codemp))));
		}	
			throw new BadRequestException("O Código, "+codemp+" não foi encontrado");
			
		}
	
	@GetMapping("/buscarServico/{id}")
	public Servico buscarClientePorID(long id,@RequestHeader("CodEmpresa") String codemp) {
		return servicoService.buscarServicoPorId(id,codemp);
	}
	
	@PutMapping("/alterarCliente")
	public ResponseEntity<Servico> alterarCliente(@RequestParam Long id, @RequestBody ServicoDTO servicoDTO,@RequestHeader("CodEmpresa") String codemp) {
		servicoService.alterarServico(id, servicoDTO,codemp);
		return ResponseEntity.ok().build();
	}
	
	
}
