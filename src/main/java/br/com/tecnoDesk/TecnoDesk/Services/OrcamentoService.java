package br.com.tecnoDesk.TecnoDesk.Services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.DtoInstantiatingConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.yaml.snakeyaml.tokens.FlowMappingEndToken;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import br.com.tecnoDesk.TecnoDesk.Component.EncryptionUtil;
import br.com.tecnoDesk.TecnoDesk.DTO.OrcamentoDTO;
import br.com.tecnoDesk.TecnoDesk.DTO.OrcamentoItemDTO;
import br.com.tecnoDesk.TecnoDesk.DTO.TotaisNotaDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Entities.OS_Entrada;
import br.com.tecnoDesk.TecnoDesk.Entities.Orcamento;
import br.com.tecnoDesk.TecnoDesk.Entities.OrcamentoItem;
import br.com.tecnoDesk.TecnoDesk.Entities.Produtos;
import br.com.tecnoDesk.TecnoDesk.Entities.Servico;
import br.com.tecnoDesk.TecnoDesk.Entities.ServicoItem;
import br.com.tecnoDesk.TecnoDesk.Enuns.ProdutoServicoEnum;
import br.com.tecnoDesk.TecnoDesk.Enuns.StatusOR;
import br.com.tecnoDesk.TecnoDesk.Repository.EmpresaRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.OrcamentoItemRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.OrcamentoRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.OsRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.ProdutoRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.ServicoItemRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.ServicoRepository;
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
	
	@Autowired
	ServicoRepository servicoRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	
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
			  		
			  		long id = 0;
			  		
			  		
					if(orcamentoItem.getProdutoOuServico() == ProdutoServicoEnum.SERVICO) {
						Servico servico = servicoRepository.encontrarPorId(orcamentoItem.getCodigoItem(),codEmp.getId());
						id = servico.getId();
						dto.setCodigoItem(servico.getId());
						dto.setProdutoOuServico(ProdutoServicoEnum.SERVICO);
						
					}
					
					else {
						Produtos produto = produtoRepository.encontrarProduto(orcamentoItem.getCodigoItem(),codEmp.getId());
						id = produto.getId();
						dto.setCodigoItem(produto.getId());
						dto.setProdutoOuServico(ProdutoServicoEnum.PRODUTO);
					}
			  				  		
			  		dto.setNomeServicoAvulso(orcamentoItem.getNomeServicoAvulso());
			  		dto.setDescricaoServicoAvulso(orcamentoItem.getDescricaoServicoAvulso());
			  		dto.setAvulso(false);
			  	}
			  	
			  	else {
			  		
					dto.setCodigoItem(0);
					dto.setAvulso(true);
					dto.setNomeServicoAvulso(orcamentoItem.getNomeServicoAvulso());
					dto.setDescricaoServicoAvulso(orcamentoItem.getDescricaoServicoAvulso());
					dto.setProdutoOuServico(ProdutoServicoEnum.SERVICO);
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
			  				dto.isAvulso(),
			  				dto.getProdutoOuServico()
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
			List<OrcamentoItem> orcList = orcamentoRepository.listaItens(codEmpr, codOrcamento);
			List<OrcamentoItem> orcListVlrLiq = new ArrayList<>();
			double valorLiquio = 0;
			
			for (OrcamentoItem orcamentoItem : orcList) {							
				
				if(orcamentoItem.getValorTotal() != null) {
					
				}
				
				if( orcamentoItem.getValorHoraAvulso() == null || orcamentoItem.getValorHoraAvulso() <= 0 ) {
						valorLiquio = orcamentoItem.getValorUnidadeAvulso() - orcamentoItem.getDescontoServico();
						orcListVlrLiq.add(orcamentoItem);
					}
						
					else {
						valorLiquio = orcamentoItem.getValorHoraAvulso() - orcamentoItem.getDescontoServico();
						orcListVlrLiq.add(orcamentoItem);
					}
				
				orcamentoItem.setValorTotal(valorLiquio);
				
			}
			
			return orcListVlrLiq;
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
	
	public TotaisNotaDTO calcularValorOrcamento(long idOrcamento, String codEmpresa) throws Exception {

	    TotaisNotaDTO totais = new TotaisNotaDTO();

	    // garante que tudo começa em ZERO
	    totais.setValorTotalProdutos(BigDecimal.ZERO);
	    totais.setValorTotalServico(BigDecimal.ZERO);
	    totais.setValorTotalDescontoProdutos(BigDecimal.ZERO);
	    totais.setValorTotalDescontoServico(BigDecimal.ZERO);
	    totais.setValorTotalNota(BigDecimal.ZERO);

	    long codEmpr = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));
	    List<OrcamentoItem> itens = orcamentoRepository.listaItens(codEmpr, idOrcamento);

	    if (itens == null || itens.isEmpty()) {
	        return totais;
	    }

	    for (OrcamentoItem item : itens) {

	        if (item == null) {
	            continue;
	        }

	        // Normaliza nulos para ZERO
	        BigDecimal valorUnidade = toBigDecimal(item.getValorUnidadeAvulso());
	        BigDecimal valorHora    = toBigDecimal(item.getValorHoraAvulso());
	        BigDecimal desconto     = toBigDecimal(item.getDescontoServico());

	        if (item.getProdutoOuServico() == ProdutoServicoEnum.SERVICO) {

	            // acumula desconto de serviços
	            if (desconto.compareTo(BigDecimal.ZERO) > 0) {
	                totais.setValorTotalDescontoServico(
	                    totais.getValorTotalDescontoServico().add(desconto)
	                );
	            }

	            BigDecimal valorServico;

	            // Se tiver valor por hora (> 0), prioriza ele
	            if (valorHora.compareTo(BigDecimal.ZERO) > 0) {
	                valorServico = valorHora;
	            } else {
	                // Usa valor unitário com desconto
	                valorServico = valorUnidade.subtract(desconto);
	            }

	            // Não deixa valor negativo, por segurança
	            if (valorServico.compareTo(BigDecimal.ZERO) < 0) {
	                valorServico = BigDecimal.ZERO;
	            }

	            totais.setValorTotalServico(
	                totais.getValorTotalServico().add(valorServico)
	            );

	        } else { // PRODUTO

	            if (desconto.compareTo(BigDecimal.ZERO) > 0) {
	                totais.setValorTotalDescontoProdutos(
	                    totais.getValorTotalDescontoProdutos().add(desconto)
	                );
	            }

	            BigDecimal valorProduto = valorUnidade.subtract(desconto);

	            if (valorProduto.compareTo(BigDecimal.ZERO) < 0) {
	                valorProduto = BigDecimal.ZERO;
	            }

	            totais.setValorTotalProdutos(
	                totais.getValorTotalProdutos().add(valorProduto)
	            );
	        }
	    }

	    // total geral = produtos + serviços
	    totais.setValorTotalNota(
	        totais.getValorTotalProdutos().add(totais.getValorTotalServico())
	    );

	    return totais;
	}

	/**
	 * Helper pra evitar if de null toda hora.
	 */
	private BigDecimal defaultZero(BigDecimal valor) {
	    return valor == null ? BigDecimal.ZERO : valor;
	}
	
	private BigDecimal toBigDecimal(Number valor) {
	    return valor == null ? BigDecimal.ZERO : BigDecimal.valueOf(valor.doubleValue());
	}

	public void removerServicoFromOrcamento(long idItemOrcamento, long codigoOrcamento, String codEmpresa) {
		try {
			long codEmpr = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));
				repository.excluirItemOrcamento(idItemOrcamento,codigoOrcamento, codEmpr);
					
		} catch (Exception e) {
			throw new BadRequest("Não foi possível excluir o item do orçamento: "+ e.getMessage());
		
		}
	}
	
}

