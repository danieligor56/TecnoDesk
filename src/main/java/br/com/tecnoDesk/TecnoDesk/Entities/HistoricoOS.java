package br.com.tecnoDesk.TecnoDesk.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "OS_HISTORICO")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoOS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "os_id", nullable = false)
    private OS_Entrada os;

    @Column(name = "descricao", nullable = false, length = 1000)
    private String descricao;

    @Column(name = "dt_alteracao", nullable = false)
    private String dataAlteracao;

    @Column(name = "responsavel", nullable = false)
    private String responsavel;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;
}
