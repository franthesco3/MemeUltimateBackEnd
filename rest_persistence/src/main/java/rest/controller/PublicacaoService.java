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
import rest.model.Publicacao;
import rest.model.User;

@Path("/publicacao")
public class PublicacaoService {
	private Publicacao p;
	// Controle da resposta (status code, mensagem)
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Publicacao> getPublicacao() {
		//return PublicacaoDAO.getAllPosts();
		return PublicacaoDAOHibernate.getAllPublicacao();
	}
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Publicacao> getPost(@PathParam("id") int id) {
		return PublicacaoDAOHibernate.getPublicacao(id);
	}

	@GET
	@Path("/search")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Publicacao> searchPost(@QueryParam("values") String str) {
		System.out.println("Conteudo da pesquisa "+str);
		return PublicacaoDAOHibernate.search(str);
	}
	@GET
	@Path("/username")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Publicacao> usernamePost(@QueryParam("values") String str) {
		System.out.println("Conteudo da pesquisa "+str);
		return PublicacaoDAOHibernate.searchUsername(str);
	}
    @POST
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addPost(@FormDataParam("image") InputStream uploadedInputStream,
            @FormDataParam("texto") String texto, @FormDataParam("like") int like,  @FormDataParam("idUsers") int idUsers,
            @FormDataParam("username") String username) {

        if(texto == null || idUsers == 0 ) {
            return Response.status(400).build();
            
        }
        System.out.println(username);
       // System.out.println("iaiaiiaiia, chegou aqui");
        return Response.status(Status.OK).entity(PublicacaoDAOHibernate.addPublicacao(new Publicacao(0, texto,  idUsers  ,  like, username),  uploadedInputStream)).build();

    }
/*
	@GET
	@Path("/search")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Publicacao getPublicacaoByName(@QueryParam("texto") String texto) {

		return PublicacaoDAO.getPublicacaoByUsername(texto);
	}
*/
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Publicacao updatePublicacao(@PathParam("texto") String texto, @PathParam("id_user") int id_user, @PathParam("id") int id, @FormDataParam("image") InputStream uploadedInputStream,
			@FormDataParam("image") FormDataContentDisposition contentDispositionHeader,
			@FormDataParam("likes") int likes,@FormDataParam("username") String username) {
	
		if(contentDispositionHeader.getFileName() == null) {
			//if(p.getlikes() == true) 
				return PublicacaoDAOHibernate.updatePublicacao(new Publicacao(id, texto,  id_user  ,  likes, username), null);
			//return PublicacaoDAO.updatePublicacao(id, likes, null);
			
		}else {
			//não seria melhor assim???
			return PublicacaoDAOHibernate.updatePublicacao(new Publicacao(id, texto,  id_user  ,  likes, username), uploadedInputStream);
			//return PublicacaoDAO.addPublicacao(texto, id, id_user,likes, uploadedInputStream);
		}
		//return null;
	}


	@DELETE
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void deletePost(@PathParam("id") int id) {
		PublicacaoDAOHibernate.deletePublicacao(id);
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

