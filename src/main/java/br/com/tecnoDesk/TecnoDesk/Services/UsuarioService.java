package br.com.tecnoDesk.TecnoDesk.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.tecnoDesk.TecnoDesk.Component.EncryptionUtil;
import br.com.tecnoDesk.TecnoDesk.DTO.UsuarioRegisterDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Entities.Usuarios;
import br.com.tecnoDesk.TecnoDesk.Enuns.Roles;
import br.com.tecnoDesk.TecnoDesk.Repository.EmpresaRepository;
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
	
	@Autowired
	EncryptionUtil secUtil;
	
	@Autowired
	EmpresaRepository empresaRepository;
	
	public void registrarUsuario(UsuarioRegisterDTO usuarioDTO,long codEmpresa) throws Exception {

		
		
		
		if(this.usuarioRepository.findByEmail(usuarioDTO.email()) != null)
			throw new BadRequest("Email já cadastrado");
	
		String maskPassword = new BCryptPasswordEncoder().encode(usuarioDTO.pass());
		Usuarios novoUsuario = new Usuarios(usuarioDTO.email(),maskPassword,Roles.ADMIN,true,empresaRepository.findEmpresaById(codEmpresa),usuarioDTO.nomeCompleto());
		
		usuarioRepository.save(novoUsuario);
	}
	
	public long getCodEmpresa(String email) {
			Usuarios user = usuarioRepository.findItByEmail(email);
		if(user != null) {
			return user.getEmpresa().getId();
		}
		
		throw new BadRequest("Usuario não encontrado");
		
	}

	
}
