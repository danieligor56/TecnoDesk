package br.com.tecnoDesk.TecnoDesk.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.tecnoDesk.TecnoDesk.Entities.OsRapida;
import java.util.List;

@Repository
public interface OsRapidaRepository extends JpaRepository<OsRapida, Long> {

    @Query(value = "SELECT * FROM os_rapida WHERE id = (SELECT MAX(id) FROM os_rapida)",
           nativeQuery = true)
    OsRapida findLastOne();

    List<OsRapida> findAll();

    @Query(value = "SELECT * FROM os_rapida WHERE tecnico_responsavel = :tecnico AND codigo_empresa = :codEmp AND status = 'NOVO' ",
           nativeQuery = true)
    List<OsRapida> findByTecnicoResponsavel(String tecnico, long codEmp);

    List<OsRapida> findByCodigoEmpresa(long codEmp);

}
