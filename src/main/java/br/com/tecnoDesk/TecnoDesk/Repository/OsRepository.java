package br.com.tecnoDesk.TecnoDesk.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.tecnoDesk.TecnoDesk.Entities.OS_Entrada;

@Repository

public interface OsRepository extends JpaRepository<OS_Entrada,Long> {
	
	

}
