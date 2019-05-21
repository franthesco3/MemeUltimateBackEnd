package rest.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User {
	
	private int id;
	private String username;
	private String password;
	private String email;
	private String telefone;
	private String data;
	private ArrayList<Publicacao> publicacao = new ArrayList<Publicacao>();
	
	public User() {
		
	}
		
	public User(int id, String username, String password, String email, String telefone, String data) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.telefone = telefone;
		this.data = data;
		
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
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
	
	private void set(String email) {
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
	
	public ArrayList getPublicacao() {
		return publicacao;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + "]";
	}	
}
