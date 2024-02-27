package br.com.tecnoDesk.TecnoDesk.Entities;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")

public class Usuarios implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id",nullable = false,unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "login",nullable = false,unique = true)
	private String email;
	
	@Column(name = "password",nullable = false,unique = false)
	private String pass;

}
