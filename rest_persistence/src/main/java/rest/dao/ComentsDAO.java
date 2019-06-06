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

import rest.model.Coments;
import rest.util.DbUtil;

public class ComentsDAO {

	private static Connection connection = DbUtil.getConnection();

	public static Coments addComents(int id_publicacao, int id_user, String comentario) {
		try {
			PreparedStatement pStmt = connection.prepareStatement("insert into comentarios(id_publicacao, id_users, comentario) values (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			pStmt.setInt(1, id_publicacao);
			pStmt.setInt(2, id_user);
			pStmt.setString(3, comentario);
			pStmt.executeUpdate();
			ResultSet rs = pStmt.getGeneratedKeys();
			if (rs.next()) {
				
				return new Coments(rs.getInt("id"),  rs.getInt("id_users"), rs.getInt("id_publicacao"), rs.getString("comentario"),null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Coments updateComents(int id, int id_publicacao, int id_user, String comentario) {
		try {
			PreparedStatement pStmt = connection.prepareStatement("update comentarios set id_publicacao=?, id_users=?, comentario=? where id=?",
					Statement.RETURN_GENERATED_KEYS);
			pStmt.setInt(1, id_publicacao);
			pStmt.setInt(2, id_user);
			pStmt.setString(3, comentario);
			pStmt.setInt(4, id);
			pStmt.executeUpdate();
			ResultSet rs = pStmt.getGeneratedKeys();
			if (rs.next()) {
				return new Coments(rs.getInt("id"),  rs.getInt("id_users"), rs.getInt("id_publicacao"), rs.getString("comentario"), null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void deleteComents(int id) {
		try {
			PreparedStatement pStmt = connection.prepareStatement("delete from comentarios where id=?");
			pStmt.setInt(1, id);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<Coments> getAllComents() {
		List<Coments> post = new ArrayList<Coments>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select c.id, c.id_users, c.id_publicacao, c.comentario, u.username from comentarios c, users u \r\n" + 
					"Where  u.id = c.id_users order By c.id DESC;");
			while (rs.next()) {
				Coments coment =  new Coments(rs.getInt("id"),  rs.getInt("id_users"), rs.getInt("id_publicacao"), rs.getString("comentario"), rs.getString("username"));
				post.add(coment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return post;
	}
	
	//metodo inutil?
	//o controle de qual comentario deve receber uma publicacao deve ser feito no front
	public static  List<Coments>  getComents(int id) {
		try {
			/*O que essa consulta faz?
			 * Ela me retorna todos as publicacoes que foram postadas
			 * por um determinado usuario e em ordem Decrecente
			 * */
			List<Coments> com = new ArrayList<Coments>();
			PreparedStatement pStmt = connection.prepareStatement("select c.id, c.id_users, c.id_publicacao, c.comentario, u.username from comentarios c, users u \r\n" + 
					"Where u.id = c.id_users order By c.id DESC;");
			pStmt.setInt(1, id);
			ResultSet rs = pStmt.executeQuery();
			
			while (rs.next()) {
				Coments coment =  new Coments(rs.getInt("id"),  rs.getInt("id_users"), rs.getInt("id_publicacao"), rs.getString("comentario"), rs.getString("username"));
				com.add(coment);
			}
			return com;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
