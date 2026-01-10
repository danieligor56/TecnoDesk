package br.com.tecnoDesk.TecnoDesk.Entities;

import org.springframework.boot.availability.ApplicationAvailability;

import br.com.tecnoDesk.TecnoDesk.Enuns.TipoVeiculo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "Veiculos", 
uniqueConstraints = { 
		@UniqueConstraint(
				name = "uk_Veiculos", 
				columnNames = {
						"codigo_empresa",
						"sequencial", 
						"placa",
						"chassi"
						}
					)		
				})
public class Veiculos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public long id;
	
	@Column
	public long sequencial;
	
	@ManyToOne
	@JoinColumn(name = "codigo_empresa",nullable = false)
	private Empresa empresa;
	
	@Column(nullable = false)
	public String placa;

	@Column
	public String Marca;

	@Column
	public String modelo;

	@Column
	public String ano;

	@Column
	public String cor;

	@Column
	public TipoVeiculo tipoVeiculo; /* (carro, moto, caminhão) */

	@Column
	public String combustivel;

	@Column
	public int quilometragemAtual;

	@Column(nullable = true)
	public String chassi; 


}
