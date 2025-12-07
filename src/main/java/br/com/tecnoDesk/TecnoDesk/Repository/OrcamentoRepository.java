package br.com.tecnoDesk.TecnoDesk.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tecnoDesk.TecnoDesk.Entities.Orcamento;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, Long>{
	
	@Query(
			value = "SELECT * FROM Orcamento c WHERE c.codigo_empresa = :CodEmp and c.id = :codOrcamento",
			nativeQuery = true
			)
	Orcamento encontrarOcamentoPorID(long CodEmp,long codOrcamento);
	
	@Query(
			value = "SELECT * FROM Orcamento c WHERE c.codigo_empresa = :CodEmp and c.codigo_os = :numOs",
			nativeQuery = true
			)
	Orcamento encontrarOcamentoPorNumOS(long CodEmp,long numOs);
	
	@Modifying
	@Transactional
	@Query(
	    value = "DELETE FROM orcamento_item " +
	            "WHERE id = :idItemOrcamento " +
	            "AND codigo_orcamento = :codigoOrcamento " +
	            "AND codigo_empresa = :CodEmp",
	    nativeQuery = true
	)
	void excluirItemOrcamento(
	    @Param("idItemOrcamento") long idItemOrcamento,
	    @Param("codigoOrcamento") long codigoOrcamento,
	    @Param("CodEmp") long CodEmp
	);
	
	
	
	
	
	
	

}
