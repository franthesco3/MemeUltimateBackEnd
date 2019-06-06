package com.ProjectMeme.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue
	private Integer id;
    
    @Column(columnDefinition="text")
	private String username;
    
    @Column(columnDefinition="text")
	private String password;
    
    @Column(columnDefinition="text")
	private String email;
    
    @Column(columnDefinition="text")
	private String telefone;
    
    @Column(columnDefinition="text")
	private String data;

    public User(){
    	
    }
    
    
	public User(Integer id, String username, String password, String email, String telefone, String data) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.telefone = telefone;
		this.data = data;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", telefone=" + telefone + ", data=" + data + "]";
	}

	
}
