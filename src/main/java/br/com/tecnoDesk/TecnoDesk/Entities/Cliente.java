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
@Table(name = "CLIENTE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Cliente implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID",nullable = false,unique = true)
	private long id;
	
	@Column(name = "codigo_sequencial")
	private long sequencial;
	
	@ManyToOne
	@JoinColumn(name = "codigo_empresa",nullable = false)
	private Empresa empresa;
	
	@Column(name = "NOME",nullable = false,unique = true)
	private String nome;
	
	@Column(name = "EMAIL",nullable = true,unique = true)
	private String email;
	
	@Column(name = "DOCUMENTO",nullable = false,unique = true)
	private String documento;

	@Column(name = "cel1",nullable = false,unique = true)
	private String cel1;
	
	@Column(name = "cel2",nullable = true,unique = false)
	private String cel2;
	
	//BLOCO ENDEREÇO// 
		@Column(name = "CEP")
		private String cep; 
		@Column(name = "UF",nullable = false)
		private String estado;
		@Column(name = "MUNIC",nullable = false)
		private String cidade;
		@Column(name = "logradouro",nullable = false)
		private String logradouro;
		@Column(name = "NUM",nullable = false)
		private int numero;
		@Column(name = "OBS",nullable = true)
		private String obs;
		// *** // 
		
		@Column(name = "FlgAtvReg",nullable = false)
		private boolean atvReg;
	
	
	
	

}
