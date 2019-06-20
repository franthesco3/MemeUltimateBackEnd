package rest.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;


@Entity
@Table(name="publicacao")
@XmlRootElement(name = "publicacao")
@XmlAccessorType(XmlAccessType.FIELD)
public class Publicacao {
	
	@Id
	@GeneratedValue
	private int id;
	
	@Column(columnDefinition="text")
	private String texts;
	
	@Column(columnDefinition="bigint")
	private int id_users;
	
	@Column(columnDefinition="bigint")
	private int likes;
	@Column(columnDefinition="text")
	private String username;
	/*
	@Column(columnDefinition="text")
	private String image;
	*/
	//private boolean click = false;
	
//>>>>>>> c76dbdff49b573d369e9dca160d5d04c2bccc733

	public Publicacao(){
	
	}
	
	public Publicacao(int likes) {
		this.likes = likes;
	}
	
	
	
	public Publicacao(int id, String texts, int id_users, int likes, String username) {
		
		this.id = id;
		this.texts = texts;
		this.id_users = id_users;
		this.likes = likes;
		this.username = username;
	}

	public String getTexto() {
		return texts;
	}
	
	public void setTexto(String texto) {
		this.texts = texto;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String Username) {
		this.username = Username;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId_user() {
		return id_users;
	}
	
	public void setId_user(int id_user) {
		this.id_users = id_user;
	}

	@Override
	public String toString() {
		return "Publicacao [texto=" + texts + ", id_user=" + id_users + ", id=" + id + ", likes=" + likes + " username = "+username+"]";
	}
	

	/*
	public boolean getlikes() {
		if(click == true) return true;
		return false;
	}
	*/
	
}