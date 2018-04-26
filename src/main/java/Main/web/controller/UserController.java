package Main.web.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Main.Validation.RegisterFormValidator;
import Main.model.User;
import Main.repository.UserRepository;
import Main.service.UserService;



@Controller
public class UserController {
	private static Logger log = Logger.getLogger(UserController.class.getName());
	@Autowired
	private RegisterFormValidator validator;
	@Autowired
	private UserRepository userRepo;
	private UserService userService;
		@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	//ADMIN CRUD
		/** deleting user by id
		 * @param id of deleting user
		 * @return list of users
		 * */
		 @RequestMapping("admin/delete/{id}")
		    public String delete(@PathVariable Integer id){
		        userService.deleteUser(id);
		        return "redirect:/admin/uzytkownicy";
		    }
		 /**
		  * finding all users and displaying them
		  * @return list of users
		  *  */
	@GetMapping("/admin/uzytkownicy")
	public String uzytkownicy(Model m) {
	List<User> uzytkownicy = userService.findAll();
		m.addAttribute("uzytkownicy", uzytkownicy);
		return "admin/uzytkownicy";
	}
	/**
	 * editing user by id
	 * @param id of user that we want to edit
	 * @return list of users 
	 * */
	@GetMapping("/admin/edit/{id}")
    public String edit(@PathVariable Integer id, Model m) {
		 m.addAttribute("user", userService.getUserById(id));
        return "admin/edit";
}
	@PostMapping("/update")
    public String update(User user, BindingResult result) {
		
		validator.validate2(user, result);
		if(result.hasErrors()) {
			log.info("Formularz rejestracyjny - NIE przeszedł walidacji");
				return "/admin/edit";
				}
		else {
        userService.saveUser(user);

        return "redirect:/admin/uzytkownicy";}
}
	
	
	// USER CRUD
	/**displaying data about user that is logged in
	 * @param id of logged user
	 * @return data of logged user
	 *  */
@GetMapping("user/aboutme/{id}")
public String about(@PathVariable Integer id, Model m) {
	 m.addAttribute("user", userService.getUserById(id));
	return "user/aboutme";
}
  /**model to add details to user with specific id
   * @param id of actual user
   * @return  url to add details
   * */
@GetMapping("user/adddetails/{id}")
public String addDetails(@PathVariable Integer id, Model m ) {
	 m.addAttribute("user", userService.getUserById(id));
    return "user/details";
}
/**model of user
 * @return url of actual user
 *  */
@GetMapping("/user")
public String user(Model m) {
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    
	 m.addAttribute("user", userRepo.findByEmail(auth.getName()));
	return "user";
}
/** 
 * adding details to actual user
 * @param user object to add details
 * @return url of actual user
 */
@PostMapping("/adding")
public String saveDetails(User user, BindingResult result) {

	 User updatingUser = userService.getUserById(user.getId());
	 validator.validateDetails(user, result);
	 if(result.hasErrors()) {
			log.info("Formularz rejestracyjny - NIE przeszedł walidacji");
				return "/user/details";
				}else {
	    updatingUser.setMobileNumber(user.getMobileNumber());
	    updatingUser.setNationality(user.getNationality());
	    updatingUser.setGender(user.getGender());
	   
	    userService.saveUser(updatingUser);
	    return "redirect:/user";
				}
}
/**
 * view of all users
 * 
 * @return list of users
 */
@GetMapping("/user/allusers/")
private String allUsers(Model m) {
	List<User> uzytkownicy = userService.findAll();
	m.addAttribute("uzytkownicy", uzytkownicy);
	return "user/allusers";
}


	
}

