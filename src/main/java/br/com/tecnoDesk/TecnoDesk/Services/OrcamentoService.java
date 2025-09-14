package br.com.tecnoDesk.TecnoDesk.Services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.yaml.snakeyaml.tokens.FlowMappingEndToken;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import br.com.tecnoDesk.TecnoDesk.Component.EncryptionUtil;
import br.com.tecnoDesk.TecnoDesk.DTO.OrcamentoDTO;
import br.com.tecnoDesk.TecnoDesk.DTO.OrcamentoItemDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Entities.OS_Entrada;
import br.com.tecnoDesk.TecnoDesk.Entities.Orcamento;
import br.com.tecnoDesk.TecnoDesk.Entities.OrcamentoItem;
import br.com.tecnoDesk.TecnoDesk.Entities.Servico;
import br.com.tecnoDesk.TecnoDesk.Entities.ServicoItem;
import br.com.tecnoDesk.TecnoDesk.Enuns.StatusOR;
import br.com.tecnoDesk.TecnoDesk.Repository.EmpresaRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.OrcamentoItemRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.OrcamentoRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.OsRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.ServicoItemRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.ServicoRepository;
import exception.BadRequest;
import exception.NotFound;
import lombok.experimental.var;

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
	
	@Autowired
	ServicoRepository servicoRepository;
	
	
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
	
	public OrcamentoItem addServicoOrcamento(@RequestBody OrcamentoItem orcamentoItem,
			  long orcamentoId,@RequestHeader("CodEmpresa") String
	  codEmpresa) { 
		
		OrcamentoItemDTO dto = new OrcamentoItemDTO();
		
		  try { 
			  
			  Empresa codEmp = empresaRepository.findEmpresaById(Long.valueOf(decriptService.decriptCodEmp(codEmpresa))); 
			  	if(codEmp == null) {
			  		throw new NotFound("Empresa não encontrada");
			  	}
			  	
			  	dto.setEmpresa(codEmp);
			  
			  Orcamento orcamentoOS = repository.encontrarOcamentoPorID(codEmp.getId(), orcamentoId);
			  	if(orcamentoOS == null) {
			  		throw new NotFound("O orçamento informado não existe");
			  	}
			  	
			  	dto.setOrcamento(orcamentoOS);
			  	
			  	if(orcamentoItem.getCodigoItem() > 0) {
			  		Servico servicoItem = servicoRepository.encontrarPorId(orcamentoItem.getCodigoItem(),codEmp.getId());
			  		dto.setCodigoItem(servicoItem.getId());
			  		dto.setNomeServicoAvulso(orcamentoItem.getNomeServicoAvulso());
			  		dto.setDescricaoServicoAvulso(orcamentoItem.getDescricaoServicoAvulso());
			  		dto.setAvulso(false);
			  	}
			  	
			  	else {
			  		
					dto.setCodigoItem(0);
					dto.setAvulso(true);
					dto.setNomeServicoAvulso(orcamentoItem.getNomeServicoAvulso());
					dto.setDescricaoServicoAvulso(orcamentoItem.getDescricaoServicoAvulso());
				}			  				  	
			  	
			  	if(orcamentoItem.getValorHoraAvulso() == null || orcamentoItem.getValorHoraAvulso() <= 0.0) {
			  		dto.setValorUnidadeAvulso(orcamentoItem.getValorUnidadeAvulso());
			  	} 
			  	
			  	else {
			  		dto.setValorHoraAvulso(orcamentoItem.getValorHoraAvulso());
			  		dto.setValorUnidadeAvulso(0.0);
				}
			  			  	
			  			
			  		OrcamentoItem novoServicoItem = new OrcamentoItem (
			  				dto.getEmpresa(),
			  				dto.getOrcamento(),
			  				dto.getCodigoItem(),
			  				dto.getNomeServicoAvulso(),
			  				dto.getDescricaoServicoAvulso(),
			  				dto.getValorUnidadeAvulso(),
			  				dto.getValorHoraAvulso(),
			  				dto.isAvulso()
			  				);
			  	orcamentoRepository.save(novoServicoItem);
		  	
			  	return novoServicoItem;
			 
			 		  
	  } catch (Exception e) { throw new
		  		BadRequest("Não foi possível inserir o serviço no orçamento"+ e); }
	  
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
		var valorOrcamento = repository.encontrarOcamentoPorID(codEmpr,idOrcamento);
			return valorOrcamento.getValorOrcamento();
	}
	
	public Double calcularValorOrcamento(long idOrcamento, String codEmpresa) throws Exception{
		double valorTotal = 0;
		long codEmpr = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));
		List<OrcamentoItem> itensOrcamento = repository.listaItens(codEmpr, idOrcamento);
		
		for (OrcamentoItem orcamentoItem : itensOrcamento) {
				if(orcamentoItem.getValorHoraAvulso() == null || orcamentoItem.getValorHoraAvulso() <= 0) {
					valorTotal += orcamentoItem.getValorUnidadeAvulso();
				}
					else {
						valorTotal += orcamentoItem.getValorHoraAvulso();
					}
				
		}
		
		return valorTotal;
	}
	
	
}

