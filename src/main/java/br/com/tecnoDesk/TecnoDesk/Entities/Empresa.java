package br.com.tecnoDesk.TecnoDesk.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="empresa")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Empresa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="ID",unique = true,nullable = false)
	private long id;
	
	@Valid
	@Column(name="NomeEmpresa",unique = true,nullable = false)
	private String nomEmpresa;
	
	@Valid
	@Column(name="DocEmp",unique = true,nullable = false)
	private String docEmpresa;
	
	@Column(name="mail",unique = true)
	private String mail;
	
	@Column(name="cell",unique = true)
	private String cel;
	
	@Column(name="tel",unique = true)
	private String tel;
	
	@Column(name="site",unique = true)
	private String site;
	
	
	//BLOCO ENDEREÇO//
	@Column(name="cep")
	private String cep;
	@Column(name="logra")
	private String logra;
	@Column(name="num")
	private int num;
	@Column(name="complemento")
	private String comp;
	@Column(name="bairro")
	private String bairro;
	@Column(name="municipio")
	private String municipio;
	@Column(name="uf")
	private String uf;
	// *** ///
	
	
	
	
	
	

}
