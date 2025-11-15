package br.com.tecnoDesk.TecnoDesk.DTO;

import java.math.BigDecimal;
import br.com.tecnoDesk.TecnoDesk.Enuns.TipoMovimentacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoEstoqueDTO {
	
	private Long produtoId;
	private TipoMovimentacao tipoMovimentacao;
	private BigDecimal quantidade;
	private String observacao;
	private String usuarioResponsavel;
	private String origemMovimentacao;

}

