package br.com.tecnoDesk.TecnoDesk.Entities;

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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Veiculo",
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
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @Column(name="codigo_sequencial")
    private long sequencial;
    
    @ManyToOne
	@JoinColumn(name = "codigo_empresa",nullable = false)
	private Empresa empresa;

    @Column(length = 10, nullable = false, unique = true)
    private String placa;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @Column(name = "ano_fabricacao")
    private Integer anoFabricacao;

    @Column(name = "ano_modelo")
    private Integer anoModelo;

    private String cor;

    private String combustivel;

    private String tipoVeiculo;

    @Column(unique = true)
    private String chassi;

    private String municipio;

    private String uf;

    @ManyToOne
    private Cliente cliente;

    @Column(length = 500)
    private String observacoes;
}
