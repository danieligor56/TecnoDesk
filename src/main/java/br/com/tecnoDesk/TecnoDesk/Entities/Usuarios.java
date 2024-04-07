package br.com.tecnoDesk.TecnoDesk.Entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Enuns.Roles;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USUARIO")

public class Usuarios implements Serializable,UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id",nullable = false,unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "codGrpEmp_id",nullable = false)
	private Empresa codEmpresa;
	
	@NotNull
	@NotEmpty
	@Column(name = "email",nullable = false,unique = true)
	private String email;
	
	@NotNull
	@NotEmpty
	@Column(name = "password",nullable = false,unique = false)
	private String pass;
	
	@Column(name="role", nullable = false, unique = false )
	private Roles role;
	
	@Column(name = "FlgAtvReg",nullable = false)
	private boolean atvReg;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(this.role == Roles.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
		else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return pass;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public Usuarios(@NotNull @NotEmpty String email, @NotNull @NotEmpty String pass, Roles role, boolean atvReg,Empresa codEmpresa) {
		super();
		this.email = email;
		this.pass = pass;
		this.role = role;
		this.atvReg = atvReg;
		this.codEmpresa = codEmpresa;
	
	}

}
