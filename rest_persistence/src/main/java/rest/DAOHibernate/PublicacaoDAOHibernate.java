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
//import javax.persistence.Query;
import javax.persistence.Query;

import rest.model.Publicacao;
import rest.model.User;
import rest.util.DbUtil;

public class PublicacaoDAOHibernate {
	private static EntityManagerFactory factory;
	private static EntityManager manager ;
	
	private static void openConection() {
		factory = Persistence.createEntityManagerFactory("tarefas");
		manager = factory.createEntityManager();
	}
	
	private static void closeConection() {
		manager.close();
		factory.close();	
	}
	
	public static Publicacao addPublicacao(Publicacao publicacao, InputStream input) {

		actionBD(publicacao,1,0);
		
		int id = getIdPublicacao(publicacao.getUsername(), publicacao.getTexto()) ;
		
		System.out.println("id da publicacao que acabou de ser inserida: "+id);
		
		if(id != -1 ) uploadFile(input, id );	
		else System.err.println("Erro ao regastar o id do novo usuario!");
		
		return publicacao;
	}
	
	public static List<Publicacao> search(String str){
		return getQuery("from Publicacao where texts like '%"+str+"%' ORDER BY id DESC");
	}
	public static List<Publicacao> searchUsername(String str) {
		
		return getQuery("from Publicacao where username like '%"+str+"%' ORDER BY id DESC");
	}
	private static int getIdPublicacao(String username, String texto) {

		List<Publicacao> encontrada = getQuery("from Publicacao where username = '" +
				username+"' and texts = '"+texto+"'");
		
		return encontrada.isEmpty()?  -1 : encontrada.get(0).getId();
	}
	public static List<Publicacao> getAllPublicacao() {
		
		List<Publicacao> encontrada = getQuery("from Publicacao ORDER BY id DESC");
		return encontrada.isEmpty() ? null : encontrada;
	}	

	public static List<Publicacao> getPublicacao(int id) {//realizando consultas
		//pegando todas as postagens de um derterminado usuario
		List<Publicacao> encontrada = getQuery("from Publicacao where id_users = "+id+" ORDER BY id DESC");
		return encontrada.isEmpty() ? null : encontrada;
	}
	
	private static List<Publicacao> getQuery(String sql){
		openConection();
		List<Publicacao> encontrada = manager.createQuery(sql , Publicacao.class).getResultList();
		closeConection();
		return encontrada;
	}
	
	public static Publicacao updatePublicacao(Publicacao publicacao, InputStream input) {
		actionBD(publicacao,2,0);
		
		int id = getIdPublicacao(publicacao.getUsername(), publicacao.getTexto()) ;
		if(input != null) {
			if(id != -1 ) uploadFile(input, id );	
			else System.err.println("Erro ao regastar o id da nova publicacao!");
		}
		return publicacao;
	}
	public static void updateUsername(String username, int id_user) {
		List<Publicacao> post = getQuery("from Publicacao where id_users = "+id_user);
		
		for(Publicacao p : post) {
			p.setUsername(username);
			actionBD(p,2,p.getId());
		}
	}
	public static void deletePublicacao(int id) {
		actionBD(null,3,id);
	}
	public static void deletePubliIdUser(int idUser) {
		openConection();
		/*tenta esse codigo
		String hql = "delete from Teste where nome = :nome";
	        Query query = session.createQuery(hql);
	        query.setString("nome","Testando a exclusão");
	        query.executeUpdate(); // retorna a quantidade de linhas que foram afetadas.
		*/
		String sql = "DELETE FROM Publicacao " + 
				"WHERE id_users = "+idUser;
	    Query query = manager.createQuery(sql);
        //query.setString("nome","Testando a exclusão");
        
        int total = query.executeUpdate(); // retorna a quantidade de linhas que foram afetadas.
	
		//List<Publicacao> encontrada = manager.createQuery(sql , Publicacao.class).getResultList();
		System.out.println("Teste da delecao Premiada kkkkk ; \n "
				+ " Foram removidas "+total+" de linhas do banco");
		closeConection();
	}
	
	private static void actionBD(Publicacao publicacao, int opcao, int id) {
		openConection();
		manager.getTransaction().begin();
		
		if(opcao == 1) //insert
			manager.persist(publicacao);
		else if(opcao == 2) //update
			manager.merge(publicacao);
		
		else {//delete
			Publicacao encontrada = manager.find(Publicacao.class, id);
			manager.remove(encontrada);
		}		
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
			
			String filePath = folder + id ;
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
