package br.com.tecnoDesk.TecnoDesk.Entities;

import br.com.tecnoDesk.TecnoDesk.Enuns.PrioridadeOS;
import br.com.tecnoDesk.TecnoDesk.Enuns.StatusOS;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "OS_Mecanica")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OS_Mecanica {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "cod_os", nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "codigo_empresa", nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "veiculo_id", nullable = false)
	private Veiculo veiculo;

	@ManyToOne
	@JoinColumn(name = "tecnico_responsavel")
	private Colaborador tecnicoResponsavel;

	@Column(name = "dt_abertura", nullable = false)
	private String dataAbertura;

	@Column(name = "km_entrada")
	private Integer kmEntrada;

	@Column(name = "km_saida")
	private Integer kmSaida;

	@Column(name = "diagnostico_inicial")
	private String diagnosticoInicial;

	@Enumerated(EnumType.STRING)
	@Column(name = "status_os")
	private StatusOS statusOS;

	@Enumerated(EnumType.STRING)
	@Column(name = "prioridade_os")
	private PrioridadeOS prioridadeOS;

	@Column(name = "observacoes")
	private String observacoes;
}
