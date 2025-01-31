package br.com.tecnoDesk.TecnoDesk.Controller;

import br.com.tecnoDesk.TecnoDesk.DTO.ServicoItemDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.ServicoItem;
import br.com.tecnoDesk.TecnoDesk.Services.ServicoItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servico-item")
@RequiredArgsConstructor
public class ServicoItemController {

	private final ServicoItemService servicoItemService;

	@PostMapping("/novoServico")
	public ResponseEntity<ServicoItemDTO> novoServico(@RequestBody ServicoItemDTO servicoItemDTO, @RequestHeader("CodEmpresa") String codEmpresa) {
		ServicoItem novoServico = servicoItemService.criarServicoItem(servicoItemDTO);
		ServicoItemDTO novoServicoDTO = new ServicoItemDTO(
				novoServico.getNome_servico(),
				novoServico.getDesc_servico(),
				novoServico.getValor_servico(),
				novoServico.getCusto_servico()
		);
		return ResponseEntity.ok(novoServicoDTO);
	}

	@PutMapping("/atualizarServico/{id}")
	public ResponseEntity<ServicoItemDTO> atualizarServico(@PathVariable Long id, @RequestBody ServicoItemDTO servicoItemDTO) {
		ServicoItemDTO atualizado = servicoItemService.atualizarServicoItem(id, servicoItemDTO);
		return ResponseEntity.ok(atualizado);
	}

	@DeleteMapping("/deletarServico/{id}")
	public ResponseEntity<Void> deletarServico(@PathVariable Long id) {
		servicoItemService.deletarServicoItem(id);
		return ResponseEntity.noContent().build();
	}
}
