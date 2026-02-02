package br.com.tecnoDesk.TecnoDesk.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.tecnoDesk.TecnoDesk.Entities.OS_Entrada;
import br.com.tecnoDesk.TecnoDesk.Enuns.StatusOS;
import java.util.List;

@Repository

public interface OsRepository extends JpaRepository<OS_Entrada, Long> {

	@Query(value = "SELECT * FROM os_abertura os WHERE os.codigo_empresa = :codEmp ORDER BY dt_abert DESC LIMIT 1 ", nativeQuery = true)
	OS_Entrada findLastOne(long codEmp);

	@Query(value = "SELECT * FROM os_abertura oa WHERE oa.codigo_empresa = :codEmp AND  sts_os not in (8,9)", nativeQuery = true)
	List<OS_Entrada> findOsByEmpresaId(long codEmp);

	@Query(value = "SELECT * FROM os_abertura oa WHERE oa.codigo_empresa = :codEmp ORDER BY dt_abertura", nativeQuery = true)
	List<OS_Entrada> listarOsCanceladasEncerrada(long codEmp);

	@Query(value = "SELECT * FROM os_abertura os WHERE os.codigo_sequencial = :numOs AND os.codigo_empresa = :codEmp", nativeQuery = true)
	OS_Entrada findByNumOs(long numOs, long codEmp);

	OS_Entrada findById(long id);

	// Dashboard queries
	long countByEmpresaIdAndStatusOS(Long empresaId, StatusOS statusOS);

	@Query(value = "SELECT COUNT(*) FROM os_abertura os where os.sts_os = 0 and os.codigo_empresa = :empresaId", nativeQuery = true)
	long countOsAberta(Long empresaId);

	@Query(value = "SELECT COUNT(*) FROM os_abertura os WHERE os.codigo_empresa = :empresaId AND os.dt_encerramento LIKE :dataHoje AND os.sts_os = 9", nativeQuery = true)
	long countFinalizadasHoje(Long empresaId, String dataHoje);

	@Query(value = "select count(*) from os_abertura oa where oa.sts_os not in (0,7,8,9) and oa.codigo_empresa = :empresaId ", nativeQuery = true)
	long countOsAndamento(long empresaId);

	@Query(value = "select total_orcamentos_mes_atual(:empresaId)", nativeQuery = true)
	long totalValorOsMensal(long empresaId);

}
