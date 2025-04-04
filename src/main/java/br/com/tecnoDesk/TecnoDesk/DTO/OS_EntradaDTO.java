package br.com.tecnoDesk.TecnoDesk.DTO;

import java.util.Date;
import br.com.tecnoDesk.TecnoDesk.Entities.Cliente;
import br.com.tecnoDesk.TecnoDesk.Entities.Colaborador;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Enuns.Aparelhos;
import br.com.tecnoDesk.TecnoDesk.Enuns.PrioridadeOS;
import br.com.tecnoDesk.TecnoDesk.Enuns.StatusOS;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class OS_EntradaDTO {
		
	private Cliente cliente;
	
	private Colaborador colaborador;
	
	private long numOs;

	private Aparelhos aparelhos;

	private String descricaoModelo;
	
	private String marcaAparelho;
	
	private String snAparelho;

	private String checkList;

	private String reclamacaoCliente;

	private String initTest;

	private StatusOS statusOS;
	
	private PrioridadeOS prioridadeOS;
}







