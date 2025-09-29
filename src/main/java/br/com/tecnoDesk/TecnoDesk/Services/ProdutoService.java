package br.com.tecnoDesk.TecnoDesk.Services;

import java.util.List;

import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.tecnoDesk.TecnoDesk.Entities.Produtos;
import br.com.tecnoDesk.TecnoDesk.Repository.ProdutoRepository;
import exception.BadRequest;

@Service
public class ProdutoService {
	
	@Autowired 
	ProdutoRepository produtoRepository;
	
	@Autowired
	DecriptService decriptService;
	
	public List<Produtos> listarProdutos(String codEmpresa){
		try {
			long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));
			return	produtoRepository.listarProdutos(codEmp);
		} catch (Exception e) {
			throw new BadRequest("Não foi possivel listar os serviços"+ e.getMessage());
		}
	}
	
}
