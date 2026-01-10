package br.com.tecnoDesk.TecnoDesk.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.tecnoDesk.TecnoDesk.Entities.Veiculo;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

}
