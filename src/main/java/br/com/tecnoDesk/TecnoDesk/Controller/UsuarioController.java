package br.com.tecnoDesk.TecnoDesk.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tecnoDesk.TecnoDesk.DTO.LoginResponseDTO;
import br.com.tecnoDesk.TecnoDesk.DTO.UsuarioDTO;
import br.com.tecnoDesk.TecnoDesk.DTO.UsuarioRegisterDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Entities.Usuarios;
import br.com.tecnoDesk.TecnoDesk.Repository.UsuarioRepository;
import br.com.tecnoDesk.TecnoDesk.Services.TokenService;
import br.com.tecnoDesk.TecnoDesk.Services.UsuarioService;
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
	
    
	@SuppressWarnings("rawtypes")
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid UsuarioDTO usuarioDTO ) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(usuarioDTO.email(),usuarioDTO.pass());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var token = tokenService.generateToken((Usuarios)auth.getPrincipal());
		return ResponseEntity.ok(new LoginResponseDTO(token));	
	}
	
	@PostMapping("/register")
	public ResponseEntity<Usuarios> registrar(@RequestBody @Valid UsuarioRegisterDTO usuarioDTO) {
		usuarioService.registrarUsuario(usuarioDTO);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/getCodEmp")
	public Long getCodEmpresa(String email) {
		return usuarioService.getCodEmpresa(email);
	}
	
	
	

}
