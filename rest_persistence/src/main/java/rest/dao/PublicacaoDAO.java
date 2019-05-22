package rest.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import rest.model.Publicacao;
import rest.model.User;
import rest.util.DbUtil;

public class PublicacaoDAO {
	static Publicacao p;
	

	private static Connection connection = DbUtil.getConnection();

//	Para manipular Posts
	public static Publicacao addPublicacao(String texto, int id_user, int likes, InputStream input) {
		try {
			PreparedStatement pStmt = connection.prepareStatement("insert into publicacao(texts, id_users, likes) values (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			pStmt.setString(1, texto);
			pStmt.setInt(2, id_user);
			//pStmt.setString(2, id_user);
			pStmt.setInt(3, likes);
			pStmt.executeUpdate();
			ResultSet rs = pStmt.getGeneratedKeys();
			if (rs.next()) {
				uploadFile(input, rs.getInt("id"));
				
				return new Publicacao(rs.getString("texts"), rs.getInt("id_users"),rs.getInt("id"), rs.getInt("likes"), null ,rs.getInt("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Publicacao updatePublicacao(int id, String texto, int id_user, int likes, InputStream input) {
		try {
			PreparedStatement pStmt = connection.prepareStatement("update publicacao set texts=?, id_users=?, likes=? where id=?",
					Statement.RETURN_GENERATED_KEYS);
			pStmt.setString(1, texto);
			pStmt.setInt(2, id_user);
			pStmt.setInt(3, likes);
			pStmt.setInt(4, id);
			pStmt.executeUpdate();
			ResultSet rs = pStmt.getGeneratedKeys();
			if (rs.next()) {
				if(input != null)
					uploadFile(input, rs.getInt("id"));
				return new Publicacao(rs.getString("texts"), rs.getInt("id_users"),rs.getInt("id"), rs.getInt("likes"), null,rs.getInt("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void deletePosts(int id) {
		try {
			PreparedStatement pStmt = connection.prepareStatement("delete from publicacao where id=?");
			pStmt.setInt(1, id);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<Publicacao> getAllPosts() {
		List<Publicacao> post = new ArrayList<Publicacao>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT post.id, post.likes,post.id_users, u.username, post.texts" + 
					" FROM publicacao post, users u WHERE post.id_users = u.id");
			while (rs.next()) {
				Publicacao publi = new Publicacao(rs.getString("texts"), rs.getInt("id_users"),rs.getInt("id"), rs.getInt("likes"), rs.getString("username"),rs.getInt("id"));
				post.add(publi);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return post;
	}
	
	public static  List<Publicacao>  getPost(int id) {
		try {
			/*O que essa consulta faz?
			 * Ela me retorna todos as publicacoes que foram postadas
			 * por um determinado usuario e em ordem Decrecente
			 * */
			List<Publicacao> post = new ArrayList<Publicacao>();
			PreparedStatement pStmt = connection.prepareStatement("SELECT post.id, post.likes,post.id_users, u.username, post.texts" + 
										" FROM publicacao post, users u WHERE post.id_users = u.id AND  u.id = ? ORDER BY post.id DESC");
			pStmt.setInt(1, id);
			ResultSet rs = pStmt.executeQuery();
			
			while (rs.next()) {
				Publicacao publi = new Publicacao(rs.getString("texts"), rs.getInt("id_users"),rs.getInt("id"), rs.getInt("likes"), rs.getString("username"),rs.getInt("id"));
				post.add(publi);
			}
			return post;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	private static void uploadFile(InputStream uploadedInputStream, int id) {
		try {
			InputStream inputStream = DbUtil.class.getClassLoader().getResourceAsStream("uploads.properties");
			Properties prop = new Properties();
			prop.load(inputStream);
			String folder = prop.getProperty("folder");
			String filePath = folder + id ;//+ "user";
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
