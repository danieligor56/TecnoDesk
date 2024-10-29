package br.com.tecnoDesk.TecnoDesk.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Services.EmpresaService;

@Controller
@RequestMapping("/empresa")
@RestController()
public class EmpresaController {
	
	@Autowired
	EmpresaService empresaService;
	
	@GetMapping("buscarEmpresaPorID")
	public ResponseEntity<Empresa> buscarEmpresaPorID(Long codEmpresa) {
		return ResponseEntity.ok().body(this.empresaService.buscarEmpresoPorID(codEmpresa));
		
	}
		

}
