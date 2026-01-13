package br.com.tecnoDesk.TecnoDesk.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.tecnoDesk.TecnoDesk.Entities.Cliente;
import br.com.tecnoDesk.TecnoDesk.Entities.Colaborador;

import java.util.List;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	public Cliente findItById(long id);
	

	@Query("SELECT c FROM Cliente c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Cliente>buscarPornome(String nome);
	
	@Query(
			value = "SELECT * FROM Cliente c WHERE c.codigo_empresa = :CodEmp",
			nativeQuery = true
			)
	List<Cliente>listAll(long CodEmp);
	
	@Query(
			value = "SELECT * FROM Cliente c WHERE c.codigo_empresa = :codEmpresa AND c.documento = :documento",
			nativeQuery = true
			)
	Cliente findByDocumento(String documento,long codEmpresa);
	
	@Query(
			value = "SELECT COUNT(*) FROM Cliente c WHERE c.codigo_empresa = :codEmpresa AND c.documento = :documento",
			nativeQuery = true
			)
	int verificarPorDocumento(String documento,long codEmpresa);
	
	boolean existsById(long id);
}
