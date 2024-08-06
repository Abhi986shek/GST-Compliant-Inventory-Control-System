package com.login.model;

import javax.persistence.Entity;
/*import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;*/
import javax.persistence.Id;

@Entity
public class Users {
    @Id
    private String username;
    private String password;
    private String role;
    private String status;
    private String name;
    
    public Users()
    {}
    
	public Users(String username, String password, String role, String status, String name) {
		super();
		
		this.username = username;
		this.password = password;
		this.role = role;
		this.status=status;
		this.name=name;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

    // Constructors, getters, and setters
    
}

