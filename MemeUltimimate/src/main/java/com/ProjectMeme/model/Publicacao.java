package com.ProjectMeme.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
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
	//private String username;

	public Publicacao() {
		
	}
	
	public Publicacao(int id, String texto, int id_user, int likes) {
		super();
		this.id = id;
		this.texto = texto;
		this.id_user = id_user;
		this.likes = likes;
	}

	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTexto() {
		return texto;
	}


	public void setTexto(String texto) {
		this.texto = texto;
	}


	public int getId_user() {
		return id_user;
	}


	public void setId_user(int id_user) {
		this.id_user = id_user;
	}


	public int getLikes() {
		return likes;
	}


	public void setLikes(int likes) {
		this.likes = likes;
	}


	@Override
	public String toString() {
		return "Publicacao [id=" + id + ", texto=" + texto + ", id_user=" + id_user + ", likes=" + likes + "]";
	}
    
    
	
}
