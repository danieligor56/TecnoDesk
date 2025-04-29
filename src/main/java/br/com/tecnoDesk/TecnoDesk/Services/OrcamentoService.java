package br.com.tecnoDesk.TecnoDesk.Services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

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
	
	public void addServicoOrcamento(@RequestBody OrcamentoItem orcamentoItem,
			  long orcamentoId,@RequestHeader("CodEmpresa") String
	  codEmpresa) { 
		  
		  try { 
			  
			  Empresa codEmp = empresaRepository.findEmpresaById(Long.valueOf(decriptService.decriptCodEmp(codEmpresa))); 
			  	if(codEmp == null) {
			  		throw new NotFound("Empresa não encontrada");
			  	}
			  
			  Orcamento orcamentoOS = repository.encontrarOcamentoPorID(codEmp.getId(), orcamentoId);
			  	if(orcamentoOS == null) {
			  		throw new NotFound("O orçamento informado não existe");
			  	}
			  	
			  	if(orcamentoItem.getValorUnidadeAvulso() > 0 && orcamentoItem.getValorHoraAvulso() <= 0) {
			  		orcamentoOS.setValorOrcamento(orcamentoOS.getValorOrcamento() + orcamentoItem.getValorUnidadeAvulso());			  		
			  	}
			  	
			  	if(orcamentoItem.getValorUnidadeAvulso() <= 0 && orcamentoItem.getValorHoraAvulso() > 0) {
			  		orcamentoOS.setValorOrcamento(orcamentoOS.getValorOrcamento() + orcamentoItem.getValorHoraAvulso());
			  	}
			  				  	
			  	OrcamentoItem novoServico = modelMapper.map(orcamentoItem, OrcamentoItem.class); 
			  	novoServico.setOrcamento(orcamentoOS);
			  	novoServico.setEmpresa(codEmp);
				  		 
			  	orcamentoRepository.save(novoServico);
			 
			 		  
	  } catch (Exception e) { throw new
		  		BadRequest("Não foi possível inserir o serviço no orçamento"+e); }
	  
	  }

	public Orcamento buscarPorNumOs(long id,String codEmpresa) {

			try {
				
				long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));				
				OS_Entrada os = osRepository.findByNumOs(id, codEmp);			
				Orcamento orcamento = repository.encontrarOcamentoPorNumOS(codEmp,os.getId());
				
							
					return orcamento;
				} 
		
				catch (Exception e) {
				throw new BadRequest("Não foi possível atender sua solicitação nesse momento"+e);
				}

				
	}
	
	public List<OrcamentoItem> listarItensOrca( String codEmpresa,long codOrcamento) {
		try {
			long codEmpr = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));
			return repository.listaItens(codEmpr, codOrcamento);
		} 
		catch ( Exception ex) {
			throw new NotFound("Não existe produtos cadastrados"+ ex);
		}
	}
	
	public Double valorOrcamento(long idOrcamento, String codEmpresa) throws Exception {
		long codEmpr = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));
		var valorOrcamento= repository.encontrarOcamentoPorID(codEmpr,idOrcamento);
			return valorOrcamento.getValorOrcamento();
	}
	
	
}

