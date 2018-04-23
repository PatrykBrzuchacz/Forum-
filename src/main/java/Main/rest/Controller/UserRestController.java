package Main.rest.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
		public void register(@RequestBody User user) {
		userService.addWithDefaultRole(user);
		}
		
		//ADMIN

@GetMapping("/admin/list")
public List<User> uzytkownicy() {

	return userService.findAll();
}



@PutMapping("/admin/edit/{id}")
public void edit(@PathVariable Integer id,@RequestBody User user) {
	userService.addWithDefaultRole(user);
 
}

@DeleteMapping("/admin/delete/{id}")
public void delete(@PathVariable Integer id) {
userService.deleteUser(id);
}

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
public User getUser(@PathVariable  Integer id) {
return userService.getUserById(id);
}

@GetMapping("user/aboutme/{id}")
public User about(@PathVariable Integer id, Model m) {
return 	userService.getUserById(id);

}}
  



