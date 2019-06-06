package com.ProjectMeme.Service;


import java.io.IOException;
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

@RestController
@CrossOrigin
@RequestMapping(path = "/api/users")
public class UserService {
	
	@Autowired
	private UserRepository user;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<User>> getCursos() {
		return new ResponseEntity<List<User>>(user.findAll(),HttpStatus.OK);
		}
	
}
