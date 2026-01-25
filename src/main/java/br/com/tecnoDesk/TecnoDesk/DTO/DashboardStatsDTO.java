package br.com.tecnoDesk.TecnoDesk.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {
    private long osAbertas;
    private long emAndamento;
    private long finalizadasHoje;
    private double faturamentoMes;
    private long orcamentosPendentes;
}
