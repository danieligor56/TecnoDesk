package br.com.tecnoDesk.TecnoDesk.Controller;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import br.com.tecnoDesk.TecnoDesk.Component.EncryptionUtil;
import br.com.tecnoDesk.TecnoDesk.DTO.LoginResponseDTO;
import br.com.tecnoDesk.TecnoDesk.DTO.UsuarioDTO;
import br.com.tecnoDesk.TecnoDesk.DTO.UsuarioRegisterDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Usuarios;
import br.com.tecnoDesk.TecnoDesk.Repository.UsuarioRepository;
import br.com.tecnoDesk.TecnoDesk.Services.TokenService;
import br.com.tecnoDesk.TecnoDesk.Services.UsuarioService;
import br.com.tecnoDesk.TecnoDesk.Services.DecriptService;
import exception.NotFound;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.List;

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
	DecriptService decriptService;

	@Autowired
	EncryptionUtil secUtil;

	@GetMapping("/listar")
	public ResponseEntity<List<Usuarios>> listarUsuarios(@RequestHeader("CodEmpresa") String codEmpresa)
			throws Exception {
		long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));
		return ResponseEntity.ok(usuarioService.listarUsuariosPorEmpresa(codEmp));
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid UsuarioDTO usuarioDTO, HttpSession httpSession) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(usuarioDTO.email(), usuarioDTO.pass());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var token = tokenService.generateToken((Usuarios) auth.getPrincipal());

		try {
			Usuarios usuario = usuarioRepository.findItByEmail(usuarioDTO.email());
			String codEmp = Long.toString(usuario.getEmpresa().getId());

			httpSession.setAttribute("CodEmpresa", codEmp);

			return ResponseEntity.ok()
					.body(new LoginResponseDTO(token, usuario.getNomeCompleto(), secUtil.encrypt(codEmp), secUtil.encrypt(usuario.getEmail())));
		
		} catch (Exception e) {
			throw new NotFound("Usuário não encontrado", e);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<Usuarios> registrar(@RequestBody @Valid UsuarioRegisterDTO usuarioDTO,
			@RequestParam long codEmpresa) throws Exception {
		usuarioService.registrarUsuario(usuarioDTO, codEmpresa);
		return ResponseEntity.ok().build();
	}

	@ResponseBody
	@GetMapping("/getCodEmp")
	public ResponseEntity<String> getCodEmpresa(@RequestParam String email) throws BadRequestException {
		var codEmpresa = usuarioService.getCodEmpresa(email);
		try {
			return ResponseEntity.ok().body(secUtil.encrypt(String.valueOf(codEmpresa)));
		} catch (Exception e) {
			throw new BadRequestException("Erro na consulta");
		}
	}

	@GetMapping("/vericarUsuario")
	public Boolean verificarUsuario(@RequestParam String email) {
		return this.usuarioRepository.existsByEmail(email);
	}
}
