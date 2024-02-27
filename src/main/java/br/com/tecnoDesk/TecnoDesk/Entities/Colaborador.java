package br.com.tecnoDesk.TecnoDesk.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Colaborador {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID",nullable = false,unique = true)
	private long id;

	@Column(name = "NOME",nullable = false,unique = true)
	private String nome;
	
	@Column(name = "DOCUMENTO",nullable = false,unique = true)
	private long documento;
	
	
}
