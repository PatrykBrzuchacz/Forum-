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
/** @return returns the look of the Home page
 * */
	@RequestMapping("/")
	public String home() {
		return "index";
	}
	

	/** @return returns the look of the about page
	 * */
	@GetMapping("/about")
	public  String about() {
		return "about";
	}
	/** @return returns the page that is displayed during the login error
	 * */
    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }
    /** @return returns the look of the admin page
     * */
    @GetMapping("/admin")
    public String admin()    {
    	return "/admin";
    }
   
}