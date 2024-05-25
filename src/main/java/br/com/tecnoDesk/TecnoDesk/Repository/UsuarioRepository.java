package br.com.tecnoDesk.TecnoDesk.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.com.tecnoDesk.TecnoDesk.Entities.Usuarios;
import java.util.List;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Long>{
	
	UserDetails findByEmail(String email);
	
	Usuarios findItByEmail(String email);

}

