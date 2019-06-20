package rest.DAOHibernate;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import rest.model.Publicacao;
import rest.model.User;

public class TestHibernate {
	static EntityManagerFactory factory =
			Persistence.createEntityManagerFactory("tarefas");

	public static void main(String[] args) {

		EntityManager manager = factory.createEntityManager();
		List<Publicacao> encontradas = manager.createQuery("select post.id,post.id_users, post.likes, post.texts, post.username from Publicacao as post where post.id = 1"
				, Publicacao.class).getResultList();
		/*
		List<Publicacao> encontradas = manager.createQuery("FROM Publicacao as post, User as u WHERE post.id_users = u.id ORDER BY post.id DESC "
						, Publicacao.class).getResultList();
		
		TypedQuery<Publicacao> query =
				manager.createQuery("SELECT NEW rest.model.User( u.id, u.username,u.password,u.email, u.telefone, u.data)" + 
		" FROM User u, Publicacao post" + " WHERE post.id_users = u.id ORDER BY post.id DESC", Publicacao.class);
						*/
		/*User user = new User("natana","na","12","121","de");
		List<User> encontradas = manager.createQuery("from User", User.class).getResultList();;
		manager.getTransaction().begin();
		manager.persist(user);
		manager.getTransaction().commit();
		*/
		manager.close();
		factory.close();
		
	}
	
	public static void addTarefas(User tarefa) {
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		manager.persist(tarefa);
		manager.getTransaction().commit();
		manager.close();
	}
}
