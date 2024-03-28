package br.com.tecnoDesk.TecnoDesk.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.tecnoDesk.TecnoDesk.DTO.UsuarioDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Usuarios;
import br.com.tecnoDesk.TecnoDesk.Services.UsuarioService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("auth")

public class UsuarioController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@PostMapping("/login")
	public ResponseEntity<Usuarios> login(@RequestBody @Valid UsuarioDTO usuarioDTO ) {
		usuarioService.login(usuarioDTO);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/register")
	public ResponseEntity<Usuarios> registrar(@RequestBody @Valid UsuarioDTO usuarioDTO) {
		usuarioService.registrarUsuario(usuarioDTO);
		return ResponseEntity.ok().build();
	}
	
	

}
