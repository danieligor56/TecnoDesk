package br.com.tecnoDesk.TecnoDesk.Entities;

import br.com.tecnoDesk.TecnoDesk.Enuns.Aparelhos;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "OS")
public class OS {

	private long id;
	
	private long numOS;
	
	private Cliente cliente;
	
	private Aparelhos aparelhos;
	
	private String descricaoModelo;
	
	private String checkList;
	
	private String corpoChamado;
	
	
	
	
	
	
	
	
}
