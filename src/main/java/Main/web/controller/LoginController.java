package Main.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class LoginController {
	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("user","user");
	    return "login";
}
	}
