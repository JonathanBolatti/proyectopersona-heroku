
package com.libreria.db.persona.entity;

import com.libreria.db.enums.Role;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Usuario extends Persona{
	
	private String username; 
	private String Password; 
	@Enumerated(EnumType.ORDINAL)
	private Role rol; 

	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String Password) {
		this.Password = Password;
	}
	public Role getRol() {
		return rol;
	}

	public void setRol(Role rol) {
		this.rol = rol;
	}
	
	
	
}
