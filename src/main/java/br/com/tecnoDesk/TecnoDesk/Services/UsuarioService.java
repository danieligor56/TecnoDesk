package br.com.tecnoDesk.TecnoDesk.Services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.tecnoDesk.TecnoDesk.Component.EncryptionUtil;
import br.com.tecnoDesk.TecnoDesk.DTO.UsuarioRegisterDTO;
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

	@Autowired
	Utils utils;

	public void registrarUsuario(UsuarioRegisterDTO usuarioDTO, long codEmpresa) throws Exception {

		if (this.usuarioRepository.findByEmail(usuarioDTO.email()) != null)
			throw new BadRequest("Email já cadastrado");

		String maskPassword = new BCryptPasswordEncoder().encode(usuarioDTO.pass());
		Usuarios novoUsuario = new Usuarios(usuarioDTO.email(), maskPassword, Roles.ADMIN, true,
				empresaRepository.findEmpresaById(codEmpresa), usuarioDTO.nomeCompleto());
		/* novoUsuario.setId((long) utils.callNextId(codEmpresa, 11)); */
		usuarioRepository.save(novoUsuario);
	}

	public void registrarUsuarioTelaInicial(String nomeCompleto, String email,
			String pass,
			long codEmpresa) throws Exception {
		try {

			if (this.usuarioRepository.findByEmail(email) != null)
				throw new BadRequest("Email já cadastrado");

			String maskPassword = new BCryptPasswordEncoder().encode(pass);
			Usuarios novoUsuario = new Usuarios(email, maskPassword, Roles.ADMIN, true,
					empresaRepository.findEmpresaById(codEmpresa), nomeCompleto);
			/* novoUsuario.setId((long) utils.callNextId(codEmpresa, 11)); */
			usuarioRepository.save(novoUsuario);

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public long getCodEmpresa(String email) {
		Usuarios user = usuarioRepository.findItByEmail(email);
		if (user != null) {
			return user.getEmpresa().getId();
		}

		throw new BadRequest("Usuario não encontrado");

	}

	public List<Usuarios> listarUsuariosPorEmpresa(long codEmpresa) {
		return usuarioRepository.findByEmpresaId(codEmpresa);
	}
}
