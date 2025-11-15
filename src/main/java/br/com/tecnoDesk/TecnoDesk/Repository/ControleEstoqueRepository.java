package br.com.tecnoDesk.TecnoDesk.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.tecnoDesk.TecnoDesk.Entities.ControleEstoque;

@Repository
public interface ControleEstoqueRepository extends JpaRepository<ControleEstoque, Long> {

	@Query(
		value = "select * from controle_estoque ce where ce.codigo_empresa = :codEmp",
		nativeQuery = true
	)
	List<ControleEstoque> listarControleEstoque(long codEmp);
	
	@Query(
		value = "select * from controle_estoque ce where ce.produto_id = :produtoId and ce.codigo_empresa = :codEmp",
		nativeQuery = true
	)
	Optional<ControleEstoque> encontrarPorProduto(long produtoId, long codEmp);
	
	@Query(
		value = "select * from controle_estoque ce where ce.estoque_atual <= ce.estoque_minimo and ce.codigo_empresa = :codEmp and ce.ativo = true",
		nativeQuery = true
	)
	List<ControleEstoque> listarProdutosEstoqueBaixo(long codEmp);
	
	@Query(
		value = "select * from controle_estoque ce where ce.codigo_item = :id and ce.codigo_empresa = :codEmp",
		nativeQuery = true
	)
	Optional<ControleEstoque> encontrarPorId(long id, long codEmp);

}

