package Main.web.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Main.Validation.RegisterFormValidator;
import Main.model.User;
import Main.service.UserService;
@Controller
public class LoginController {
	
	private static Logger log = Logger.getLogger(UserController.class.getName());
	@Autowired
	private RegisterFormValidator validator;
	private UserService userService;
	@Autowired
public void setUserService(UserService userService) {
	this.userService = userService;
}
	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("user","user");
	    return "login";
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
	}
