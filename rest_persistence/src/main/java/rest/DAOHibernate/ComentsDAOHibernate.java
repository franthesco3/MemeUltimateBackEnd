package rest.DAOHibernate;

import java.io.InputStream;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import rest.model.Coments;

public class ComentsDAOHibernate {
	private static EntityManagerFactory factory;
	private static EntityManager manager ;
	
	public static void openConection() {
		factory = Persistence.createEntityManagerFactory("tarefas");
		manager = factory.createEntityManager();
	}
	
	public static void closeConection() {
		manager.close();
		factory.close();	
	}
	
	public static Coments addComents(Coments coments) {

		actionBD(coments,1,0);
			
		return coments;
	}
	
	public static List<Coments> search(String str){
		return getQuery("from Coments where texts like '%"+str+"%' ORDER BY id DESC");
	}
	public static List<Coments> searchUsername(String str) {
		
		return getQuery("from Coments where username like '%"+str+"%' ORDER BY id DESC");
	}
	
	public static List<Coments> getAllComents() {
		
		List<Coments> encontrada = getQuery("from Coments ORDER BY id DESC");
		return encontrada.isEmpty() ? null : encontrada;
	}	
/*
	public static List<Coments> getComents(int id) {
		//pegando todas as postagens de um derterminado usuario
		List<Coments> encontrada = getQuery("from Coments where id_users = "+id+" ORDER BY id DESC");
		return encontrada.isEmpty() ? null : encontrada;
	}
	*/
	private static List<Coments> getQuery(String sql){
		openConection();
		List<Coments> encontrada = manager.createQuery(sql , Coments.class).getResultList();
		closeConection();
		return encontrada;
	}
	
	public static Coments updateComents(Coments coments) {
		actionBD(coments,2,0);
		
		return coments;
	}
	
	public static void deleteComents(int id) {
		actionBD(null,3,id);
	}
	public static void deleteComentsIdUser(int idUser) {
		openConection();
		String sql = "DELETE FROM Coments " + 
				"WHERE id_users = "+idUser;
		List<Coments> encontrada = manager.createQuery(sql , Coments.class).getResultList();
		System.out.println("Teste da delecao Premiada kkkkk ");
		closeConection();
	}
	
	private static void actionBD(Coments coments, int opcao, int id) {
		openConection();
		manager.getTransaction().begin();
		if(opcao == 1) //insert
			manager.persist(coments);
		else if(opcao == 2) //update
			manager.merge(coments);
		else {//delete
			Coments encontrada = manager.find(Coments.class, id);
			manager.remove(encontrada);
		}		
		manager.getTransaction().commit();
		closeConection();
	}
}
