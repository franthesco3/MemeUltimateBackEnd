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

import rest.dao.ComentsDAO;
import rest.model.Coments;
import rest.model.User;

@Path("/coments")
public class ComentsService {

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Coments> getComents() {
		return ComentsDAO.getAllComents();
	}

	// Controle da resposta (status code, mensagem)
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getComents(@PathParam("id") int id) {
		return Response.status(Status.OK).entity(ComentsDAO.getComents(id)).build();
	}


    @POST
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addComents(@FormDataParam("comentario") String comentario, @FormDataParam("idUser") int id_user,  @FormDataParam("idPostagem") int id_publicacao) {
        
    	if(comentario == null || id_user == 0 || id_publicacao == 0) {
        	System.out.println("Campos vazios, entre com valores válidos !");
        	
        	return Response.status(400).build();
        } 
        
        return Response.status(Status.OK).entity(ComentsDAO.addComents( id_publicacao, id_user, comentario)).build();
    }

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Coments updateComents(@FormDataParam("id") int id ,@FormDataParam("comentario") String comentario, @FormDataParam("idUser") int id_user,  @FormDataParam("idPostagem") int id_publicacao) {
		
		return ComentsDAO.updateComents( id, id_publicacao, id_user, comentario);
		
	}

	@DELETE
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void deleteComents(@PathParam("id") int id) {
		ComentsDAO.deleteComents(id);
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
