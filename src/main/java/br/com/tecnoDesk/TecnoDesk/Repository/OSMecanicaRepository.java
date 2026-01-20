package br.com.tecnoDesk.TecnoDesk.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.tecnoDesk.TecnoDesk.Entities.OS_Mecanica;

@Repository
public interface OSMecanicaRepository extends JpaRepository<OS_Mecanica, Long> {

}
