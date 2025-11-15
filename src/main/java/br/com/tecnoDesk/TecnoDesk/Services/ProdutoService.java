package br.com.tecnoDesk.TecnoDesk.Services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.tecnoDesk.TecnoDesk.DTO.ProdutosDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Entities.Produtos;
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
				produtoRepository.save(novoProduto);			
						
		return novoProduto;
		} catch (Exception e) {
			throw new BadRequest("Não foi possível cadastrar o produto: "+ e);
		}
		
		
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
