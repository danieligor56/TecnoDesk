package br.com.tecnoDesk.TecnoDesk.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.tecnoDesk.TecnoDesk.DTO.OS_EntradaDTO;
import br.com.tecnoDesk.TecnoDesk.DTO.OrcamentoDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.OS_Entrada;
import br.com.tecnoDesk.TecnoDesk.Entities.Orcamento;
import br.com.tecnoDesk.TecnoDesk.Entities.OrcamentoItem;
import br.com.tecnoDesk.TecnoDesk.Services.OrcamentoService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@Controller
@RequestMapping("api/v1/orcamento")
public class OrcamentoController {
	
	@Autowired
	OrcamentoService service;
	
	@PostMapping("criarOrcamento")
	public ResponseEntity<Orcamento> criarOrcamento(@RequestBody OrcamentoDTO orcamento,@RequestParam long numOS,@RequestHeader("CodEmpresa") String codEmpresa){
		service.criarOrcamento(orcamento,numOS, codEmpresa);
		return ResponseEntity.ok().build();
	}
	
	
	
	  @PostMapping("inserirServico") public ResponseEntity<Orcamento>
	  inserirServicoOrcamento(@RequestBody OrcamentoItem orcamentoItem,
      @RequestParam long orcamentoId ,@RequestHeader("CodEmpresa") String
	  codEmpresa) { service.addServicoOrcamento(orcamentoItem, orcamentoId,
	  codEmpresa); return ResponseEntity.ok().build(); }
	  
	  @GetMapping("buscarOcamento")
	  public ResponseEntity<Orcamento> buscarOrcamentoID(@RequestParam long id, @RequestHeader("CodEmpresa") String codEmpresa){
		  	Orcamento orc = service.buscarPorNumOs(id, codEmpresa);
		  return ResponseEntity.ok().body(orc);
	  }
	  
	  @GetMapping("listarServicosOrcamento")
	  public ResponseEntity<List<OrcamentoItem>> listarServicosOrcamento(@RequestParam long orcamento_id,@RequestHeader("CodEmpresa") String codEmpresa){
		  return ResponseEntity.ok().body(service.listarItensOrca(codEmpresa, orcamento_id));
		  
	  }
	  @GetMapping("valorOrcamento")
	  public ResponseEntity<Double> valorOrcamento(@RequestParam long orcamento_id,@RequestHeader("CodEmpresa") String codEmpresa) throws Exception{
		  return ResponseEntity.ok().body(service.valorOrcamento(orcamento_id, codEmpresa));
	  }
	
}
