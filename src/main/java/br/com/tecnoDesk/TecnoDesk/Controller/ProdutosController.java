package br.com.tecnoDesk.TecnoDesk.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.tecnoDesk.TecnoDesk.Entities.Produtos;
import br.com.tecnoDesk.TecnoDesk.Services.ProdutoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@Controller
@RequestMapping("api/v1/produtos")
public class ProdutosController {
	
	@Autowired
	ProdutoService produtoService;

	@GetMapping("/listarProdutos")
	public ResponseEntity<List<Produtos>> getMethodName(@RequestHeader("CodEmpresa") String codEmpresa) {
		return ResponseEntity.ok().body(produtoService.listarProdutos(codEmpresa));
	}
	
}
