package br.com.tecnoDesk.TecnoDesk.DTO;

import java.util.List;

import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Enuns.Ocupacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ColaboradorDTO {
	
	private Empresa empresa;
	
	private String nome;
	
	private long documento;
	
	private List<Ocupacao> ocupacao;
	
	private String email;

	private String tel1;

	private String cel1;
	 
	private String estado;
	
	private String cep;
	
	private String bairro;

	private String cidade;

	private String logradouro;

	private int numero;

	private String obs;
	
	private boolean atvReg;

}
