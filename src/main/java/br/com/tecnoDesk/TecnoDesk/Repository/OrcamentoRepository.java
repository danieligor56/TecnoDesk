package br.com.tecnoDesk.TecnoDesk.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.tecnoDesk.TecnoDesk.Entities.Colaborador;
import br.com.tecnoDesk.TecnoDesk.Entities.Orcamento;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, Long>{
	
	@Query(
			value = "SELECT * FROM Orcamento c WHERE c.codigo_empresa = :CodEmp and c.id = :codOrcamento",
			nativeQuery = true
			)
	Orcamento encontrarOcamentoPorID(long CodEmp,long codOrcamento); 
}
