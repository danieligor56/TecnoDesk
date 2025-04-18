package br.com.tecnoDesk.TecnoDesk.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.com.tecnoDesk.TecnoDesk.Entities.Colaborador;
import br.com.tecnoDesk.TecnoDesk.Entities.Orcamento;
import br.com.tecnoDesk.TecnoDesk.Entities.OrcamentoItem;

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
	
	@Query("SELECT i FROM OrcamentoItem i WHERE i.empresa.id = :CodEmp AND i.orcamento.id = :codigoOrcamento")
	List<OrcamentoItem> listaItens(@Param("CodEmp") long CodEmp, @Param("codigoOrcamento") long codigoOrcamento);

}
