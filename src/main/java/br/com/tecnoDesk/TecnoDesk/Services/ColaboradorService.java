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

	public Colaborador adicionaColaborador(ColaboradorDTO colaboradorDTO, String codEmpresa) throws Exception {
			
		
		
		try {
			
				Empresa empresa = empresaRepository.findEmpresaById(decriptService.decriptCodEmp(codEmpresa));
				
				colaboradorDTO.setEmpresa(empresa);
				
				var addNovCob = modelMapper.map(colaboradorDTO, Colaborador.class);
				
			
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
			
				Colaborador colab = colaboradorRespository.findItById(id);
				
							
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
		Colaborador existeCob = colaboradorRespository.findItById(id);
		
		if (existeCob != null) {
			
			 String chave = secUtil.decrypt(codEmpresa);
			
			if(existeCob.getEmpresa().getId() == Long.valueOf(chave))
			colaboradorRespository.deleteById(id);
			return null;
		}

		throw new NotFound("Não há colaboradores cadastrados com esse ID");
		
		}

	public void alterColab(Long id, ColaboradorDTO colaboradorDTO,String codEmpresa) {
		
		try {
			
			Colaborador colab = colaboradorRespository.findItById(id);
			
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

	public void desativaColaborador(long id) {
		
		try {
			
			Colaborador colab = colaboradorRespository.findItById(id);
			colab.setAtvReg(true);
			
		} catch (Exception e) {  
			
			throw new NotFound("Não há colaboradores cadastrados com esse ID");
		}
	}
		

	public void ativaColaborador(long id) {
		
		try {
			
			Colaborador colab = colaboradorRespository.findItById(id);
			colab.setAtvReg(false);
	
			
		} catch (Exception e) {
			
		throw new NotFound("Não há colaboradores cadastrados com esse ID");
		
		}
	}
	
	
	public List<Colaborador> encontrarColaborador(long id){
		return colaboradorRespository.findByCodEmpresa(id);
	}

	
	
	
		

}
