package br.com.tecnoDesk.TecnoDesk.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tecnoDesk.TecnoDesk.DTO.ControleEstoqueDTO;
import br.com.tecnoDesk.TecnoDesk.DTO.MovimentacaoEstoqueDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.ControleEstoque;
import br.com.tecnoDesk.TecnoDesk.Entities.MovimentacaoEstoque;
import br.com.tecnoDesk.TecnoDesk.Services.ControleEstoqueService;

@RestController
@Controller
@RequestMapping("api/v1/estoque")
public class ControleEstoqueController {
	
	@Autowired
	ControleEstoqueService controleEstoqueService;
	
	@GetMapping("/listar")
	public ResponseEntity<List<ControleEstoque>> listarControleEstoque(@RequestHeader("CodEmpresa") String codEmpresa) {
		return ResponseEntity.ok().body(controleEstoqueService.listarControleEstoque(codEmpresa));
	}
	
	@GetMapping("/estoqueBaixo")
	public ResponseEntity<List<ControleEstoque>> listarProdutosEstoqueBaixo(@RequestHeader("CodEmpresa") String codEmpresa) {
		return ResponseEntity.ok().body(controleEstoqueService.listarProdutosEstoqueBaixo(codEmpresa));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ControleEstoque> buscarControleEstoquePorId(@PathVariable Long id, @RequestHeader("CodEmpresa") String codEmpresa) {
		return ResponseEntity.ok().body(controleEstoqueService.buscarControleEstoquePorId(id, codEmpresa));
	}
	
	@PostMapping("/criarOuAtualizar")
	public ResponseEntity<ControleEstoque> criarOuAtualizarControleEstoque(@RequestBody ControleEstoqueDTO dto, @RequestHeader("CodEmpresa") String codEmpresa) {
		return ResponseEntity.ok().body(controleEstoqueService.criarOuAtualizarControleEstoque(dto, codEmpresa));
	}
	
	@PostMapping("/movimentacao")
	public ResponseEntity<MovimentacaoEstoque> realizarMovimentacao(@RequestBody MovimentacaoEstoqueDTO dto, @RequestHeader("CodEmpresa") String codEmpresa) {
		return ResponseEntity.ok().body(controleEstoqueService.realizarMovimentacao(dto, codEmpresa));
	}
	
	@GetMapping("/movimentacoes")
	public ResponseEntity<List<MovimentacaoEstoque>> listarMovimentacoes(@RequestHeader("CodEmpresa") String codEmpresa) {
		return ResponseEntity.ok().body(controleEstoqueService.listarMovimentacoes(codEmpresa));
	}
	
	@GetMapping("/movimentacoes/produto/{produtoId}")
	public ResponseEntity<List<MovimentacaoEstoque>> listarMovimentacoesPorProduto(@PathVariable Long produtoId, @RequestHeader("CodEmpresa") String codEmpresa) {
		return ResponseEntity.ok().body(controleEstoqueService.listarMovimentacoesPorProduto(produtoId, codEmpresa));
	}

}

