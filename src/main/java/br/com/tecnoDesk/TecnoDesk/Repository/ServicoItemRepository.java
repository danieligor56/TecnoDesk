package br.com.tecnoDesk.TecnoDesk.Repository;

import br.com.tecnoDesk.TecnoDesk.Entities.ServicoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoItemRepository extends JpaRepository<ServicoItem, Long> {
}
