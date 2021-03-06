package rest.controller;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import rest.DAOHibernate.PublicacaoDAOHibernate;
import rest.DAOHibernate.UserDAOHibernate;
import rest.DAOHibernate.ComentsDAOHibernate;
import rest.dao.UserDAO;
import rest.model.User;

@Path("/users")
public class UserService {
	/*Oi frathesco, alterei essa classe para que ja trabalhe com o HIbernate,bjs*/
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<User> getUsers() {
		//return UserDAO.getAllUsers();
		return UserDAOHibernate.getAllUser();
	}

	// Controle da resposta (status code, mensagem)

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getUser(@PathParam("id") int id) {
		//	return Response.status(Status.OK).entity(UserDAO.getUser(id)).build();
		return Response.status(Status.OK).entity(UserDAOHibernate.getUser(id)).build();
	}
	
	@GET
	@Path("/search")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public User getUserByName(@QueryParam("username") String username) {
		//	return UserDAO.getUserByUsername(username);
		return null;//UserDAOHibernate.getUserByUsername(username);
	} 

	
	@POST
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addUser(@FormDataParam("image") InputStream uploadedInputStream,
            @FormDataParam("username") String username, @FormDataParam("password") String password,  @FormDataParam("email") String email,  @FormDataParam("telefone") String telefone,  @FormDataParam("data") String data) {
        
    	if(username == null || password == null || username.equals("null") || password.equals("null")) {
        	System.out.println("Campos vazios, entre com valores v�lidos !");
        	
        	return Response.status(401).build();
        } 
        if(UserDAOHibernate.getUserByUsername(username) != null) {
        	System.out.println("Usu�rio ja existente, tente outros dados!\n"+UserDAOHibernate.getUserByUsername(username));
        	return Response.status(400).build();	
        }
        
        if(data == null || data.equals("null") || telefone == null  || telefone.equals("null") || email == null  || email.equals(null)) {
        	System.out.println("Algum dos campos de email, telefone ou data est�o vazios");
        	return Response.status(700).build();
        }
        System.out.println("valor de 'image':"+uploadedInputStream);
        
        if(uploadedInputStream == null) {
        	
        	//uploadedInputStream = "C:\\Users\\usuario eu\\Documents\\GitHub\\RedeSocialMemeUltimate\\img\\semImgPerfil.jpg";
        }
        
        return Response.status(Status.OK).entity(UserDAOHibernate.addUser(new User(username, password,email,telefone,data), uploadedInputStream)).build();
    }
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public User updateUser(@PathParam("id") int id, @FormDataParam("image") InputStream uploadedInputStream,
			@FormDataParam("image") FormDataContentDisposition contentDispositionHeader,
			@FormDataParam("username") String username, @FormDataParam("password") String password, @FormDataParam("email") String email, @FormDataParam("telefone") String telefone, @FormDataParam("data") String data) {
		
		//System.out.println("ola seu cabras");
		User user = UserDAOHibernate.getUser(id);
		//atualizar os demais coisas
		if(username != null && email != null && telefone != null && data != null) {
			return UserDAOHibernate.updateUser(new User(id, username,user.getPassword(),email,telefone,data),null);
		}
		
		//atualizar so a senha
		if(password != null && username == null && email == null && telefone == null && data == null) {
			System.out.println("i de u ceritp");
			return UserDAOHibernate.updateUser(new User(id, user.getUsername(),password,user.getEmail(),user.getTelefone(),user.getData()),null);
		}
		//para atualizar so a imagem
		if (uploadedInputStream != null) UserDAOHibernate.uploadFile(uploadedInputStream, id);
		
		return user;
		
	}

	@DELETE
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void deleteUser(@PathParam("id") int id) {
		
		//PublicacaoDAOHibernate.deletePubliIdUser(id);
		//ComentsDAOHibernate.deleteComentsIdUser(id);
		UserDAOHibernate.deleteUser(id);
		//n�o posso usar query 
		//UserDAO.deleteUser(id);
	}
	
	//Session
	@POST
	@Path("/oi")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/json")
    public Response hello(@Context HttpServletRequest req, @FormDataParam("name") String name) {
        HttpSession session = req.getSession(true);
        Object foo = session.getAttribute("foo");
        System.out.println(session.getId());
        
        if (foo != null) {
            System.out.println(foo.toString());
        } else {
            foo = "bar";
            session.setAttribute("foo", foo);
            System.out.println("first");
        }
        
        return Response.status(Status.OK).entity(foo.toString()).build();
    }
}