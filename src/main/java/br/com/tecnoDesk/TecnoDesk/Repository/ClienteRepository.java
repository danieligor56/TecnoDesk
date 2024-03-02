package br.com.tecnoDesk.TecnoDesk.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tecnoDesk.TecnoDesk.Entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
