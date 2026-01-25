package br.com.tecnoDesk.TecnoDesk.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.tecnoDesk.TecnoDesk.DTO.DashboardStatsDTO;
import br.com.tecnoDesk.TecnoDesk.Enuns.StatusOR;
import br.com.tecnoDesk.TecnoDesk.Enuns.StatusOS;
import br.com.tecnoDesk.TecnoDesk.Repository.OrcamentoRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.OsRepository;

@Service
public class DashboardService {

    @Autowired
    private OsRepository osRepository;

    @Autowired
    private OrcamentoRepository orcamentoRepository;

    @Autowired
    private DecriptService decriptService;

    public DashboardStatsDTO getStats(String codEmpresa) throws Exception {
        Long empresaId = decriptService.decriptCodEmp(codEmpresa);

        DashboardStatsDTO stats = new DashboardStatsDTO();

        // Count OS Abertas (NOVO status)
		/*
		 * stats.setOsAbertas(osRepository.countByEmpresaIdAndStatusOS(empresaId,
		 * StatusOS.NOVO));
		 */
        
        stats.setOsAbertas(osRepository.countOsAberta(empresaId));

        // Count Em Andamento
		/*
		 * stats.setEmAndamento(osRepository.countByEmpresaIdAndStatusOS(empresaId,
		 * StatusOS.EM_ANDAMENTO));
		 */
        stats.setEmAndamento(osRepository.countOsAndamento(empresaId));

        // Count Finalizadas Hoje
        LocalDate hoje = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataHoje = hoje.format(formatter);
        stats.setFinalizadasHoje(osRepository.countFinalizadasHoje(empresaId, dataHoje + "%"));

        // Faturamento do Mês - placeholder (você pode implementar a lógica real)
        stats.setFaturamentoMes(0.0);

        // Count Orçamentos Pendentes
        stats.setOrcamentosPendentes(orcamentoRepository.countByEmpresaIdAndStatusOR(empresaId, StatusOR.NOVO));

        return stats;
    }
}
