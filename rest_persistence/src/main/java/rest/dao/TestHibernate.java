package rest.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import rest.model.User;

public class TestHibernate {
	static EntityManagerFactory factory =
			Persistence.createEntityManagerFactory("tarefas");

	public static void main(String[] args) {

		EntityManager manager = factory.createEntityManager();
		
		List<User> encontradas = manager.createQuery("from users, publicacao, comentarios ", User.class).getResultList();;
		
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
