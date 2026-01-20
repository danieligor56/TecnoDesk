package br.com.tecnoDesk.TecnoDesk.Entities;

import java.math.BigDecimal;
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
@Table(name = "Produtos", uniqueConstraints = {
		@UniqueConstraint(name = "uk_Produto", columnNames = {
				"codigo_empresa",
				"nome",
				"descricao"
		})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produtos {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", nullable = false, unique = true)
	private Long id;

	@Column(name = "codigo_sequencial")
	private long sequencial;

	@ManyToOne
	@JoinColumn(name = "codigo_empresa", nullable = false)
	private Empresa empresa;

	@Column(name = "nome", nullable = false, length = 150)
	private String nome;

	@Column(name = "descricao", length = 500)
	private String descricao;

	@Column(length = 500, nullable = true)
	private String marcaProduto;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal preco;

	@Column(nullable = true, precision = 10, scale = 2)
	private BigDecimal precoCusto;

	@Column(nullable = false)
	private Integer quantidadeEstoque;

	@Column(nullable = true)
	private long codigo_barras;

	@ManyToOne
	@JoinColumn(name = "codigo_categoria")
	private CategoriaProduto categoria;

	@ManyToOne
	@JoinColumn(name = "codigo_unidade_medida")
	private UnidadeMedida unidadeMedida;

	@Column
	private boolean produtoAtivo;

}
