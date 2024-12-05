package br.com.tecnoDesk.TecnoDesk.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.tecnoDesk.TecnoDesk.Entities.OS_Entrada;
import java.util.List;


@Repository

public interface OsRepository extends JpaRepository<OS_Entrada,Long> {
	
	@Query(value = "SELECT * FROM os_abertura os WHERE os.codigo_empresa = :codEmp ORDER BY dt_abert DESC LIMIT 1 ",
			nativeQuery = true)
	OS_Entrada findLastOne(long codEmp);
	
	List<OS_Entrada> findOsByEmpresaId(long CodEmp);
	
	OS_Entrada findByNumOs(long numOs);
	
	OS_Entrada findById(long id);

}
