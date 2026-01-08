package br.com.tecnoDesk.TecnoDesk.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OsRapidaDTO {
	
	private long sequencial;
	
    private String clienteNome;

    private String clienteTelefone;

    private String equipamentoServico;

    private String problemaRelatado;

    private BigDecimal valorEstimado;

    private String prazoCombinado;

    private String observacoes;

    private String tecnicoResponsavel;

}
