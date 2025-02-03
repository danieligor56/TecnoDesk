package br.com.tecnoDesk.TecnoDesk.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@Table(name = "servico_item")
@Table(name = "servico_item", uniqueConstraints = @UniqueConstraint(columnNames = {"nome_servico", "codigo_empresa"}))

public class ServicoItem {

	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "codigo_empresa",nullable = false)
	private Empresa empresa;

	@Column(name = "nome_servico", nullable = false)
	private String nomeServico;

	@Column(name = "desc_servico", length = 255)
	private String descServico;

	@Column(name = "valor_servico", nullable = false)
	private Double valorServico;

	@Column(name = "custo_servico")
	private Double custoServico;

}

