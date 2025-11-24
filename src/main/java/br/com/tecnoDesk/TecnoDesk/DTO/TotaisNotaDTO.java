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
public class TotaisNotaDTO {

	public BigDecimal valorTotalNota;
	public BigDecimal valorTotalProdutos;
	public BigDecimal valorTotalDescontoProdutos;
	public BigDecimal valorTotalServico;
	public BigDecimal valorTotalDescontoServico;
	
	
}
