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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Controle_Estoque")
public class ControleEstoque {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID",nullable = false,unique = true)
	private long codigoItem;
	
	@ManyToOne
	@JoinColumn(name = "codigo_empresa",nullable = false)
	private Empresa empresa;
	
	private String nomeProduto;
	
	private BigDecimal estoqueAtual = BigDecimal.ZERO;

	private BigDecimal estoqueMinimo = BigDecimal.ZERO;
	 
	private BigDecimal estoqueAnterior = BigDecimal.ZERO;
	
	private String undMedida = "unidade";



}
