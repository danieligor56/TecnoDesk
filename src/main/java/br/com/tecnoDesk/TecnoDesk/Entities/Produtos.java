package br.com.tecnoDesk.TecnoDesk.Entities;

import java.math.BigDecimal;

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
@Table(name = "Produtos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Produtos {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id; 
	
	@ManyToOne
	@JoinColumn(name = "codigo_empresa",nullable = false)
	private Empresa empresa;
	
	@Column(nullable = false, length = 150) 
	private String nome; 
	
	@Column(length = 500) 
	private String descricao;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal preco;
	
	@Column(nullable = false)
	private Integer quantidadeEstoque;
	
	@Column(length = 50)
	private String categoria;
	
	@Column(length = 50) 
	private String unidadeMedida; // Ex: "kg", "un", "litro" @Column(nullable = false) private Boolean ativo = true;
	
	

}
