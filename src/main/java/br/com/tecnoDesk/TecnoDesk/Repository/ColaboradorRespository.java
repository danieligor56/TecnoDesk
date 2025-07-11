package br.com.tecnoDesk.TecnoDesk.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.tecnoDesk.TecnoDesk.Entities.Colaborador;
import br.com.tecnoDesk.TecnoDesk.Enuns.Ocupacao;


@Repository

public interface ColaboradorRespository extends JpaRepository<Colaborador, Long> {

	@Query("SELECT c FROM Colaborador c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Colaborador>buscarPornome(String nome);
	
	/* List<Colaborador>findAll(); */
	
	Colaborador findItById(long id);
	
	@Query(
			value = "SELECT * FROM Colaborador c Where c.codigo_empresa = :CodEmp",
			nativeQuery = true
			)
	List<Colaborador>findByCodEmpresa(long CodEmp);
	
	@Query(
			value = "SELECT * FROM Colaborador c WHERE c.codigo_empresa = :CodEmp",
			nativeQuery = true
			)
	
	List<Colaborador>listAll(long CodEmp);
	
	@Query(
			value = "SELECT * FROM Colaborador c WHERE c.codigo_empresa = :CodEmp and c.funcao = 0",
			nativeQuery = true
			)
	List<Colaborador>listarTecnicos(long CodEmp);
	
	@Query(
			value = "SELECT COUNT(*) FROM os_abertura oa  WHERE oa.colaborador_id = :idColab or oa.tecnico_responsavel = :idColab AND oa.codigo_empresa = :CodEmp",
			nativeQuery = true)
	int colaboradorEmUso(long idColab, long CodEmp);
	
	
}
