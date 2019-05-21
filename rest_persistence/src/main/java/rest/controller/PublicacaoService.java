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

<<<<<<< HEAD
import rest.dao.UserDAO;
import rest.model.Publicacao;

@Path("/posts")
public class PublicacaoService {


	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Publicacao> getPosts() {
		return UserDAO.getAllPosts();
=======
import rest.dao.PublicacaoDAO;
import rest.dao.UserDAO;
import rest.model.Publicacao;
import rest.model.User;

@Path("/publicacao")
public class PublicacaoService {
	private static Publicacao p;

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Publicacao> getPublicacao() {
		return PublicacaoDAO.getPublicacao();
>>>>>>> c76dbdff49b573d369e9dca160d5d04c2bccc733
	}

	// Controle da resposta (status code, mensagem)
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
<<<<<<< HEAD
	public Response getPost(@PathParam("id") int id) {
		return Response.status(Status.OK).entity(UserDAO.getPost(id)).build();
	}
/*
	@GET
	@Path("/search")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public User getUserByName(@QueryParam("username") String username) {

		return UserDAO.getUserByUsername(username);
	}
*/
    @POST
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addPost(@FormDataParam("image") InputStream uploadedInputStream,
            @FormDataParam("texto") String texto, @FormDataParam("like") int like,  @FormDataParam("idUsers") int idUsers) {

        if(texto == null || idUsers == 0 ) {
            return Response.status(400).build();
            
        }
       // System.out.println("iaiaiiaiia, chegou aqui");
        return Response.status(Status.OK).entity(UserDAO.addPost(texto,idUsers, like,  uploadedInputStream)).build();
=======
	public Response gePublicacao(@PathParam("id") int id) {
		return Response.status(Status.OK).entity(PublicacaoDAO.getPublicacao(id)).build();
	}

	@GET
	@Path("/search")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Publicacao getPublicacaoByName(@QueryParam("texto") String texto) {

		return PublicacaoDAO.getPublicacaoByUsername(texto);
	}

    @POST
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addPublicacao(@FormDataParam("image") InputStream uploadedInputStream,
            @FormDataParam("texto") String texto, @FormDataParam("id_user") int id_user,  @FormDataParam("id")int id,  @FormDataParam("likes") int likes) {
        
        return Response.status(Status.OK).entity(PublicacaoDAO.addPublicacao(texto, id_user, id, likes, null)).build();
>>>>>>> c76dbdff49b573d369e9dca160d5d04c2bccc733
    }

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
<<<<<<< HEAD
	public Publicacao updateUser(@PathParam("id") int id, @FormDataParam("image") InputStream uploadedInputStream,
			@FormDataParam("image") FormDataContentDisposition contentDispositionHeader,
			@FormDataParam("texto") String texto, @FormDataParam("idUsers") int idUsers, @FormDataParam("like") int like) {
		
		if(contentDispositionHeader.getFileName() == null) {
			return UserDAO.updatePost(id, texto,idUsers, like, null);	
		} else {
			return UserDAO.updatePost(id,texto,idUsers, like, uploadedInputStream);
		}
	}
=======
	public Publicacao updatePublicacao(@PathParam("texto") String texto, @PathParam("id_user") int id_user, @PathParam("id") int id, @FormDataParam("image") InputStream uploadedInputStream,
			@FormDataParam("image") FormDataContentDisposition contentDispositionHeader,
			@FormDataParam("likes") int likes) {
				if(contentDispositionHeader.getFileName() == null) {
					if(p.getlikes() == true) return PublicacaoDAO.updatePublicacao(id, likes, null);
				}else {
				return PublicacaoDAO.addPublicacao(texto, id, id_user,likes, uploadedInputStream);
				}
				return null;
			}
>>>>>>> c76dbdff49b573d369e9dca160d5d04c2bccc733

	@DELETE
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
<<<<<<< HEAD
	public void deletePost(@PathParam("id") int id) {
		UserDAO.deletePosts(id);
=======
	public void deleteUser(@PathParam("id") int id) {
		PublicacaoDAO.deletePublicacao(id);
>>>>>>> c76dbdff49b573d369e9dca160d5d04c2bccc733
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
<<<<<<< HEAD

}
=======
}
>>>>>>> c76dbdff49b573d369e9dca160d5d04c2bccc733
