package br.com.tecnoDesk.TecnoDesk.Enuns;

public enum Roles {
	
	ADMIN,USER;
	
	String role;
	
	void UserRole(String role){
		this.role = role;
	}

	public String getRole() {
		return role;
	}
}
