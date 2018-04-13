package Main.web.controller;

import java.util.ArrayList;
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

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "registerForm";
	}
	
	@PostMapping("/register")
	public String addUser(@ModelAttribute("user") User user,
			BindingResult result, RedirectAttributes redAtt) {
	validator.validate(user, result);
	if(result.hasErrors()) {
		log.info("Formularz rejestracyjny - NIE przeszedł walidacji");
			return "registerForm";
			}
		else {
			
			log.info("Formularz rejestracyjny - przeszedł walidacje");
			log.info(user.toString());
			userService.addWithDefaultRole(user);
			return "registerSuccess";
		}}
		 @RequestMapping("admin/delete/{id}")
		    public String delete(@PathVariable Integer id){
		        userService.deleteUser(id);
		        return "redirect:/admin/uzytkownicy";
		    }
	
	@GetMapping("/admin/uzytkownicy")
	public String uzytkownicy(Model m) {
		ArrayList<User> uzytkownicy = userService.findAll();
		m.addAttribute("uzytkownicy", uzytkownicy);
		return "admin/uzytkownicy";
	}
	@GetMapping("/admin/edit/{id}")
    public String edit(@PathVariable Integer id, Model m) {
		 m.addAttribute("user", userService.getUserById(id));
        return "admin/edit";
}
	@PostMapping("/update")
    public String update(User user) {
        userService.saveUser(user);

        return "redirect:/admin/uzytkownicy";
}
@GetMapping("user/aboutme/{id}")
public String about(@PathVariable Integer id, Model m) {
	 m.addAttribute("user", userService.getUserById(id));
	return "user/aboutme";
}
  
@GetMapping("user/adddetails/{id}")
public String addDetails(@PathVariable Integer id, Model m ) {
	 m.addAttribute("user", userService.getUserById(id));
    return "user/details";
}
@GetMapping("/user")
public String user(Model m) {
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    
	 m.addAttribute("user", userRepo.findByEmail(auth.getName()));
	return "user";
}
/*@PostMapping("/login/{id}")
public String loginn(@PathVariable Integer id, Model m) {
	User user =  userService.getUserById(id);
	m.addAttribute("user", user);
	return "redirect:/user/" + user.getId();
}*/
@PostMapping("/adding")
public String saveDetails(User user) {

	 User updatingUser = userService.getUserById(user.getId());
	    updatingUser.setMobileNumber(user.getMobileNumber());
	    updatingUser.setNationality(user.getNationality());
	    updatingUser.setGender(user.getGender());
	    userService.saveUser(updatingUser);
	    return "redirect:/user";
  
}
}