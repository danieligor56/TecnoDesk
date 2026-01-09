package br.com.tecnoDesk.TecnoDesk.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.DtoInstantiatingConverter;
import org.springframework.stereotype.Service;

import br.com.tecnoDesk.TecnoDesk.DTO.EmpresaUsuarioDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Entities.Usuarios;
import br.com.tecnoDesk.TecnoDesk.Repository.EmpresaRepository;
import exception.BadRequest;
import lombok.extern.java.Log;

@Service
public class RegistroInicialService {
	
	@Autowired
	Utils utils;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	EmpresaRepository empresaRepository;
		
	public void iniciarRegistroInicial(EmpresaUsuarioDTO dto) {
		try {
			
			// *** REGISTRANDO NOVA EMPRESA PRIMEIRO. 
			Empresa novaEmpresa = new Empresa();
			
			novaEmpresa.setRazaoSocial(dto.getRazaoSocial());
			novaEmpresa.setNomEmpresa(dto.getNomEmpresa());
			
			// SE O USUÁRIO FOR CPF OU NÃO PREFERIR REGISTRAR O CNPJ A RAZÃO SOCIAL DEVE SER O 
			// MESMO NOME EMPRESA. 
			// TEMOS QUE VALIDAR SE EM TODOS OS RELATORIOS AS INFORMAÇÕES VÃO ESTAR CORRETAS. 
			
			if(dto.getRazaoSocial().isEmpty() || dto.getRazaoSocial().isEmpty()) {
				novaEmpresa.setRazaoSocial(dto.getNomEmpresa());
			}
			
			novaEmpresa.setDocEmpresa(dto.getDocEmpresa());
			novaEmpresa.setDocEmpresa(dto.getMail());
			novaEmpresa.setCel(dto.getCel());
			novaEmpresa.setTel(dto.getTel());
			novaEmpresa.setSite(dto.getSite());
			
			novaEmpresa.setCep(dto.getCep());
			novaEmpresa.setLogra(dto.getLogra());
			novaEmpresa.setNum(dto.getNum());
			novaEmpresa.setComp(dto.getComp());
			novaEmpresa.setBairro(dto.getBairro());
			novaEmpresa.setMunicipio(dto.getMunicipio());
			novaEmpresa.setUf(dto.getUf());
			
			// PEGANDO SEQUENCIAL PARA NOVA EMPRESA. 
			novaEmpresa.setId((long) utils.callNextId(1,3 ));
			empresaRepository.save(novaEmpresa);
			
			// REGISTRAR NOVO USUÁRIO. 			
			usuarioService.registrarUsuarioTelaInicial(
					dto.getNomeCompleto(),
					dto.getEmail(),
					dto.getPass(),
					novaEmpresa.getId()
					);
						
			
			
			
		} catch (Exception e) {
			throw new BadRequest("Não foi possível criar a empresa, por favor acione o suporte"+ e.getMessage());
		}
	}
	
}
