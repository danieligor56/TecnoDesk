package br.com.tecnoDesk.TecnoDesk.Entities;

import java.util.List;
import br.com.tecnoDesk.TecnoDesk.Enuns.Ocupacao;
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
	
	@ManyToOne
	@JoinColumn(name = "codigo_empresa",nullable = false)
	private Empresa empresa;

	@Column(name = "NOME",nullable = false,unique = true)
	private String nome;
	
	@Column(name = "DOCUMENTO",nullable = false,unique = true)
	private long documento;
	
	@Column(name = "FUNCAO",nullable = true,unique = false)
	private Ocupacao ocupacao;
	
	@Column(name = "EMAIL",nullable = true,unique = true)
	private String email;
	
	/*
	 * @Column(name = "tel",nullable = true,unique = true) private String tel1;
	 */
	
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
	@Column(name="CEP",nullable = true)
	private String cep;
	@Column(name="BAIRRO",nullable = true)
	private String bairro;
	
	// *** //
	
	@Column(name = "FlgAtvReg",nullable = false)
	private boolean atvReg;
	
	
	
	
	
	
}
