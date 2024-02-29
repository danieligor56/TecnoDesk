package br.com.tecnoDesk.TecnoDesk.Entities;

import br.com.tecnoDesk.TecnoDesk.Enuns.Aparelhos;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "OS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class OS {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID",nullable = false,unique = true)
	private long id;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "NumOS",nullable = false,unique = true)
	private long numOS;
	
	
	@JoinColumn(name = "tblCli",referencedColumnName = "id")
	private Cliente cliente;
	
	@Column(name = "tblCli",nullable = false)
	private Aparelhos aparelhos;
	
	@Column(name = "descModel",nullable = false)
	private String descricaoModelo;
	
	@Column(name = "chkList",nullable = false)
	private String checkList;
	
	@Column(name = "recCliente",nullable = false)
	private String corpoChamado;
	
	@Column(name = "ldoChamado",nullable = false)
	private String laudoChamado;
	
	
	
	
	
	
	
	
	
	
}
