package br.com.tecnoDesk.TecnoDesk.DTO;

import java.math.BigDecimal;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProdutosDTO {
	
	private Empresa empresa;
	private String nome; 
	private String descricao;
	private String marcaProduto;
	private BigDecimal preco;
	private BigDecimal precoCusto;
	private Integer quantidadeEstoque;
	private long codigo_barras; 
	private String categoria;
	private String unidadeMedida;
	private boolean produtoAtivo;

}
