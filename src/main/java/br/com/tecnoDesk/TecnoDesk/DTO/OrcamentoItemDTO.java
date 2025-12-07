package br.com.tecnoDesk.TecnoDesk.DTO;

import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Entities.Orcamento;
import br.com.tecnoDesk.TecnoDesk.Enuns.ProdutoServicoEnum;
import br.com.tecnoDesk.TecnoDesk.Services.ProdutoService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrcamentoItemDTO {
	
	private Empresa empresa;

	private Orcamento orcamento;

    private long codigoItem;
    
    private String nomeServicoAvulso;

    private String descricaoServicoAvulso;

    private Double valorUnidadeAvulso;

    private Double valorHoraAvulso;
    
    private boolean isAvulso;
    
    private ProdutoServicoEnum produtoOuServico;
	
}
