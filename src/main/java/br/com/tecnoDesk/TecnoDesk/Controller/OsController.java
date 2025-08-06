package br.com.tecnoDesk.TecnoDesk.Controller;

import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import br.com.tecnoDesk.TecnoDesk.DTO.OS_EntradaDTO;
import br.com.tecnoDesk.TecnoDesk.DTO.TecnicoEPrioridadeDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.OS_Entrada;
import br.com.tecnoDesk.TecnoDesk.Enuns.Ocupacao;
import br.com.tecnoDesk.TecnoDesk.Services.OsService;
import exception.BadRequest;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequestMapping("/Os")
@RestController

public class OsController {

	@Autowired
	OsService osService;

	@PostMapping("/criarNovaOS")
	public ResponseEntity<OS_Entrada> criarOsEntrada(@RequestBody OS_EntradaDTO osDTO,@RequestHeader("CodEmpresa") String codEmpresa) throws BadRequestException {		
		return ResponseEntity.ok().body(osService.crianova(osDTO,codEmpresa));

	}
	
	@GetMapping("/listarOS")
	public ResponseEntity<List<OS_Entrada>> listarOS(@RequestHeader("CodEmpresa") String codEmpresa) throws Exception{		
		return ResponseEntity.ok().body(osService.listarOS(codEmpresa));
	}
	
	@GetMapping("/numOs")
	public ResponseEntity<OS_Entrada> buscarOsPorNumero(@RequestParam long numOS ,@RequestHeader("CodEmpresa") String codEmpresa)throws Exception {
		return ResponseEntity.ok().body(osService.buscarPorNumOs(numOS,codEmpresa));
		
	}
	
	@PutMapping("/alterarTecnicEprioridade")
	public ResponseEntity alterarTecnicoEprioridade(@RequestBody TecnicoEPrioridadeDTO dto,
			@RequestHeader("CodEmpresa") String codEmpresa) {
		this.osService.alterarTecnicoPrioridadeOs(dto,codEmpresa);
		
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/alterarStatusOs")
	public ResponseEntity alterarStatusOs(@RequestParam long numOs,int stsOS, @RequestHeader("CodEmpresa") String codEmpresa ) throws BadRequestException {
		this.osService.alterarStatusDaOS(numOs, stsOS, codEmpresa);	
		return ResponseEntity.ok().build();
	}

}
