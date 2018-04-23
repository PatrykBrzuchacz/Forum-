package Main.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import Main.Validation.RegisterFormValidator;
import Main.model.Post;
import Main.model.Topic;
import Main.model.User;
import Main.model.form.PostForm;
import Main.model.form.TopicForm;
import Main.service.TopicService;
import Main.service.UserService;



@Controller
public class TopicController {
	private static Logger log = Logger.getLogger(LoginController.class.getName());
	@Autowired
	private RegisterFormValidator validator;
	@Autowired
	private UserService userService;
	@Autowired
	private TopicService topicService;

	@GetMapping("/user/topics")
	private String topics(Model m) {
	List<Topic> topic = topicService.findAlltopics();
	m.addAttribute("topic", topic);
		return "/user/topics";
	}

	@GetMapping("/user/topics/createtopic")
	private String createTopic(Model m) {
		m.addAttribute("topicForm", new TopicForm());
		return "/user/createTopic";
	}
	
	@PostMapping("/user/topics/createtopic")
	private String createTopicPost(@ModelAttribute("topicForm") TopicForm topicF)
	{
		String author = SecurityContextHolder.getContext().getAuthentication().getName();
		Topic created = topicF.createTopica();
		User topicAuthor=userService.findByEmail(author);
		created.setAuthor(topicAuthor);
		Post firstPost = new Post();
		firstPost.setAuthor(topicAuthor);
		firstPost.setContent(topicF.getFirstPostContent());
	
	created.getPosts().add(firstPost);
	topicService.saveTopic(created);
	return "redirect:/user/topics/"+created.getTitle();
	}
	
	@GetMapping("/user/topics/{topictitle}")
	private String topic(@PathVariable("topictitle") String topictitle, Model m) {
	Topic topic=topicService.findByTitle(topictitle);
	m.addAttribute("topictitle",topictitle);
	List<Post> posts = topic.getPosts();
	m.addAttribute("postList", posts); 
	m.addAttribute("postAdd", new PostForm());
	return "/user/post";
	}
	
	@PostMapping("/user/topics/{topictitle}")
	private String addPost(@PathVariable("topictitle") String topictitle, @ModelAttribute("postAdd") PostForm form)
	{
		Post post = form.createPost();
		String author = SecurityContextHolder.getContext().getAuthentication().getName();
		User postAuthor = userService.findByEmail(author);
		post.setAuthor(postAuthor);
		Topic topic = topicService.findByTitle(topictitle);
		topic.getPosts().add(post);
		topicService.updateTopic(topic);
		return "redirect:/user/topics/" + topictitle;
		
		
		
	}

}
