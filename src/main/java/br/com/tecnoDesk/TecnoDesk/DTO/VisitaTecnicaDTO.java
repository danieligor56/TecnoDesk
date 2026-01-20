package br.com.tecnoDesk.TecnoDesk.DTO;

import br.com.tecnoDesk.TecnoDesk.Enuns.StatusVisita;
import br.com.tecnoDesk.TecnoDesk.Enuns.TipoVisita;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisitaTecnicaDTO {
    private Long id;
    private Long sequencial;
    private Long clienteId;
    private String clienteNome; // For display
    private Long tecnicoId;
    private String tecnicoNome; // For display
    private LocalDate dataVisita;
    private LocalTime horaVisita;
    private TipoVisita tipoVisita;
    private StatusVisita statusVisita;
    private String observacoes;
    private String relatorioTecnico;
    private String enderecoVisita;
    private LocalDate proximaVisitaSugerida;
    private Long empresaId;
}
