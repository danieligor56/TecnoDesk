package br.com.tecnoDesk.TecnoDesk.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.tecnoDesk.TecnoDesk.Entities.MovimentacaoEstoque;

@Repository
public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, Long> {

	@Query(
		value = "select * from movimentacao_estoque me where me.codigo_empresa = :codEmp order by me.data_movimentacao desc",
		nativeQuery = true
	)
	List<MovimentacaoEstoque> listarMovimentacoes(long codEmp);
	
	@Query(
		value = "select * from movimentacao_estoque me where me.produto_id = :produtoId and me.codigo_empresa = :codEmp order by me.data_movimentacao desc",
		nativeQuery = true
	)
	List<MovimentacaoEstoque> listarMovimentacoesPorProduto(long produtoId, long codEmp);
	
	@Query(
		value = "select * from movimentacao_estoque me where me.codigo_empresa = :codEmp and me.data_movimentacao >= :dataInicio and me.data_movimentacao <= :dataFim order by me.data_movimentacao desc",
		nativeQuery = true
	)
	List<MovimentacaoEstoque> listarMovimentacoesPorPeriodo(long codEmp, java.time.LocalDateTime dataInicio, java.time.LocalDateTime dataFim);

}

