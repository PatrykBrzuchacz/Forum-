package Main.rest.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import Main.Validation.RegisterFormValidator;
import Main.model.User;
import Main.repository.UserRepository;
import Main.service.UserService;



@RestController
@RequestMapping("/api")
public class UserRestController {
	private static Logger log = Logger.getLogger(UserRestController.class.getName());
	@Autowired
	private RegisterFormValidator validator;
	@Autowired
	private UserRepository userRepo;
	private UserService userService;
		@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
		
		//REGISTER
		@PostMapping("/register")
		public ResponseEntity<Void> register(@RequestBody User user,BindingResult result,UriComponentsBuilder ucBuilder ) {
			
			validator.validate(user, result);
			if (result.hasErrors()){
	            return new ResponseEntity<Void>(HttpStatus.CONFLICT);}
			else {
		userService.addWithDefaultRole(user);
		 HttpHeaders headers = new HttpHeaders();
	        headers.setLocation(ucBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
	        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
			}}
		
		//ADMIN

@GetMapping("/admin/list")
public ResponseEntity<List<User>> uzytkownicy() {
	log.info("getting all users");
	 List<User> users = userService.findAll();
     if (users == null || users.isEmpty()){
         log.info("users not found");
         return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
     }
     return new ResponseEntity<List<User>>(users, HttpStatus.OK);
}



@PutMapping("/admin/edit/{id}")
public ResponseEntity<User> edit(@PathVariable Integer id,@RequestBody User user,BindingResult result) {
	  validator.validate2(user, result);
	User currentUser = userService.getUserById(id);
	
	  log.info("updating user: {}");
		if (currentUser==null){
	    return new ResponseEntity<User>(HttpStatus.NOT_FOUND);}
		else if(result.hasErrors()) { 
			return new ResponseEntity<User>(HttpStatus.CONFLICT);
		}
		else {
	userService.addWithDefaultRole(user);
	return new ResponseEntity<User>(currentUser, HttpStatus.OK);
		}
}

@DeleteMapping("/admin/delete/{id}")
public ResponseEntity<Void> delete(@PathVariable Integer id) {
	 log.info("deleting user with id: {}");
	 User user=userService.getUserById(id);
	 if(user==null) {
		  return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	 }
	 else {
userService.deleteUser(id);
return new ResponseEntity<Void>(HttpStatus.OK);
	 }}

			// USER
@PutMapping("/user/{id}/details")
public void saveDetails(@RequestBody User user,@PathVariable Integer id) {

	 User updatingUser = userService.getUserById(id);
	    updatingUser.setMobileNumber(user.getMobileNumber());
	    updatingUser.setNationality(user.getNationality());
	    updatingUser.setGender(user.getGender());
	    userService.saveUser(updatingUser);
	    
}
@GetMapping("/user/{id}")
public ResponseEntity<User> getUser(@PathVariable  Integer id) {
User user =userService.getUserById(id);
if(user==null) {
	return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
}
else {
    return new ResponseEntity<User>(user, HttpStatus.OK);	
}
}

@GetMapping("user/aboutme/{id}")
public  ResponseEntity<User> about(@PathVariable Integer id, Model m) {
	User user =userService.getUserById(id);
	if(user==null) {
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}
	else {
	    return new ResponseEntity<User>(user, HttpStatus.OK);	
	}}}
  



