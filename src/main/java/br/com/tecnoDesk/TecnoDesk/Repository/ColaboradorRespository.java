package br.com.tecnoDesk.TecnoDesk.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.tecnoDesk.TecnoDesk.Entities.Colaborador;

@Repository

public interface ColaboradorRespository extends JpaRepository<Colaborador, Long> {

	@Query("SELECT c FROM Colaborador c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Colaborador>buscarPornome(String nome);
	
	List<Colaborador>findAll();
	
	Colaborador findItById(long id);

	
}
