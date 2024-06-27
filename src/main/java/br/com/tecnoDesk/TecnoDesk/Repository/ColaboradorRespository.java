package br.com.tecnoDesk.TecnoDesk.Repository;

import java.util.List;

import org.hibernate.query.NativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;

import br.com.tecnoDesk.TecnoDesk.Entities.Colaborador;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import lombok.extern.java.Log;

@Repository

public interface ColaboradorRespository extends JpaRepository<Colaborador, Long> {

	@Query("SELECT c FROM Colaborador c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Colaborador>buscarPornome(String nome);
	
	/* List<Colaborador>findAll(); */
	
	Colaborador findItById(long id);
	
	@Query(
			value = "SELECT * FROM Colaborador c Where c.codigo_empresa = :id",
			nativeQuery = true
			)
	List<Colaborador>findByCodEmpresa(long id);
	
	@Query(
			value = "SELECT * FROM Colaborador c WHERE c.codigo_empresa = :id",
			nativeQuery = true
			)
	
	List<Colaborador>listAll(long id);
	
}
