package br.com.tecnoDesk.TecnoDesk.Controller;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import br.com.tecnoDesk.TecnoDesk.Component.EncryptionUtil;
import br.com.tecnoDesk.TecnoDesk.DTO.LoginResponseDTO;
import br.com.tecnoDesk.TecnoDesk.DTO.UsuarioDTO;
import br.com.tecnoDesk.TecnoDesk.DTO.UsuarioRegisterDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Usuarios;
import br.com.tecnoDesk.TecnoDesk.Repository.UsuarioRepository;
import br.com.tecnoDesk.TecnoDesk.Services.TokenService;
import br.com.tecnoDesk.TecnoDesk.Services.UsuarioService;
import br.com.tecnoDesk.TecnoDesk.Services.Utils;
import exception.NotFound;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class UsuarioController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UsuarioService usuarioService;

	@Autowired
	TokenService tokenService;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	EncryptionUtil secUtil;
	
    
	@SuppressWarnings("rawtypes")
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid UsuarioDTO usuarioDTO,HttpSession httpSession ) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(usuarioDTO.email(),usuarioDTO.pass());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var token = tokenService.generateToken((Usuarios)auth.getPrincipal());
		
		try {
			Usuarios usuario = usuarioRepository.findItByEmail(usuarioDTO.email());
			String codEmp = Long.toString(usuario.getEmpresa().getId());
			
			httpSession.setAttribute("CodEmpresa", codEmp);
			org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();

			return ResponseEntity.ok().body(new LoginResponseDTO(token,usuario.getNomeCompleto(),secUtil.encrypt(codEmp)));
			 
			
		} catch (Exception e) {
			
			throw new NotFound("Usuário não encontrado",e);
		}
		
		
		
		/* new LoginResponseDTO(token) */
	}
	
	@PostMapping("/register")
public ResponseEntity<Usuarios> registrar(@RequestBody @Valid UsuarioRegisterDTO usuarioDTO,long codEmpresa) throws Exception {
		usuarioService.registrarUsuario(usuarioDTO,codEmpresa);
		return ResponseEntity.ok().build();
	}
	
	@ResponseBody
	@GetMapping("/getCodEmp")
	public ResponseEntity<String> getCodEmpresa(String email) throws BadRequestException {
		
		var codEmpresa = usuarioService.getCodEmpresa(email);
		
		try {
			
			return ResponseEntity.ok().body(secUtil.encrypt(String.valueOf(codEmpresa)));
		
		} catch (Exception e) {
			
			throw new BadRequestException("Erro na consulta");
		}
		
		
		
		
	}
	
	@GetMapping("/vericarUsuario")
	public Boolean verificarUsuario(String email) {
		return this.usuarioRepository.existsByEmail(email);
		
	}
	
	
	

}
