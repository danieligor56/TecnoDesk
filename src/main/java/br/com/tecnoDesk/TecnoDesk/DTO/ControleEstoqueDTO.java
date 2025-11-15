package br.com.tecnoDesk.TecnoDesk.DTO;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ControleEstoqueDTO {
	
	private Long produtoId;
	private BigDecimal estoqueMinimo;
	private BigDecimal estoqueMaximo;
	private String observacao;

}

