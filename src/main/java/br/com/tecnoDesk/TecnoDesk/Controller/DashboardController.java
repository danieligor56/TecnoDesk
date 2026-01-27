package br.com.tecnoDesk.TecnoDesk.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.tecnoDesk.TecnoDesk.DTO.DashboardStatsDTO;
import br.com.tecnoDesk.TecnoDesk.Services.DashboardService;

@RestController
@RequestMapping("/Dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getStats(@RequestHeader("CodEmpresa") String codEmpresa) throws Exception {
        return ResponseEntity.ok(dashboardService.getStats(codEmpresa));
    }
}
