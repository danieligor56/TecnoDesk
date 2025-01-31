package br.com.tecnoDesk.TecnoDesk.Controller;

import br.com.tecnoDesk.TecnoDesk.DTO.ServicoItemDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.ServicoItem;
import br.com.tecnoDesk.TecnoDesk.Services.ServicoItemService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servico-item")
@RequiredArgsConstructor
public class ServicoItemController {

	@Autowired
	ServicoItemService servicoItemService;


	@PostMapping("/novoServico")
	public ResponseEntity<ServicoItem> novoServico(@RequestBody ServicoItemDTO servicoItemDTO, @RequestHeader("CodEmpresa") String codEmpresa) throws BadRequestException {
		return ResponseEntity.ok().body(servicoItemService.criarServicoItem(servicoItemDTO, codEmpresa));
	}

	@GetMapping("/todosServicos")
	public ResponseEntity<List<ServicoItem>> listarTodosServicos(@RequestHeader("CodEmpresa") String codEmpresa) throws BadRequestException {
		List<ServicoItem> servicos = servicoItemService.buscarTodos();
		return ResponseEntity.ok(servicos);
	}


	@PutMapping("/atualizarServico/{id}")
	public ResponseEntity<ServicoItemDTO> atualizarServico(@PathVariable Long id, @RequestBody ServicoItemDTO servicoItemDTO, @RequestHeader("CodEmpresa") String codEmpresa) {
		ServicoItemDTO atualizado = servicoItemService.atualizarServicoItem(id, servicoItemDTO);
		return ResponseEntity.ok(atualizado);
	}


	@DeleteMapping("/deletarServico/{id}")
	public ResponseEntity<String> deletarServico(@PathVariable Long id) {
		return servicoItemService.deletarServicoItem(id);
	}
}
