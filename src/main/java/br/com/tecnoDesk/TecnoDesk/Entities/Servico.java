package br.com.tecnoDesk.TecnoDesk.Entities;

import java.io.Serializable;

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
@Table(name = "SERVICO")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Servico implements Serializable {
		
		private static final long serialVersionUID = 1L;

		@Id
		@Column(name = "ID",nullable = false,unique = true)
		private long id;
		
		@ManyToOne
		@JoinColumn(name = "codigo_empresa",nullable = false)
		private Empresa empresa;
		
		@Column(name = "nomeServico",nullable = false)
		private String nomeServico;
		
		@Column(name = "descricaoServico",nullable = true)
		private String descricaoServico;
		
		@Column(name = "valorServicoUnidade",nullable = true)
		private Double valorServicoUnidade;
		
		@Column(name = "valorServicoHora",nullable = true)
		private Double valorServicoHora;

}
