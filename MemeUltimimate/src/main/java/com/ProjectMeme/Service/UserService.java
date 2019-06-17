package com.ProjectMeme.Service;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ProjectMeme.Repository.UserRepository;
import com.ProjectMeme.model.User;
import com.ProjectMeme.util.Upload;

@RestController
@RequestMapping(path = "/api/users")
@CrossOrigin
public class UserService {
	
	@Autowired
	private UserRepository user;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<User>> getUser() {
		return new ResponseEntity<List<User>>(user.findAll(),HttpStatus.OK);
		}

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("id") Integer id) {
        Optional<User> u = user.findById(id);
 
        if (u.isPresent()) {
            return new ResponseEntity<User>(u.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }      
    }
   
    /*
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<User> getCurso(@RequestParam("nome") String nome) {
        System.out.println(nome);
        User u = user.findByNome(nome);
 
        if (u != null) {
            return new ResponseEntity<User>(u, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
 */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> addUser(MultipartFile image,
           String username, String password,String email,  String telefone,  String data) {
    	
        if (username == null ||  username.equals("null") || image == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User usuario = new User( null,  username,  password,  email,  telefone,  data);
        		
        User userAux = user.save(usuario);
        try {
            Upload.uploadFile(image.getInputStream(), userAux.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        return new ResponseEntity<User>(usuario, HttpStatus.OK);
    }
 
    /*
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> atualizarUser(@PathVariable("id") int id, String username, String password,String email,  String telefone,  String data,
            MultipartFile image) {
        if (username == null || username.equals("null") ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<User> usuario = user.findById(id);
 
        if (usuario.isPresent()) {
        	usuario.get().setUsername();
        	usuario.get().setNome(nome);
        	usuario.get().setDuracao(duracao);
            user.save(usuario.get());
            try {
                if (image != null) {
                    Upload.uploadFile(image.getInputStream(), id);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
 
            return new ResponseEntity<Curso>(curso.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
 */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletarCurso(@PathVariable("id") int id) {
        if(user.existsById(id)) {
            user.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
