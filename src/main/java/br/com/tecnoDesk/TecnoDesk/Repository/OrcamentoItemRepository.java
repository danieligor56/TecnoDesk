package br.com.tecnoDesk.TecnoDesk.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.tecnoDesk.TecnoDesk.Entities.OrcamentoItem;

@Repository
public interface OrcamentoItemRepository extends JpaRepository<OrcamentoItem, Long>{

	/*
	 * @Query("DELETE i FROM orcamento_item WHERE i.codigo_empresa = :CodEmp and i.codigo_orcamento = :idOrcamento and i.id = :idProduto"
	 * ) void removerItemOrcamento(@Param("CodEmp") long
	 * CodEmp, @Param("idOrcamento") long idOrcamento, @Param("idProduto") long
	 * idProduto);
	 */
	
	@Query("SELECT i FROM OrcamentoItem i WHERE i.empresa.id = :CodEmp AND i.orcamento.id = :codigoOrcamento")
	List<OrcamentoItem> listaItens(@Param("CodEmp") long CodEmp, @Param("codigoOrcamento") long codigoOrcamento);
	
	@Query(value = "SELECT COUNT(*) FROM orcamento_item oi where codigo_orcamento = :codOrcamento and codigo_empresa = :codEmp",nativeQuery = true)
	int contarItensOrcamento(long codEmp,long codOrcamento);
	

}
