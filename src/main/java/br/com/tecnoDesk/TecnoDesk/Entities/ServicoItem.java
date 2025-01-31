package br.com.tecnoDesk.TecnoDesk.Entities;

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
@Table(name = "servico_item")
public class ServicoItem {

	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "codigo_empresa",nullable = false)
	private Empresa empresa;

	@Column(name = "nome_servico", nullable = false, unique = true)
	private String nomeServico;

	@Column(name = "desc_servico", length = 255)
	private String descServico;

	@Column(name = "valor_servico", nullable = false)
	private Double valorServico;

	@Column(name = "custo_servico")
	private Double custoServico;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getNomeServico() {
		return nomeServico;
	}

	public void setNomeServico(String nomeServico) {
		this.nomeServico = nomeServico;
	}

	public String getDescServico() {
		return descServico;
	}

	public void setDescServico(String descServico) {
		this.descServico = descServico;
	}

	public Double getValorServico() {
		return valorServico;
	}

	public void setValorServico(Double valorServico) {
		this.valorServico = valorServico;
	}

	public Double getCustoServico() {
		return custoServico;
	}

	public void setCustoServico(Double custoServico) {
		this.custoServico = custoServico;
	}
}

