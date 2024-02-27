package br.com.tecnoDesk.TecnoDesk.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "OS")
public class OS {

	private long id;
	
	private Cliente cliente;
	
	private long aparelho;
	
}
