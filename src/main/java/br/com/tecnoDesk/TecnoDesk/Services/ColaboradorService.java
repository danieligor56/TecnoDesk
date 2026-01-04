package br.com.tecnoDesk.TecnoDesk.Services;

import java.util.List;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.tecnoDesk.TecnoDesk.Component.EncryptionUtil;
import br.com.tecnoDesk.TecnoDesk.DTO.ColaboradorDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Colaborador;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Enuns.Ocupacao;
import br.com.tecnoDesk.TecnoDesk.Repository.ColaboradorRespository;
import br.com.tecnoDesk.TecnoDesk.Repository.EmpresaRepository;
import exception.BadRequest;
import exception.NotFound;

@Service
public class ColaboradorService {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	ColaboradorRespository colaboradorRespository;
	
	@Autowired
	EmpresaRepository empresaRepository;
	
	@Autowired
	EncryptionUtil secUtil;
	
	@Autowired
	DecriptService decriptService;
	
	@Autowired 
	Utils utils;

	public Colaborador adicionaColaborador(ColaboradorDTO colaboradorDTO, String codEmpresa) throws Exception {
				
		try {
			
				Empresa empresa = empresaRepository.findEmpresaById(decriptService.decriptCodEmp(codEmpresa));				
				colaboradorDTO.setEmpresa(empresa);
				
				var addNovCob = modelMapper.map(colaboradorDTO, Colaborador.class);
				addNovCob.setSequencial((long) utils.callNextId(empresa.getId(), 2));
				colaboradorRespository.save(addNovCob);				
				return null;
				
			} catch (Exception e) {
				throw new BadRequestException("Não localizamos contrato ativo",e);
			}
			
	}

	public List<Colaborador> encontraColaboradorPNome(String nome) {
		return colaboradorRespository.buscarPornome(nome);

	}

	public List<Colaborador> listarColaboradores(long CodEmp) {
		
		
		
		
		
		return colaboradorRespository.listAll(CodEmp);
	}

	public Colaborador buscarPorID(Long id,String codEmpresa) throws Exception {
		
		try {
				Empresa empresa = empresaRepository.findEmpresaById(decriptService.decriptCodEmp(codEmpresa));
				Colaborador colab = colaboradorRespository.findItById(id, empresa.getId());
				
							
				if(colab.getEmpresa().getId() == decriptService.decriptCodEmp(codEmpresa)) {
				return colab;
				
				}
				
				else {
					throw new NotFound("Não há colaboradores cadastrados com esse ID");
				}
			
				} 
		
				catch (Exception e) {
				throw new BadRequest("Não foi possível atender sua solicitação nesse moemnto");
				}

				}

	public Colaborador deletarColaborador(long id, String codEmpresa) throws Exception {
		
		try {
			
			Empresa empresa = empresaRepository.findEmpresaById(decriptService.decriptCodEmp(codEmpresa));
			Colaborador existeCob = colaboradorRespository.findItById(id, empresa.getId());
			String chave = secUtil.decrypt(codEmpresa);
			var colaboradorEmUso = colaboradorRespository.colaboradorEmUso(id,Long.valueOf(chave));
			
			if (existeCob != null) {
				 
				if(colaboradorEmUso > 0) {
					throw new BadRequest("O colaborador possui OS's registradas em seu nome");
				}
			
				if(existeCob.getEmpresa().getId() == Long.valueOf(chave))
				colaboradorRespository.deleteById(id);
				return null;
			}
			
				return null;
			
		} catch (Exception e) {
			throw new NotFound("Erro ao exluir o colaborardor: "+ e.getMessage());
		}
	}
		

	public void alterColab(long id, ColaboradorDTO colaboradorDTO,String codEmpresa) {
		
		try {
			
			Empresa empresa = empresaRepository.findEmpresaById(decriptService.decriptCodEmp(codEmpresa));
			Colaborador colab = colaboradorRespository.findItById(id, empresa.getId());
			
			if (colaboradorRespository.existsById(colab.getId())) {
			
				if(colab.getEmpresa().getId() == Long.valueOf(decriptService.decriptCodEmp(codEmpresa))) {
				
					Colaborador colaborador = modelMapper.map(colaboradorDTO, Colaborador.class);
					colaborador.setId(id);
					colaboradorRespository.save(colaborador);
				}
			
			}
		
		} catch (Exception e) {
			// TODO: handle exception
		}
	
	}

	public void desativaColaborador(long id, String codEmpresa) {
		
		try {
			
			Empresa empresa = empresaRepository.findEmpresaById(decriptService.decriptCodEmp(codEmpresa));
			Colaborador colab = colaboradorRespository.findItById(id, empresa.getId());
			colab.setAtvReg(true);
			
		} catch (Exception e) {  
			
			throw new NotFound("Não há colaboradores cadastrados com esse ID");
		}
	}
		
	public void ativaColaborador(long id,String codEmpresa) {
		
		try {
			
			Empresa empresa = empresaRepository.findEmpresaById(decriptService.decriptCodEmp(codEmpresa));
			Colaborador colab = colaboradorRespository.findItById(id, empresa.getId());
			colab.setAtvReg(false);
	
			
		} catch (Exception e) {
			
		throw new NotFound("Não há colaboradores cadastrados com esse ID");
		
		}
	}
		
	public List<Colaborador> encontrarColaborador(long id){
		return colaboradorRespository.findByCodEmpresa(id);
	}

	
	  public List<Colaborador> listarTecnicos(long CodEmp) throws Exception {
	  
	  
	  return colaboradorRespository.listarTecnicos(CodEmp);
	  
	  	}
	 
		

}
