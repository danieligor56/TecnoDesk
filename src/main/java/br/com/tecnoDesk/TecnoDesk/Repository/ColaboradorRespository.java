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
	
	@Query(
			value = "SELECT * FROM Colaborador c Where c.id = :id AND c.codigo_empresa = :CodEmp",
			nativeQuery = true
			)
	Colaborador findItById(long id,long CodEmp);
	
	
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
			value = "SELECT * FROM Colaborador c WHERE c.codigo_empresa = :CodEmp and 0 = any(c.funcao)",
			nativeQuery = true
			)
	List<Colaborador>listarTecnicos(long CodEmp);
	
	@Query(
			value = "SELECT COUNT(*) FROM os_abertura oa  WHERE oa.colaborador_id = :idColab or oa.tecnico_responsavel = :idColab AND oa.codigo_empresa = :CodEmp",
			nativeQuery = true)
	int colaboradorEmUso(long idColab, long CodEmp);
	
	
}
