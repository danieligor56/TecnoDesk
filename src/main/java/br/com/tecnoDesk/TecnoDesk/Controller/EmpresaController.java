package br.com.tecnoDesk.TecnoDesk.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;

@Controller
@RequestMapping("/empresa")

public class EmpresaController {
	
	public Empresa empresa(Long codEmpresa) {
		return null;
	}
		

}
