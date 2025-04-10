package br.com.tecnoDesk.TecnoDesk.Services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import br.com.tecnoDesk.TecnoDesk.Component.EncryptionUtil;
import br.com.tecnoDesk.TecnoDesk.DTO.OrcamentoDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Entities.OS_Entrada;
import br.com.tecnoDesk.TecnoDesk.Entities.Orcamento;
import br.com.tecnoDesk.TecnoDesk.Entities.OrcamentoItem;
import br.com.tecnoDesk.TecnoDesk.Enuns.StatusOR;
import br.com.tecnoDesk.TecnoDesk.Repository.EmpresaRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.OrcamentoItemRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.OrcamentoRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.OsRepository;
import exception.BadRequest;
import exception.NotFound;

@Service
public class OrcamentoService {
	
	@Autowired
	EncryptionUtil secUtil;
	
	@Autowired
	DecriptService decriptService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	OrcamentoRepository repository;
	
	@Autowired
	EmpresaRepository empresaRepository;

	@Autowired
	OsRepository osRepository;
	
	@Autowired
	OrcamentoItemRepository orcamentoRepository;
	
	
	
	public void criarOrcamento(OrcamentoDTO dto,long codOs,String codempresa) {
		try {
			Orcamento novoOrcamento = new Orcamento();
						
			Empresa codEmp = empresaRepository.findEmpresaById(Long.valueOf(decriptService.decriptCodEmp(codempresa)));
			if(codEmp == null) {
				throw new NotFound("OS selecionada não existe. ");
			}
			
			OS_Entrada os = osRepository.findByNumOs(codOs, codEmp.getId());
			
			if(os == null) {
				throw new NotFound("OS selecionada não existe. ");
			}
						
			novoOrcamento.setEmpresa(codEmp);
			novoOrcamento.setOs(os);
			novoOrcamento.setStatusOR(StatusOR.NOVO);
			repository.save(novoOrcamento);
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	  public void addServicoOrcamento(@RequestBody List<OrcamentoItem> orcamentoItem,
			  long orcamentoId,@RequestHeader("CodEmpresa") String
	  codEmpresa) { 
		  
		  try { 
			  List<OrcamentoItem> listItem = new ArrayList<>();
			  
		  
			  Empresa codEmp = empresaRepository.findEmpresaById(Long.valueOf(decriptService.decriptCodEmp(codEmpresa))); 
			  	if(codEmp == null) {
			  		throw new NotFound("Empresa não encontrada");
			  	}
			  
			  Orcamento orcamentoOS = repository.encontrarOcamentoPorID(codEmp.getId(), orcamentoId);
			  	if(orcamentoOS == null) {
			  		throw new NotFound("O orçamento informado não existe");
			  	}
			  	
			  for(OrcamentoItem servicos : orcamentoItem) { 
			  
				  
				  servicos.setOrcamento(orcamentoOS);
				  servicos.setEmpresa(codEmp);
				  
			
				if(servicos.getCodigoItem() == 0 ) {
					servicos.setAvulso(true);					  
						}
			  
			 
			  servicos.setAvulso(false);
			  orcamentoRepository.save(servicos);
			  listItem.add(servicos);
			  
			  			  
		  }
			  
			  orcamentoOS.setItens(listItem); 
	  
	  } catch (Exception e) { throw new
	  BadRequest("Não foi possível inserir o serviço no orçamento"+e); }
	  
	  }
	 
}
