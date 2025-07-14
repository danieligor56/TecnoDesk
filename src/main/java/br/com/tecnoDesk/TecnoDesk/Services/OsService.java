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
import br.com.tecnoDesk.TecnoDesk.DTO.TecnicoEPrioridadeDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Entities.OS_Entrada;
import br.com.tecnoDesk.TecnoDesk.Entities.Orcamento;
import br.com.tecnoDesk.TecnoDesk.Enuns.Ocupacao;
import br.com.tecnoDesk.TecnoDesk.Enuns.PrioridadeOS;
import br.com.tecnoDesk.TecnoDesk.Enuns.StatusOR;
import br.com.tecnoDesk.TecnoDesk.Repository.ClienteRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.ColaboradorRespository;
import br.com.tecnoDesk.TecnoDesk.Repository.EmpresaRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.OrcamentoRepository;
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
	
	@Autowired
	OrcamentoRepository orcamentoRepository;


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
			
			Orcamento novoOrcamentoOS = new Orcamento();
			novoOrcamentoOS.setEmpresa(empresa);
			novoOrcamentoOS.setOs(novaOs);
			novoOrcamentoOS.setStatusOR(StatusOR.NOVO);
			orcamentoRepository.save(novoOrcamentoOS);
			
			
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

	public void alterarTecnicoPrioridadeOs(TecnicoEPrioridadeDTO dto,String codEmpresa) {
		try {
	
			OS_Entrada os = osRepository.findByNumOs(dto.numOs,Long.valueOf(decriptService.decriptCodEmp(codEmpresa)));
			
			if(dto.tecnicoId > 0) {
				os.setTecnico_responsavel(colaboradorRespository.findItById(dto.tecnicoId,Long.valueOf(decriptService.decriptCodEmp(codEmpresa))));
			}
				
				switch (dto.prioridadeOS) {
				
					case 0: {
						
						os.setPrioridadeOS(PrioridadeOS.NORMAL);
						break;
					}
					
					case 1: {
						os.setPrioridadeOS(PrioridadeOS.URGENCIA);
						break;
					}
					
					case 2:{
						os.setPrioridadeOS(PrioridadeOS.GARANTIA);
						break;
					}
					
					case 3:{
						os.setPrioridadeOS(PrioridadeOS.PRIORITARIA);
						break;
					}
					default:
						throw new IllegalArgumentException("Unexpected value: " + dto.prioridadeOS);
				}
				
				osRepository.save(os);
				
		} catch (Exception e) {
			throw new BadRequest("Não foi possível alterar a OS: "+ e.getMessage());
		}
		
	}












}
