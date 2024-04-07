package br.com.tecnoDesk.TecnoDesk.Services;

import java.util.concurrent.atomic.LongAccumulator;

import org.apache.catalina.webresources.EmptyResourceSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.access.event.PublicInvocationEvent;
import org.springframework.security.authentication.AuthenticationManager;
import br.com.tecnoDesk.TecnoDesk.DTO.UsuarioDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
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

		if(this.usuarioRepository.findByEmail(usuarioDTO.email()) != null)
			throw new BadRequest("Email já cadastrado");
	
		String maskPassword = new BCryptPasswordEncoder().encode(usuarioDTO.pass());
		Usuarios novoUsuario = new Usuarios(usuarioDTO.email(),maskPassword,Roles.ADMIN,true,usuarioDTO.empresa());
		usuarioRepository.save(novoUsuario);
	}

	
}
