package Main.web.controller;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import Main.Validation.RegisterFormValidator;
import Main.model.Topic;
import Main.service.TopicService;
import Main.service.UserService;
@Controller
public class TopicController {
	private static Logger log = Logger.getLogger(LoginController.class.getName());
	@Autowired
	private RegisterFormValidator validator;
	private UserService userService;
	@Autowired
	private TopicService topicService;


	@GetMapping("/user/topics")
	private String topics(Model m) {
		ArrayList<Topic> topic = topicService.findAlltopics();
		m.addAttribute("topic", topic);
		return "/user/topics";
	}}
	

