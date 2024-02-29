package br.com.tecnoDesk.TecnoDesk.DTO;

import br.com.tecnoDesk.TecnoDesk.Enuns.Ocupacao;
import lombok.Getter;

public class ColaboradorDTO {

	private String nome;
	
	private long documento;
	
	private Ocupacao ocupacao;
	
	private String email;

	private String tel1;

	private String cel1;
	 
	private String estado;

	private String cidade;

	private String logradouro;

	private int numero;

	private String obs;
	
	private boolean atvReg;

}
