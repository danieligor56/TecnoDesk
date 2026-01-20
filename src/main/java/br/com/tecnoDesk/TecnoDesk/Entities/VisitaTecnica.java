package br.com.tecnoDesk.TecnoDesk.Entities;

import br.com.tecnoDesk.TecnoDesk.Enuns.StatusVisita;
import br.com.tecnoDesk.TecnoDesk.Enuns.TipoVisita;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "VISITA_TECNICA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisitaTecnica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "codigo_sequencial")
    private Long sequencial;

    @ManyToOne
    @JoinColumn(name = "codigo_empresa", nullable = false)
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador tecnico;

    @Column(name = "data_visita", nullable = false)
    private LocalDate dataVisita;

    @Column(name = "hora_visita", nullable = false)
    private LocalTime horaVisita;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_visita", nullable = false, length = 30)
    private TipoVisita tipoVisita;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_visita", nullable = false, length = 30)
    private StatusVisita statusVisita;

    @Column(name = "observacoes", length = 1000)
    private String observacoes;

    @Column(name = "relatorio_tecnico", length = 2000)
    private String relatorioTecnico;

    @Column(name = "endereco_visita", length = 255)
    private String enderecoVisita;

    @Column(name = "proxima_visita_sugerida")
    private LocalDate proximaVisitaSugerida;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
        if (statusVisita == null) {
            statusVisita = StatusVisita.AGENDADA;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }
}
