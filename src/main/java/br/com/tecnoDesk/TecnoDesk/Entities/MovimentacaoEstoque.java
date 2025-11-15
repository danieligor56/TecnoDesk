package br.com.tecnoDesk.TecnoDesk.Entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import br.com.tecnoDesk.TecnoDesk.Enuns.TipoMovimentacao;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Movimentacao_Estoque")
public class MovimentacaoEstoque {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false, unique = true)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "codigo_empresa", nullable = false)
	private Empresa empresa;
	
	@ManyToOne
	@JoinColumn(name = "produto_id", nullable = false)
	private Produtos produto;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private TipoMovimentacao tipoMovimentacao;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal quantidade;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal estoqueAnterior;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal estoqueAtual;
	
	@Column(nullable = false)
	private LocalDateTime dataMovimentacao;
	
	@Column(length = 500)
	private String observacao;
	
	@Column(length = 100)
	private String usuarioResponsavel;
	
	@Column(length = 50)
	private String origemMovimentacao; // Ex: "Venda", "Compra", "Ajuste Manual", etc

}

