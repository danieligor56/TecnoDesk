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
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
	    name = "CLIENTE",
	    uniqueConstraints = {
	        // Documento único POR EMPRESA quando informado
	        @UniqueConstraint(
	            name = "uk_cliente_documento_empresa",
	            columnNames = { "codigo_empresa", "DOCUMENTO" }
	        )
	    }
	)

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	
	public class Cliente implements Serializable {

	    private static final long serialVersionUID = 1L;

	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name = "ID", nullable = false)
	    private Long id;

	    @Column(name = "codigo_sequencial")
	    private Long sequencial;

	    @ManyToOne
	    @JoinColumn(name = "codigo_empresa", nullable = false)
	    private Empresa empresa;

	    @Column(name = "NOME", nullable = false)
	    private String nome;

	    @Column(name = "EMAIL")
	    private String email;


	    @Column(name = "DOCUMENTO")
	    private String documento;

	    @Column(name = "cel1", nullable = false)
	    private String cel1;

	    @Column(name = "cel2")
	    private String cel2;

	    @Column(name = "CEP")
	    private String cep;

	    @Column(name = "UF")
	    private String estado;

	    @Column(name = "MUNIC")
	    private String cidade;

	    @Column(name = "logradouro")
	    private String logradouro;

	    @Column(name = "NUM")
	    private Integer numero;

	    @Column(name = "OBS")
	    private String obs;

	    @Column(name = "FlgAtvReg", nullable = false)
	    private boolean atvReg;
	}

