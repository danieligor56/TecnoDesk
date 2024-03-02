package br.com.tecnoDesk.TecnoDesk.DTO;

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

	private String email;

	private long documento;

	private long tel;

	private long cel1;

	private long cel2;

	private String estado;

	private String cidade;

	private String logradouro;

	private int numero;

	private String obs;

	private boolean atvReg;

}
