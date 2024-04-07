package br.com.tecnoDesk.TecnoDesk.DTO;

import br.com.tecnoDesk.TecnoDesk.Entities.Cliente;
import br.com.tecnoDesk.TecnoDesk.Entities.Colaborador;
import br.com.tecnoDesk.TecnoDesk.Enuns.Aparelhos;
import br.com.tecnoDesk.TecnoDesk.Enuns.StatusOS;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class OsDTO {
	
	private	Cliente cliente;
	
	private Colaborador colaborador;
	
	private Aparelhos aparelhos;
	
	private String descricaoModelo;
	
	private String checkList;
	
	private String corpoChamado;
	
	private String laudoChamado;
	
	private StatusOS statusOS;

}
