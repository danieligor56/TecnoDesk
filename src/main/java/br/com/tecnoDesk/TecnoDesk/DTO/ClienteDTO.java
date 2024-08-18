package br.com.tecnoDesk.TecnoDesk.DTO;

import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClienteDTO {

	private String nome;
	
	private Empresa empresa;

	private String email;

	private String documento;

	private String cel1;

	private String cel2;
	
	private String cep;

	private String estado;

	private String cidade;

	private String logradouro;

	private int numero;

	private String obs;

	private boolean atvReg;

}
