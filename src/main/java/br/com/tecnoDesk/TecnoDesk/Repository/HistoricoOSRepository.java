package br.com.tecnoDesk.TecnoDesk.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.tecnoDesk.TecnoDesk.Entities.HistoricoOS;

@Repository
public interface HistoricoOSRepository extends JpaRepository<HistoricoOS, Long> {
    List<HistoricoOS> findByOsSequencialAndEmpresaIdOrderByDataAlteracaoDesc(Long osSequencial, Long empresaId);
}
