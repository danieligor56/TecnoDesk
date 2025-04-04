package br.com.tecnoDesk.TecnoDesk.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.tecnoDesk.TecnoDesk.Entities.Cliente;
import br.com.tecnoDesk.TecnoDesk.Entities.Servico;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long>{

	@Query(
			value = "SELECT * FROM Servico c WHERE c.codigo_empresa = :CodEmp",
			nativeQuery = true
			)
	
	List<Servico>listAll(long CodEmp);
}
