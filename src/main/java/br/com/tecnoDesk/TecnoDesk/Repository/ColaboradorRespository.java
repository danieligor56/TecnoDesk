package br.com.tecnoDesk.TecnoDesk.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.tecnoDesk.TecnoDesk.Entities.Colaborador;


@Repository

public interface ColaboradorRespository extends JpaRepository<Colaborador, Long> {

}
