package br.com.tecnoDesk.TecnoDesk.Services;

import java.util.List;
import java.util.Optional;

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

	public Colaborador adicionaColaborador(ColaboradorDTO colaboradorDTO, String codEmpresa) throws Exception {
			
		
		
		try {
			
				var decriptCodEmp = secUtil.decrypt(codEmpresa);
			
				Empresa empresa = empresaRepository.findEmpresaById(Long.valueOf(decriptCodEmp));
				
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

	public List<Colaborador> listarColaboradores(long id) {
		return colaboradorRespository.listAll(id);
	}

	public Colaborador buscarPorID(Long id) {
		Optional<Colaborador> colab = colaboradorRespository.findById(id);

		if (colab.isPresent())

			return colab.get();

		else {

			throw new NotFound("Não há colaboradores cadastrados com esse ID");
		}

	}

	public Colaborador deletarColaborador(long id) {
		Optional<Colaborador> existeCob = colaboradorRespository.findById(id);
		if (existeCob.isPresent()) {

			colaboradorRespository.deleteById(id);
			return null;
		}

		throw new NotFound("Não há colaboradores cadastrados com esse ID");
		
		}

	public void alterColab(Long id, ColaboradorDTO colaboradorDTO) {
		Optional<Colaborador> colab = colaboradorRespository.findById(id);

		if (colab.isPresent()) {

			Colaborador colaborador = modelMapper.map(colaboradorDTO, Colaborador.class);
			colaborador.setId(id);
			colaboradorRespository.save(colaborador);

		}

		throw new NotFound("Não há colaboradores cadastrados com esse ID");
	}

	public void desativaColaborador(long id) {
		
		try {
			
			Colaborador colab = colaboradorRespository.findItById(id);
			colab.setAtvReg(false);
			
		} catch (Exception e) {  
			
			throw new NotFound("Não há colaboradores cadastrados com esse ID");
		}
	}
		

	public void ativaColaborador(long id) {
		
		try {
			
			Colaborador colab = colaboradorRespository.findItById(id);
			colab.setAtvReg(true);
	
			
		} catch (Exception e) {
			
		throw new NotFound("Não há colaboradores cadastrados com esse ID");
		
		}
	}
	
	
	public List<Colaborador> encontrarColaborador(long id){
		return colaboradorRespository.findByCodEmpresa(id);
	}

	
	
	
		

}
