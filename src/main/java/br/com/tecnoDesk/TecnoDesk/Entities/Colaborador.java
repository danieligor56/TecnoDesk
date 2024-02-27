package br.com.tecnoDesk.TecnoDesk.Entities;

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
@Table(name = "COLABORADOR")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class Colaborador {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID",nullable = false,unique = true)
	private long id;

	@Column(name = "NOME",nullable = false,unique = true)
	private String nome;
	
	@Column(name = "DOCUMENTO",nullable = false,unique = true)
	private long documento;
	
	@Column(name = "FUNCAO",nullable = true,unique = false)
	private Ocupacao ocupacao;
	
	@Column(name = "EMAIL",nullable = true,unique = true)
	private String email;
	@Column(name = "tel",nullable = true,unique = true)
	private String tel1;
	@Column(name = "cel",nullable = true,unique = true)
	private String cel1;
	
	// BLOCO ENDEREÇO// 
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
