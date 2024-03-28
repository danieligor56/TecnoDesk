package br.com.tecnoDesk.TecnoDesk.Services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import br.com.tecnoDesk.TecnoDesk.DTO.UsuarioDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Usuarios;
import br.com.tecnoDesk.TecnoDesk.Enuns.Roles;
import br.com.tecnoDesk.TecnoDesk.Repository.UsuarioRepository;
import exception.BadRequest;

@Service
public class UsuarioService {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	TokenService tokenService;
	
	public void registrarUsuario(UsuarioDTO usuarioDTO) {

		if(this.usuarioRepository.findByEmail(usuarioDTO.getEmail()) != null)
			throw new BadRequest("Email já cadastrado");
	
		String maskPassword = new BCryptPasswordEncoder().encode(usuarioDTO.getEmail());
		Usuarios novoUsuario = new Usuarios(usuarioDTO.email,maskPassword,Roles.ADMIN,true);
		usuarioRepository.save(novoUsuario);
	}
	
	public void login(UsuarioDTO usuarioDTO) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getPass());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var token = tokenService.generateToken((Usuarios)auth.getPrincipal());
	}

}
