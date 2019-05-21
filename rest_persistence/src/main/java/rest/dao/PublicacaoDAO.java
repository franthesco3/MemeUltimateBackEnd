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

	public static Publicacao addPublicacao(String texto, int id_user, int id, int likes, InputStream input) {
		try {
			PreparedStatement pStmt = connection.prepareStatement("insert into publicacao(texto, id_user, id, likes, image) values (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			pStmt.setString(1, texto);
			pStmt.setInt(2, id_user);
			pStmt.setInt(3, id);
			pStmt.setInt(4, likes);
			pStmt.executeUpdate();
			ResultSet rs = pStmt.getGeneratedKeys();
			if (rs.next()) {
				uploadFile(input, rs.getInt("id"));
				return new Publicacao(rs.getString("texto"), rs.getInt("id_user"), rs.getInt("id"), rs.getInt("likes"), rs.getString("image"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Publicacao updatePublicacao(int id, int likes, InputStream input) {
		try {
			PreparedStatement pStmt = connection.prepareStatement("update publicacao set id=?, likes=? where id=?",
					Statement.RETURN_GENERATED_KEYS);
			pStmt.setInt(1, id);
			pStmt.setInt(2, likes);
			pStmt.executeUpdate();
			ResultSet rs = pStmt.getGeneratedKeys();
			if(rs.next()) {
				uploadFile(input, rs.getInt("id"));
					return new Publicacao(rs.getInt(likes)+1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void deletePublicacao(int id) {
		try {
			PreparedStatement pStmt = connection.prepareStatement("delete from publicacao where id=?");
			pStmt.setInt(1, id);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<Publicacao> getPublicacao() {
		List<Publicacao> publicacao = new ArrayList<Publicacao>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from publicacao order by id");
			while (rs.next()) {
				Publicacao publica = new Publicacao(rs.getString("texto"), rs.getInt("id_user"), rs.getInt("id"), rs.getInt("likes"), rs.getString("image"));
				publicacao.add(publica);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return publicacao;
	}

	public static Publicacao getPublicacao(int id) {
		try {
			PreparedStatement pStmt = connection.prepareStatement("select * from publicacao where id=?");
			pStmt.setInt(1, id);
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				return new Publicacao(rs.getString("texto"), rs.getInt("id_user"), rs.getInt("id"), rs.getInt("likes"), rs.getString("image"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Publicacao getPublicacaoByUsername(String username) {
		try {
			PreparedStatement pStmt = connection.prepareStatement("select * from publicacao where username=?");
			pStmt.setString(1, username);
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				return new Publicacao(rs.getString("texto"), rs.getInt("id_user"), rs.getInt("id"), rs.getInt("likes"), rs.getString("image"));
			}
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
			String filePath = folder + id;
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
