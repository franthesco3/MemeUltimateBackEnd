package rest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
 
@Entity
@Table(name="users")
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User {
	/*Dicas para trabalhar com o hibernate;
	 * 1 - Por algum motivo, não permite criação de tabelas com letras maiusculas
	 * 2 - Mapei todas as variaveis que devem ser criadas no BD
	 * */
	
	@Id
	@GeneratedValue
	private int id;
	
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
	
	//private ArrayList<Publicacao> publicacao = new ArrayList<Publicacao>();
	
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
	public User( String username, String password, String email, String telefone, String data) {
		super();
		
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
	/*
	public ArrayList getPublicacao() {
		return publicacao;
	}
	*/
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + "]";
	}	
}
