package br.com.tecnoDesk.TecnoDesk.Entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	
	@Column(name = "NOME",nullable = false,unique = true)
	private String nome;
	
	@Column(name = "EMAIL",nullable = true,unique = true)
	private String email;
	
	@Column(name = "DOCUMENTO",nullable = false,unique = true)
	private long documento;
	
	@Column(name = "tel",nullable = true,unique = true)
	private long tel;
	@Column(name = "cel1",nullable = false,unique = true)
	private long cel1;
	@Column(name = "cel2",nullable = true,unique = true)
	private long cel2;
	
	
	//BLOCO ENDEREÇO// 

		@Column(name = "UF",nullable = true)
		private String estado;
		@Column(name = "MUNIC",nullable = true)
		private String cidade;
		@Column(name = "logradouro",nullable = true)
		private String logradouro;
		@Column(name = "NUM",nullable = true)
		private int numero;
		@Column(name = "OBS",nullable = true)
		private String obs;
		// *** // 
		
		@Column(name = "FlgAtvReg",nullable = false)
		private boolean atvReg;
	
	
	
	

}
