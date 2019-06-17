package rest.dao;

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
		//Erro aqui, como pegar o id do cara que acabou de ser inserido??
		uploadFile(input, user.getId());
		closeConection();
		return user;
	}
	public static User getUser(int id) {//get por id
		openConection();
		User encontrada = manager.find(User.class, id);
		closeConection();
		return encontrada;
	}
	
	public static List<User> getUser() {
		openConection();
		List<User> encontradas = manager.createQuery("from users",
		User.class).getResultList();
		closeConection();
		return encontradas;
	}
	
	public static User getUserByUsername(String username) {

		openConection();
		User encontrada = manager.createQuery("from User where username = " +
		username, User.class).getResultList().get(0);
		closeConection();
		return encontrada;
	}
	public static User getUserByQuery(int id) {//realizando consultas
		
		openConection();
		User encontrada = manager.createQuery("from User where id = " +
		id, User.class).getResultList().get(0);
		closeConection();
		return encontrada;
	}
	
	public static User updateUser(User user, InputStream input) {
		//Sera que assim da certo?
		openConection();
		manager.getTransaction().begin();
		manager.merge(user);
		manager.getTransaction().commit();
		///???????????????????????????????????????
		uploadFile(input, user.getId());
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
