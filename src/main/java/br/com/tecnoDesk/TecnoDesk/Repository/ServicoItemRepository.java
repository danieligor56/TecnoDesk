package br.com.tecnoDesk.TecnoDesk.Repository;

import br.com.tecnoDesk.TecnoDesk.Entities.ServicoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoItemRepository extends JpaRepository<ServicoItem, Long> {
	
	@Query(value = "SELECT * FROM servico_item si WHERE si.codigo_empresa = :cod_empresa",
			nativeQuery = true)
	java.util.List<ServicoItem>listSevicoItem(long cod_empresa);
}
