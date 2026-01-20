package br.com.tecnoDesk.TecnoDesk.Repository;

import br.com.tecnoDesk.TecnoDesk.Entities.VisitaTecnica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitaTecnicaRepository extends JpaRepository<VisitaTecnica, Long> {
    List<VisitaTecnica> findByEmpresaId(Long empresaId);

    List<VisitaTecnica> findByEmpresaIdOrderByDataVisitaDescHoraVisitaDesc(Long empresaId);
}
