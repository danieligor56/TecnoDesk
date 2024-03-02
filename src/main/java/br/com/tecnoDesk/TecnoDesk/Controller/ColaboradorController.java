package br.com.tecnoDesk.TecnoDesk.Controller;

import java.util.List;

import org.modelmapper.internal.bytebuddy.asm.Advice.OffsetMapping.ForOrigin.Renderer.ForReturnTypeName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.tecnoDesk.TecnoDesk.DTO.ColaboradorDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Colaborador;
import br.com.tecnoDesk.TecnoDesk.Services.ColaboradorService;

@Controller
@RequestMapping("api/v1/Colaborador")
@RestController

public class ColaboradorController {
	
	@Autowired
	ColaboradorService colaboradorService;
	
	@PostMapping("/AdicionaNovoColaborador")
	public ResponseEntity<Colaborador> criarNovoColaborador(@RequestBody ColaboradorDTO colaboradorDTO){
		colaboradorService.adicionaColaborador(colaboradorDTO);
		return ResponseEntity.noContent().build();
		
	}
	
	@GetMapping("/EncontrarUsuarioPorNome")
	public ResponseEntity<List<Colaborador>> encontrarPorNome( String nome){
		return ResponseEntity.ok().body(colaboradorService.encontraColaboradorPNome(nome));
	}
	
	@GetMapping("/listarColaboradores")
		public ResponseEntity<List<Colaborador>>listarColaboradores(){
			return ResponseEntity.ok().body(colaboradorService.listarColaboradores());
		}
	
	@GetMapping("/buscarporID")
	public ResponseEntity<Colaborador> colaboradorServicebuscarPorId(@RequestParam Long id){
		return ResponseEntity.ok().body(colaboradorService.buscarPorID(id));
	}
	
	@DeleteMapping("/deletarColaborador")
	public ResponseEntity<Colaborador> deletarColaboradoResponseEntity(@RequestParam Long id) {
			return ResponseEntity.ok().body(colaboradorService.deletarColaborador(id));
	}
	
	@PutMapping("/alterarColab/id")
	public ResponseEntity<Colaborador> alterarColab(@RequestParam long id,@RequestBody ColaboradorDTO colaboradorDTO){
		colaboradorService.alterColab(id, colaboradorDTO);
		return ResponseEntity.ok().build();
	}
	
}

