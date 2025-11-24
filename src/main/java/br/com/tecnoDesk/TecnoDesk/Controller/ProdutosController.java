package br.com.tecnoDesk.TecnoDesk.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tecnoDesk.TecnoDesk.DTO.ProdutosDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Produtos;
import br.com.tecnoDesk.TecnoDesk.Entities.Servico;
import br.com.tecnoDesk.TecnoDesk.Services.ProdutoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@Controller
@RequestMapping("api/v1/produtos")
public class ProdutosController {
	
	@Autowired
	ProdutoService produtoService;
	
	@PostMapping("/criarProdutos")
	public ResponseEntity<Produtos> criarProduto(@RequestBody ProdutosDTO dto, @RequestHeader("CodEmpresa") String codEmpresa){
		return ResponseEntity.ok().body(produtoService.criarNovoProduto(dto, codEmpresa));
	}

	@GetMapping("/listarProdutos")
	public ResponseEntity<List<Produtos>> getMethodName(@RequestHeader("CodEmpresa") String codEmpresa) {
		return ResponseEntity.ok().body(produtoService.listarProdutos(codEmpresa));
	}
	
	@GetMapping("/buscarProduto/{id}")
	public Produtos buscarClientePorID(long id,@RequestHeader("CodEmpresa") String codemp) {
		return produtoService.buscarProdutoPorId(id,codemp);
	}
	
	@PutMapping("/alterarProduto/{id}")
	public ResponseEntity<Produtos> alterarProduto(@PathVariable long id, @RequestBody ProdutosDTO dto, @RequestHeader("CodEmpresa") String codEmpresa) {
		return ResponseEntity.ok().body(produtoService.alterarProduto(id, dto, codEmpresa));
	}
	
	@DeleteMapping("/deletarProduto/{id}")
	public ResponseEntity<Void> deletarProduto(@RequestParam long id, @RequestHeader("CodEmpresa") String codEmpresa) throws Exception {
		produtoService.deletarProduto(id, codEmpresa);
		return ResponseEntity.ok().build();
	}
	
}
