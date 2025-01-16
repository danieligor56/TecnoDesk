package br.com.tecnoDesk.TecnoDesk.Repository;

import org.hibernate.query.NativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;


@Repository
public interface EmpresaRepository extends JpaRepository<Empresa,Long> {
	
	public Empresa findEmpresaById(long id);

	
	
}
