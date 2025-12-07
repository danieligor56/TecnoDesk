package br.com.tecnoDesk.TecnoDesk.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import br.com.tecnoDesk.TecnoDesk.DTO.OrcamentoDTO;
import br.com.tecnoDesk.TecnoDesk.DTO.TotaisNotaDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Orcamento;
import br.com.tecnoDesk.TecnoDesk.Entities.OrcamentoItem;
import br.com.tecnoDesk.TecnoDesk.Services.OrcamentoService;

import org.springframework.web.bind.annotation.DeleteMapping;
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
	
	
	
	  @PostMapping("inserirServico") public ResponseEntity<OrcamentoItem>
	  inserirServicoOrcamento(@RequestBody OrcamentoItem orcamentoItem,@RequestParam long orcamentoId ,@RequestHeader("CodEmpresa") String codEmpresa) {
		  OrcamentoItem novoItemOrcamento = service.addServicoOrcamento(orcamentoItem, orcamentoId,codEmpresa);
		  return ResponseEntity.ok().body(novoItemOrcamento); 
		  }
	  
	  @DeleteMapping("/exluirServico")
	  public ResponseEntity excluirServico(long idItemOrcamento, long codigoOrcamento, @RequestHeader("CodEmpresa") String codEmpresa) {
		  service.removerServicoFromOrcamento(idItemOrcamento, codigoOrcamento, codEmpresa);
		  return ResponseEntity.ok().body("Excluído com sucesso.");
	  } 
	  
	 
	  
	  
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
	  public ResponseEntity<TotaisNotaDTO> valorOrcamento(@RequestParam long orcamento_id,@RequestHeader("CodEmpresa") String codEmpresa) throws Exception{
		  return ResponseEntity.ok().body(service.calcularValorOrcamento(orcamento_id, codEmpresa));
	  }

	@PostMapping("atualizarDesconto")
	public ResponseEntity<OrcamentoItem> atualizarDesconto(@RequestParam long itemId, @RequestParam double desconto, @RequestHeader("CodEmpresa") String codEmpresa) throws Exception {
		OrcamentoItem itemAtualizado = service.atualizarDesconto(itemId, desconto, codEmpresa);
		return ResponseEntity.ok().body(itemAtualizado);
	}
	
}
