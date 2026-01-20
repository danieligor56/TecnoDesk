package br.com.tecnoDesk.TecnoDesk.DTO;

import br.com.tecnoDesk.TecnoDesk.Enuns.Segmento;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaUsuarioDTO {
	
	// 1º CRIAR NOVA EMPRESA. 
	// VERIFICAR SE A EMPRESA É CNPJ OU CPF. 
	
	private String razaoSocial;
	
	private String nomEmpresa;
	
	private String docEmpresa;
	
	private String mail;
	
	private String cel;
	
	private String tel;
	
	private String site;
	
	private int segmento;
	
	//BLOCO ENDEREÇO - SESSÃO EMPRESA //
	private String cep;
	
	private String logra;

	private int num;

	private String comp;

	private String bairro;

	private String municipio;

	private String uf;
	
	// SESSÃO DE USUÁRIO: 
	
	private String nomeCompleto;
	
	private String email; 
	
	private String pass;
	
}
