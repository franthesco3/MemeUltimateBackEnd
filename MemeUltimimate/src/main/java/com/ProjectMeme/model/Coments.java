package com.ProjectMeme.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Coments {

    @Id
    @GeneratedValue
	public int id;
    
    @Column(columnDefinition="bigint")
	public int id_users;
    
    @Column(columnDefinition="bigint")
	public int id_publicacao;
	
    @Column(columnDefinition="text")
	public String comentario;
	//public String username;

	public Coments() {
		
	}
	
	public Coments(int id, int id_users, int id_publicacao, String comentario) {
		super();
		this.id = id;
		this.id_users = id_users;
		this.id_publicacao = id_publicacao;
		this.comentario = comentario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_users() {
		return id_users;
	}

	public void setId_users(int id_users) {
		this.id_users = id_users;
	}

	public int getId_publicacao() {
		return id_publicacao;
	}

	public void setId_publicacao(int id_publicacao) {
		this.id_publicacao = id_publicacao;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	@Override
	public String toString() {
		return "Coments [id=" + id + ", id_users=" + id_users + ", id_publicacao=" + id_publicacao + ", comentario="
				+ comentario + "]";
	}
    
    
}
