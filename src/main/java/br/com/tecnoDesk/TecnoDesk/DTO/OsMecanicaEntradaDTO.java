package br.com.tecnoDesk.TecnoDesk.DTO;

import br.com.tecnoDesk.TecnoDesk.Entities.Cliente;
import br.com.tecnoDesk.TecnoDesk.Entities.Colaborador;
import br.com.tecnoDesk.TecnoDesk.Entities.Veiculo;
import br.com.tecnoDesk.TecnoDesk.Enuns.PrioridadeOS;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OsMecanicaEntradaDTO {

	private Cliente cliente;

	private Veiculo veiculo;
	
	private Colaborador colaborador; // RESPONSÁVEL PELA ABERTURA DA OS.
	
	private Colaborador tecnicoResponsavel;

	private String dataAbertura;

	private Integer kmEntrada;
	
	private String reclamacaoCliente;
	
	private String initTest;

	private PrioridadeOS prioridadeOS;
	
	private String checkList;

}

