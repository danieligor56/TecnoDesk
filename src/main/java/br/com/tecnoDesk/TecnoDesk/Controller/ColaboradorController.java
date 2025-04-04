package br.com.tecnoDesk.TecnoDesk.Controller;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.modelmapper.internal.bytebuddy.asm.Advice.OffsetMapping.ForOrigin.Renderer.ForReturnTypeName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.tecnoDesk.TecnoDesk.Component.EncryptionUtil;
import br.com.tecnoDesk.TecnoDesk.DTO.ColaboradorDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Colaborador;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Entities.Usuarios;
import br.com.tecnoDesk.TecnoDesk.Services.ColaboradorService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("api/v1/Colaborador")
@RestController

public class ColaboradorController {
	
	@Autowired
	ColaboradorService colaboradorService;
	
	@Autowired
	EncryptionUtil secUtil;
	
	@PostMapping("/AdicionaNovoColaborador")
	public ResponseEntity<Colaborador> criarNovoColaborador(@RequestBody ColaboradorDTO colaboradorDTO,@RequestHeader("CodEmpresa") String codEmpresa) throws Exception{
		
		colaboradorService.adicionaColaborador(colaboradorDTO,codEmpresa);
		
		return ResponseEntity.ok().build();
		
	}
	
	@GetMapping("/EncontrarUsuarioPorNome")
	public ResponseEntity<List<Colaborador>> encontrarPorNome( String nome){
		return ResponseEntity.ok().body(colaboradorService.encontraColaboradorPNome(nome));
	}
	
	@GetMapping("/listarColaboradores")
	public ResponseEntity<List<Colaborador>>listarColaboradores(@RequestHeader("CodEmpresa") String codemp) throws Exception {

		if(codemp != null) {
			
			return ResponseEntity.ok().body(colaboradorService.listarColaboradores(Long.valueOf(secUtil.decrypt(codemp))));
		}	
			throw new BadRequestException("O Código, "+codemp+" não foi encontrado");
		}
	
	@GetMapping("/buscarporID")
	public ResponseEntity<Colaborador> colaboradorServicebuscarPorId(@RequestParam Long id,@RequestHeader("CodEmpresa") String codemp) throws Exception{
			return ResponseEntity.ok().body(colaboradorService.buscarPorID(id,codemp));
	}
	
	@DeleteMapping("/deletarColaborador")
	public ResponseEntity<Colaborador> deletarColaboradoResponseEntity(@RequestParam Long id,@RequestHeader("CodEmpresa") String codemp) throws Exception {
			return ResponseEntity.ok().body(colaboradorService.deletarColaborador(id,codemp));
	}
	 
	@PutMapping("/alterarColab/{id}")
	public ResponseEntity<Colaborador> alterarColab(@RequestParam long id,
			@RequestBody ColaboradorDTO colaboradorDTO,
			@RequestHeader("CodEmpresa") String codemp){
		
		colaboradorService.alterColab(id, colaboradorDTO,codemp);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("desativarColab/{id}")
	public ResponseEntity<Colaborador> desativaColaborador(@RequestParam long id) {
		colaboradorService.desativaColaborador(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("ativarColab/{id}")
	public ResponseEntity<Colaborador> ativaColaborador(@RequestParam long id) {
		colaboradorService.ativaColaborador(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("buscarPorEmpresa/{id}")
	public List<Colaborador> encontrarColaboradores(long id){
		
		return colaboradorService.encontrarColaborador(id);
	}
	
	
	  @GetMapping("/listarTecnicos") 
	  public ResponseEntity<List<Colaborador>>listarTecnicos(@RequestHeader("CodEmpresa")String codemp) throws Exception{
	  
	  if(codemp != null) {
	  
		  Long codempresa = Long.valueOf(secUtil.decrypt(codemp));
	 
		  return ResponseEntity.ok().body(colaboradorService.listarTecnicos(codempresa)); 
	  
	  } 
	  
	  throw new BadRequestException("O Código, "+codemp+" não foi encontrado"); }
	 
}

