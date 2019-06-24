package rest.DAOHibernate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import rest.model.User;
import rest.util.DbUtil;

public class UserDAOHibernate {
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
	
	public static User addUser(User user, InputStream input) {
		
		openConection();
		manager.getTransaction().begin();
		manager.persist(user);
		manager.getTransaction().commit();
		int id = getIdUser(user.getUsername()) ;
		
		if(id != -1 ) uploadFile(input, id );	
		else System.err.println("Erro ao regastar o id do novo usuario!");
		
		closeConection();
		return user;
	}
	
	private static int getIdUser(String username) {
		
		List<User> encontrada = getUserByUsername(username);
		return encontrada.isEmpty()?  -1 : encontrada.get(0).getId();
	}

	public static User getUser(int id) {//get por id
		openConection();
		User encontrada = manager.find(User.class, id);
		closeConection();
		return encontrada;
	}
	
	public static List<User> getAllUser() {
		
		List<User> encontrada = getQuery("from User");
		return encontrada.isEmpty() ? null : encontrada;
	}
	
	public static List<User> getUserByUsername(String username) {

		List<User> encontrada = getQuery("from User where username = '" +
				username+"'");
		
		return encontrada.isEmpty() ? null : encontrada;
	}
	public static User getUserByQuery(int id) {//realizando consultas
		
		List<User> encontrada = getQuery("from User where id = " + id);
		return encontrada.isEmpty() ? null : encontrada.get(0);
	}
	
	private static List<User> getQuery(String sql){
		openConection();
		List<User> encontrada = manager.createQuery(sql , User.class).getResultList();
		closeConection();
		return encontrada;
	}
	
	public static User updateUser(User user, InputStream input) {
		//Sera que assim da certo?
		
		
		openConection();
		manager.getTransaction().begin();
		manager.merge(user);
		manager.getTransaction().commit();
		if(input != null) {
			int id = getIdUser(user.getUsername()) ;
			
			if(id != -1 ) uploadFile(input, id );	
			else System.err.println("Erro ao regastar o id do novo usuario!");
		}
		closeConection();
		return user;
	}
	
	public static void deleteUser(int i) {
		openConection();
		User encontrada = manager.find(User.class, i);
		manager.getTransaction().begin();
		manager.remove(encontrada);
		manager.getTransaction().commit();
		closeConection();
	}
	

	//Para Manipular arquivos
	private static void uploadFile(InputStream uploadedInputStream, int id) {
		try {
			InputStream inputStream = DbUtil.class.getClassLoader().getResourceAsStream("uploads.properties");
			Properties prop = new Properties();
			prop.load(inputStream);
			String folder = prop.getProperty("folder");
			String filePath = folder + id + "user";
			System.out.println("O valor de filePath:"+filePath);
			saveFile(uploadedInputStream, filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void saveFile(InputStream uploadedInputStream, String serverLocation) {

		try {
			OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			outpuStream = new FileOutputStream(new File(serverLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outpuStream.write(bytes, 0, read);
			}
			outpuStream.flush();
			outpuStream.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
