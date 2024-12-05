package br.com.tecnoDesk.TecnoDesk.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "Observacao_OS")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObsEmpresaOS {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID",nullable = false,unique = true)
	private long id;
	
	@OneToOne
	@JoinColumn(name = "codigo_empresa",nullable = false)
	private Empresa empresa;
	
	@Column(name = "descricao_observacao",nullable = false)
	private String descricaoObservacao;

}
