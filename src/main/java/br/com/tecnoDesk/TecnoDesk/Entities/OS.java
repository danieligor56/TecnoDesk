package br.com.tecnoDesk.TecnoDesk.Entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import br.com.tecnoDesk.TecnoDesk.Enuns.Aparelhos;
import br.com.tecnoDesk.TecnoDesk.Enuns.StatusOS;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "OS")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class OS {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "Cod_OS", nullable = false, unique = true)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "codigo_empresa",nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "colaborador_id")
	private Colaborador colaborado;
	
	@Column(name = "dtAbert",nullable = false)
	private String dataAbertura;

	@Column(name = "aparelho", nullable = false)
	private Aparelhos aparelhos;

	@Column(name = "descModel", nullable = false)
	private String descricaoModelo;

	@Column(name = "chkList", nullable = false)
	private String checkList;

	@Column(name = "recCliente", nullable = false)
	private String corpoChamado;

	@Column(name = "ldoChamado", nullable = false)
	private String laudoChamado;

	@Column(name = "stsOs", nullable = true)
	private StatusOS statusOS;

}
