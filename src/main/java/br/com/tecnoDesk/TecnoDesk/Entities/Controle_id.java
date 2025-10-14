package br.com.tecnoDesk.TecnoDesk.Entities;

import org.springframework.context.annotation.Primary;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Table(name = "tabela_identificador")
public class Controle_id {

	@EmbeddedId
	public long codEmpresa;
	
	@PrimaryKeyJoinColumn
	public long codigo_identificador_tabela;
	
	@Column
	public long sequencial;
	
}
