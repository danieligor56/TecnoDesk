package br.com.tecnoDesk.TecnoDesk.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import br.com.tecnoDesk.TecnoDesk.DTO.OS_EntradaDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Entities.OS_Entrada;
import br.com.tecnoDesk.TecnoDesk.Enuns.Ocupacao;
import br.com.tecnoDesk.TecnoDesk.Repository.ClienteRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.ColaboradorRespository;
import br.com.tecnoDesk.TecnoDesk.Repository.EmpresaRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.OsRepository;
import exception.BadRequest;

@Service
public class OsService {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	OsRepository osRepository;
	
	@Autowired
	ColaboradorRespository colaboradorRespository;
	
	@Autowired
	DecriptService decriptService;
	
	@Autowired
	EmpresaRepository empresaRepository;


	public OS_Entrada crianova(OS_EntradaDTO osDTO,String codEmpresa) throws BadRequestException {
		
		 
		
		try {
			
			Empresa empresa = empresaRepository.findEmpresaById(Long.valueOf(decriptService.decriptCodEmp(codEmpresa)));	
			
			OS_Entrada novaOs = modelMapper.map(osDTO, OS_Entrada.class);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			formatter.format(now);
			novaOs.setDataAbertura(formatter.format(now));
			novaOs.setEmpresa(empresa);
			
			OS_Entrada codUltimaOs = osRepository.findLastOne(empresa.getId());
			
			if(codUltimaOs == null) {
				novaOs.setNumOs(1);
			}
			else {
				novaOs.setNumOs(codUltimaOs.getNumOs() + 1);
			}
			
			osRepository.save(novaOs);
			
			return novaOs;
			
		} catch (Exception e) {
			throw new BadRequest("Não foi possível atender a solicitação no momento",e);
		}
		
		
		

	}
	
	public List<OS_Entrada> listarOS(String codEmpresa) throws Exception{
		List<OS_Entrada> oss = this.osRepository.findOsByEmpresaId(Long.valueOf(decriptService.decriptCodEmp(codEmpresa)));		
		return oss;
	}

	public OS_Entrada buscarPorNumOs(long numOs,String codEmpresa) throws Exception {
		Empresa empresa = empresaRepository.findEmpresaById(Long.valueOf(decriptService.decriptCodEmp(codEmpresa)));
		
		if(empresa != null) {
		OS_Entrada os =	osRepository.findByNumOs(numOs,empresa.getId());
		return os;
		}
		
		throw new BadRequest("Não foi possível atender sua solicitação nesse momento");
	}

}
