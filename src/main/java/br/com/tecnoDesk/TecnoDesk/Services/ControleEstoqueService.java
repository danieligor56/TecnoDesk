package br.com.tecnoDesk.TecnoDesk.Services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.tecnoDesk.TecnoDesk.DTO.ControleEstoqueDTO;
import br.com.tecnoDesk.TecnoDesk.DTO.MovimentacaoEstoqueDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.ControleEstoque;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Entities.MovimentacaoEstoque;
import br.com.tecnoDesk.TecnoDesk.Entities.Produtos;
import br.com.tecnoDesk.TecnoDesk.Repository.ControleEstoqueRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.EmpresaRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.MovimentacaoEstoqueRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.ProdutoRepository;
import exception.BadRequest;
import exception.NotFound;

@Service
public class ControleEstoqueService {
	
	@Autowired
	ControleEstoqueRepository controleEstoqueRepository;
	
	@Autowired
	MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	EmpresaRepository empresaRepository;
	
	@Autowired
	DecriptService decriptService;
	
	@Autowired
	Utils utils;
	
	/**
	 * Lista todos os controles de estoque da empresa
	 */
	public List<ControleEstoque> listarControleEstoque(String codEmpresa) {
		try {
			long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));
			return controleEstoqueRepository.listarControleEstoque(codEmp);
		} catch (Exception e) {
			throw new BadRequest("Não foi possível listar o controle de estoque: " + e.getMessage());
		}
	}
	
	/**
	 * Lista produtos com estoque abaixo do mínimo
	 */
	public List<ControleEstoque> listarProdutosEstoqueBaixo(String codEmpresa) {
		try {
			long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));
			return controleEstoqueRepository.listarProdutosEstoqueBaixo(codEmp);
		} catch (Exception e) {
			throw new BadRequest("Não foi possível listar produtos com estoque baixo: " + e.getMessage());
		}
	}
	
	/**
	 * Cria ou atualiza o controle de estoque de um produto
	 */
	@Transactional
	public ControleEstoque criarOuAtualizarControleEstoque(ControleEstoqueDTO dto, String codEmpresa) {
		try {
			long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));
			Empresa emp = empresaRepository.findEmpresaById(codEmp);
			Produtos produto = produtoRepository.encontrarProduto(dto.getProdutoId(), codEmp);
			
			if (produto == null) {
				throw new NotFound("Produto não encontrado");
			}
			
			Optional<ControleEstoque> controleExistente = controleEstoqueRepository.encontrarPorProduto(dto.getProdutoId(), codEmp);
			
			ControleEstoque controleEstoque;
			
			if (controleExistente.isPresent()) {
				// Atualiza controle existente
				controleEstoque = controleExistente.get();
				controleEstoque.setEstoqueMinimo(dto.getEstoqueMinimo() != null ? dto.getEstoqueMinimo() : controleEstoque.getEstoqueMinimo());
				controleEstoque.setEstoqueMaximo(dto.getEstoqueMaximo());
				controleEstoque.setObservacao(dto.getObservacao());
			} else {
				// Cria novo controle
				controleEstoque = new ControleEstoque();
				long sequencial = (long) utils.callNextId(emp.getId(), 9);
				controleEstoque.setCodigoItem(sequencial);
				controleEstoque.setEmpresa(emp);
				controleEstoque.setProduto(produto);
				controleEstoque.setEstoqueAtual(BigDecimal.valueOf(produto.getQuantidadeEstoque()));
				controleEstoque.setEstoqueMinimo(dto.getEstoqueMinimo() != null ? dto.getEstoqueMinimo() : BigDecimal.ZERO);
				controleEstoque.setEstoqueMaximo(dto.getEstoqueMaximo());
				controleEstoque.setObservacao(dto.getObservacao());
				controleEstoque.setAtivo(true);
			}
			
			controleEstoqueRepository.save(controleEstoque);
			return controleEstoque;
			
		} catch (Exception e) {
			throw new BadRequest("Não foi possível criar/atualizar o controle de estoque: " + e.getMessage());
		}
	}
	
	/**
	 * Realiza uma movimentação de estoque (entrada, saída ou ajuste)
	 */
	@Transactional
	public MovimentacaoEstoque realizarMovimentacao(MovimentacaoEstoqueDTO dto, String codEmpresa) {
		try {
			long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));
			Empresa emp = empresaRepository.findEmpresaById(codEmp);
			Produtos produto = produtoRepository.encontrarProduto(dto.getProdutoId(), codEmp);
			
			if (produto == null) {
				throw new NotFound("Produto não encontrado");
			}
			
			if (dto.getQuantidade() == null || dto.getQuantidade().compareTo(BigDecimal.ZERO) <= 0) {
				throw new BadRequest("Quantidade deve ser maior que zero");
			}
			
			// Busca ou cria controle de estoque
			Optional<ControleEstoque> controleOpt = controleEstoqueRepository.encontrarPorProduto(dto.getProdutoId(), codEmp);
			ControleEstoque controleEstoque;
			
			if (controleOpt.isPresent()) {
				controleEstoque = controleOpt.get();
			} else {
				// Cria controle automaticamente se não existir
				long sequencial = (long) utils.callNextId(emp.getId(), 9);
				controleEstoque = new ControleEstoque();
				controleEstoque.setCodigoItem(sequencial);
				controleEstoque.setEmpresa(emp);
				controleEstoque.setProduto(produto);
				controleEstoque.setEstoqueAtual(BigDecimal.valueOf(produto.getQuantidadeEstoque()));
				controleEstoque.setEstoqueMinimo(BigDecimal.ZERO);
				controleEstoque.setAtivo(true);
				controleEstoqueRepository.save(controleEstoque);
			}
			
			BigDecimal estoqueAnterior = controleEstoque.getEstoqueAtual();
			BigDecimal estoqueAtual;
			
			// Calcula novo estoque baseado no tipo de movimentação
			switch (dto.getTipoMovimentacao()) {
				case ENTRADA:
					estoqueAtual = estoqueAnterior.add(dto.getQuantidade());
					break;
				case SAIDA:
					if (estoqueAnterior.compareTo(dto.getQuantidade()) < 0) {
						throw new BadRequest("Estoque insuficiente. Estoque atual: " + estoqueAnterior);
					}
					estoqueAtual = estoqueAnterior.subtract(dto.getQuantidade());
					break;
				case AJUSTE:
					estoqueAtual = dto.getQuantidade();
					break;
				default:
					throw new BadRequest("Tipo de movimentação inválido");
			}
			
			// Atualiza estoque no controle
			controleEstoque.setEstoqueAtual(estoqueAtual);
			controleEstoqueRepository.save(controleEstoque);
			
			// Atualiza quantidade no produto
			produto.setQuantidadeEstoque(estoqueAtual.intValue());
			produtoRepository.save(produto);
			
			// Cria registro de movimentação
			MovimentacaoEstoque movimentacao = new MovimentacaoEstoque();
			long sequencialMov = (long) utils.callNextId(emp.getId(), 10);
			movimentacao.setId(sequencialMov);
			movimentacao.setEmpresa(emp);
			movimentacao.setProduto(produto);
			movimentacao.setTipoMovimentacao(dto.getTipoMovimentacao());
			movimentacao.setQuantidade(dto.getQuantidade());
			movimentacao.setEstoqueAnterior(estoqueAnterior);
			movimentacao.setEstoqueAtual(estoqueAtual);
			movimentacao.setDataMovimentacao(LocalDateTime.now());
			movimentacao.setObservacao(dto.getObservacao());
			movimentacao.setUsuarioResponsavel(dto.getUsuarioResponsavel());
			movimentacao.setOrigemMovimentacao(dto.getOrigemMovimentacao() != null ? dto.getOrigemMovimentacao() : "Manual");
			
			movimentacaoEstoqueRepository.save(movimentacao);
			
			return movimentacao;
			
		} catch (Exception e) {
			throw new BadRequest("Não foi possível realizar a movimentação: " + e.getMessage());
		}
	}
	
	/**
	 * Lista todas as movimentações de estoque
	 */
	public List<MovimentacaoEstoque> listarMovimentacoes(String codEmpresa) {
		try {
			long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));
			return movimentacaoEstoqueRepository.listarMovimentacoes(codEmp);
		} catch (Exception e) {
			throw new BadRequest("Não foi possível listar as movimentações: " + e.getMessage());
		}
	}
	
	/**
	 * Lista movimentações de um produto específico
	 */
	public List<MovimentacaoEstoque> listarMovimentacoesPorProduto(Long produtoId, String codEmpresa) {
		try {
			long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));
			return movimentacaoEstoqueRepository.listarMovimentacoesPorProduto(produtoId, codEmp);
		} catch (Exception e) {
			throw new BadRequest("Não foi possível listar as movimentações do produto: " + e.getMessage());
		}
	}
	
	/**
	 * Busca controle de estoque por ID
	 */
	public ControleEstoque buscarControleEstoquePorId(Long id, String codEmpresa) {
		try {
			long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));
			Optional<ControleEstoque> controle = controleEstoqueRepository.encontrarPorId(id, codEmp);
			
			if (controle.isPresent()) {
				return controle.get();
			} else {
				throw new NotFound("Controle de estoque não encontrado");
			}
		} catch (Exception e) {
			throw new BadRequest("Não foi possível buscar o controle de estoque: " + e.getMessage());
		}
	}

}

