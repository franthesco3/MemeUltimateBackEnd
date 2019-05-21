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

public class UserDAO {
	//Para Users
	private static Connection connection = DbUtil.getConnection();

	public static User addUser(String username, String password, String email, String telefone, String data, InputStream input) {
		try {
			PreparedStatement pStmt = connection.prepareStatement("insert into users(username, password, email, telefone, data) values (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			pStmt.setString(1, username);
			pStmt.setString(2, password);
			pStmt.setString(3, email);
			pStmt.setString(4, telefone);
			pStmt.setString(5, data);
			pStmt.executeUpdate();
			ResultSet rs = pStmt.getGeneratedKeys();
			if (rs.next()) {
				uploadFile(input, rs.getInt("id"));
				return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("telefone"), rs.getString("data"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static User updateUser(int id, String username, String password, String email, String telefone, String data, InputStream input) {
		try {
			PreparedStatement pStmt = connection.prepareStatement("update users set username=?, password=?, email=?, telefone=?, data=? where id=?",
					Statement.RETURN_GENERATED_KEYS);
			pStmt.setString(1, username);
			pStmt.setString(2, password);
			pStmt.setInt(3, id);
			pStmt.setString(4, email);
			pStmt.setString(5, telefone);
			pStmt.setString(6, data);
			pStmt.executeUpdate();
			ResultSet rs = pStmt.getGeneratedKeys();
			if (rs.next()) {
				if(input != null)
					uploadFile(input, rs.getInt("id"));
				return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("telefone"), rs.getString("data"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	

	public static void deleteUser(int id) {
		try {
			PreparedStatement pStmt = connection.prepareStatement("delete from users where id=?");
			pStmt.setInt(1, id);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from users order by id");
			while (rs.next()) {
				User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("telefone"), rs.getString("data"));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}

	public static User getUser(int id) {
		try {
			PreparedStatement pStmt = connection.prepareStatement("select * from users where id=?");
			pStmt.setInt(1, id);
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("telefone"), rs.getString("data"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	public static boolean i;
	public static User getUserByUsername(String username) {
		try {
			PreparedStatement pStmt = connection.prepareStatement("select * from users where username=?");
			pStmt.setString(1, username);
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("telefone"), rs.getString("data"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	//Para manipular Posts
	public static Publicacao addPost(String texto, int id_user, int likes, InputStream input) {
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
				
				return new Publicacao(rs.getString("texts"), rs.getInt("id_users"),rs.getInt("id"), rs.getInt("likes"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Publicacao updatePost(int id, String texto, int id_user, int likes, InputStream input) {
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
				return new Publicacao(rs.getString("texts"), rs.getInt("id_users"),rs.getInt("id"), rs.getInt("likes"));
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
				Publicacao publi = new Publicacao(rs.getString("texts"), rs.getInt("id_users"),rs.getInt("id"), rs.getInt("likes"));
				post.add(publi);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return post;
	}
	
	public static Publicacao getPost(int id) {
		try {
			PreparedStatement pStmt = connection.prepareStatement("select * from publicacao where id=?");
			pStmt.setInt(1, id);
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				return new Publicacao(rs.getString("texts"), rs.getInt("id_users"),rs.getInt("id"), rs.getInt("likes"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	//Para Manipular arquivos
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
