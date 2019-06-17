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
	private String texto;
	
	@Column(columnDefinition="bigint")
	private int id_user;
	
	@Column(columnDefinition="bigint")
	private int likes;
	@Column(columnDefinition="text")
	private String username;
	
	@Column(columnDefinition="text")
	private String image;
	
	//private boolean click = false;
	
//>>>>>>> c76dbdff49b573d369e9dca160d5d04c2bccc733

	public Publicacao(){
	
	}
	
	public Publicacao(int likes) {
		this.likes = likes;
	}
	
	public Publicacao(String texto, int id_user, int id, int likes, String username,int image) {
		this.texto = texto;
		this.likes = likes;
		this.id = id;
		this.id_user = id_user;
		this.username = username;
		this.image = ""+image;//converter int para String
	}
	
	public String getTexto() {
		return texto;
	}
	
	public void setTexto(String texto) {
		this.texto = texto;
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
		return id_user;
	}
	
	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	@Override
	public String toString() {
		return "Publicacao [texto=" + texto + ", id_user=" + id_user + ", id=" + id + ", likes=" + likes + " username = "+username+"]";
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String Image) {
		this.image = image;
	}
	/*
	public boolean getlikes() {
		if(click == true) return true;
		return false;
	}
	*/
	
}