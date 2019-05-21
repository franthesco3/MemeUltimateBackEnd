package rest.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "post")
@XmlAccessorType(XmlAccessType.FIELD)
public class Publicacao {
	private String texto;
	private int id_user;
	private int id;
	private int likes;
	private String username;

	public Publicacao(){
	
	}
	
	public Publicacao(String texto, int id_user, int id, int likes) {
		this.texto = texto;
		this.likes = likes;
		this.id = id;
		this.id_user = id_user;
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
	
}