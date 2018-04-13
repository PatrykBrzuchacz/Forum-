package Main.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String home() {
		return "index";
	}

	@RequestMapping("/secured")
	@ResponseBody // stara sie zwrocic w formacie json
	public String secured() {
		return "secured page";
	}

	
	@GetMapping("/about")
	public  String about() {
		return "about";
	}
    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }
    @GetMapping("/admin")
    public String admin()    {
    	return "/admin";
    }
   
}