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
	private String nome_servico;

	@Column(name = "desc_servico", length = 255)
	private String desc_servico;

	@Column(name = "valor_servico", nullable = false)
	private Double valor_servico;

	@Column(name = "custo_servico")
	private Double custo_servico;

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

	public String getNome_servico() {
		return nome_servico;
	}

	public void setNome_servico(String nome_servico) {
		this.nome_servico = nome_servico;
	}

	public String getDesc_servico() {
		return desc_servico;
	}

	public void setDesc_servico(String desc_servico) {
		this.desc_servico = desc_servico;
	}

	public Double getValor_servico() {
		return valor_servico;
	}

	public void setValor_servico(Double valor_servico) {
		this.valor_servico = valor_servico;
	}

	public Double getCusto_servico() {
		return custo_servico;
	}

	public void setCusto_servico(Double custo_servico) {
		this.custo_servico = custo_servico;
	}
}

