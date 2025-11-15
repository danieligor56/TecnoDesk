package br.com.tecnoDesk.TecnoDesk.Services;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.type.TrueFalseConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tecnoDesk.TecnoDesk.DTO.ControleEstoqueDTO;
import br.com.tecnoDesk.TecnoDesk.DTO.ProdutosDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.ControleEstoque;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Entities.Produtos;
import br.com.tecnoDesk.TecnoDesk.Repository.ControleEstoqueRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.EmpresaRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.ProdutoRepository;
import exception.BadRequest;
import exception.NotFound;
import br.com.tecnoDesk.TecnoDesk.Component.EncryptionUtil;

@Service
public class ProdutoService {
	@Autowired
	Utils utils;
	
	@Autowired 
	ProdutoRepository produtoRepository;
	
	@Autowired
	EmpresaRepository empresaRepository;
	
	@Autowired
	DecriptService decriptService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	EncryptionUtil secUtil;
	
	@Autowired
	ControleEstoqueRepository controleEstoqueRepository;
	
	public List<Produtos> listarProdutos(String codEmpresa){
		try {
			long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));
			return	produtoRepository.listarProdutos(codEmp);
		} catch (Exception e) {
			throw new BadRequest("Não foi possivel listar os serviços"+ e.getMessage());
			}
	}
	
	public Produtos criarNovoProduto(ProdutosDTO dto, String codEmpresa) {
		try {			
			
			Empresa emp = empresaRepository.findEmpresaById(Long.valueOf(decriptService.decriptCodEmp(codEmpresa)));
				long sequencial = (long) utils.callNextId(emp.getId(),8);
				
				Produtos novoProduto = modelMapper.map(dto, Produtos.class);
				novoProduto.setId(sequencial);
				novoProduto.setEmpresa(emp);
				
				// Se código de barras não foi informado (0 ou negativo), gera automaticamente no padrão EAN-13
				if(novoProduto.getCodigo_barras() <= 0) {
					long codigoBarras = gerarCodigoBarrasEAN13(sequencial, emp.getId());
					novoProduto.setCodigo_barras(codigoBarras);
				}
				
				produtoRepository.save(novoProduto);
				
				//ADICIONAR CONTROLE DE ESTOQUE AO PRODUTO.
				try {
					ControleEstoqueDTO controleEstoqueDTO = new ControleEstoqueDTO(novoProduto.getId(),BigDecimal.ZERO,BigDecimal.ZERO,"");
					ControleEstoque novoControleEstoque = modelMapper.map(controleEstoqueDTO, ControleEstoque.class);
					novoControleEstoque.setEstoqueAtual(BigDecimal.valueOf(novoProduto.getQuantidadeEstoque()));
					novoControleEstoque.setEmpresa(emp);
					controleEstoqueRepository.save(novoControleEstoque);
					
				} catch (Exception e) {
					throw new BadRequest("Não foi possível cadastrar o produto no estoque, por favor.adicione de forma manual"+ e.getMessage());
				}
				
				
				
						
		return novoProduto;
		} catch (Exception e) {
			throw new BadRequest("Não foi possível cadastrar o produto: "+ e);
		}
		
		
	}
	
	/**
	 * Gera um código de barras EAN-13 válido
	 * Formato: 789 (Brasil) + 9 dígitos (ID produto + código empresa) + dígito verificador
	 * 
	 * @param idProduto ID do produto
	 * @param idEmpresa ID da empresa
	 * @return Código de barras EAN-13 válido
	 */
	private long gerarCodigoBarrasEAN13(long idProduto, long idEmpresa) {
		// Código do país Brasil (789)
		String codigoPais = "789";
		
		// Combina ID do produto e empresa para formar 9 dígitos
		// Usa os últimos dígitos do ID da empresa e do produto para garantir unicidade
		String idEmpresaStr = String.valueOf(idEmpresa);
		String idProdutoStr = String.valueOf(idProduto);
		
		// Pega até 3 dígitos do ID da empresa (do final)
		String empresaPart = idEmpresaStr.length() > 3 ? 
			idEmpresaStr.substring(idEmpresaStr.length() - 3) : 
			String.format("%03d", Long.parseLong(idEmpresaStr));
		
		// Pega até 6 dígitos do ID do produto (do final)
		String produtoPart = idProdutoStr.length() > 6 ? 
			idProdutoStr.substring(idProdutoStr.length() - 6) : 
			String.format("%06d", Long.parseLong(idProdutoStr));
		
		// Combina para formar 9 dígitos
		String codigoProduto = empresaPart + produtoPart;
		
		// Garante exatamente 9 dígitos (preenche com zeros à esquerda se necessário)
		if(codigoProduto.length() < 9) {
			codigoProduto = String.format("%09d", Long.parseLong(codigoProduto));
		} else if(codigoProduto.length() > 9) {
			codigoProduto = codigoProduto.substring(codigoProduto.length() - 9);
		}
		
		// Monta os 12 primeiros dígitos (sem o verificador)
		String codigoSemVerificador = codigoPais + codigoProduto;
		
		// Calcula o dígito verificador
		int digitoVerificador = calcularDigitoVerificadorEAN13(codigoSemVerificador);
		
		// Retorna o código completo
		return Long.parseLong(codigoSemVerificador + digitoVerificador);
	}
	
	/**
	 * Calcula o dígito verificador para código EAN-13
	 * 
	 * @param codigo 12 primeiros dígitos do código
	 * @return Dígito verificador (0-9)
	 */
	private int calcularDigitoVerificadorEAN13(String codigo) {
		int soma = 0;
		
		// Multiplica dígitos nas posições ímpares por 1 e pares por 3
		for(int i = 0; i < codigo.length(); i++) {
			int digito = Character.getNumericValue(codigo.charAt(i));
			if(i % 2 == 0) {
				soma += digito * 1; // Posições ímpares (0, 2, 4, ...)
			} else {
				soma += digito * 3; // Posições pares (1, 3, 5, ...)
			}
		}
		
		// Calcula o dígito verificador
		int resto = soma % 10;
		return resto == 0 ? 0 : 10 - resto;
	}
	
	public Produtos alterarProduto(long id, ProdutosDTO dto, String codEmpresa) {
		try {
			
			long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));
			Produtos produtoExistente = produtoRepository.encontrarProduto(id, codEmp);
			
			if (produtoExistente != null) {
				
					// Atualiza os campos do produto existente com os dados do DTO
					produtoExistente.setNome(dto.getNome());
					produtoExistente.setDescricao(dto.getDescricao());
					produtoExistente.setMarcaProduto(dto.getMarcaProduto());
					produtoExistente.setPreco(dto.getPreco());
					produtoExistente.setPrecoCusto(dto.getPrecoCusto());
					produtoExistente.setQuantidadeEstoque(dto.getQuantidadeEstoque());
					produtoExistente.setCodigo_barras(dto.getCodigo_barras());
					produtoExistente.setCategoria(dto.getCategoria());
					produtoExistente.setUnidadeMedida(dto.getUnidadeMedida());
					produtoExistente.setProdutoAtivo(dto.isProdutoAtivo());
					
					produtoRepository.save(produtoExistente);
					return produtoExistente;
				
			} else {
				throw new NotFound("Não há produtos cadastrados com esse ID");
			}
		} catch (Exception e) {
			throw new BadRequest("Não foi possível alterar o produto: "+ e.getMessage());
		}
	}
	
	public void deletarProduto(long id, String codEmpresa) throws Exception {
		
		long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));
		Produtos produtoExistente = produtoRepository.encontrarProduto(id, codEmp);
	
		if (produtoExistente != null) {	
			
			if(produtoExistente.getEmpresa().getId() == codEmp) {
				produtoRepository.deleteById(id);
			} else {
				throw new NotFound("Produto não pertence a esta empresa");
			}
		} else {
			throw new NotFound("Não há produtos cadastrados com esse ID");
		}
	}
	
}
