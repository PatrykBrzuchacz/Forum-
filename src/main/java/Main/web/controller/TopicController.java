package Main.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import Main.Validation.RegisterFormValidator;
import Main.Validation.TopicFormValidator;
import Main.model.Post;
import Main.model.Topic;
import Main.model.User;
import Main.model.form.PostForm;
import Main.model.form.TopicForm;
import Main.service.TopicService;
import Main.service.UserService;



@Controller
public class TopicController {
	private static Logger log = Logger.getLogger(TopicController.class.getName());

	@Autowired
	private UserService userService;
	@Autowired
	private TopicService topicService;
	@Autowired
	private TopicFormValidator validator;
	/**
	 * a function that gives us the appearance of list topics
	 * 
	 * @return we return the look of the list of topics
	 */
	@GetMapping("/user/topics")
	private String topics(Model m) {
	List<Topic> topic = topicService.findAlltopics();
	m.addAttribute("topic", topic);
		return "/user/topics";
	}
/**  A function that gives us the appearance of creating a theme
 * @return we return the look of the topic creation
 *   */
	@GetMapping("/user/topics/createtopic")
	private String createTopic(Model m) {
		m.addAttribute("topicForm", new TopicForm());
		return "/user/createTopic";
	}
	/**    A function for creating topics
	 *  @param topicF
	 *   class object {@link @TopicForm} that is used to create topics
	 *  @param result - validation
	 *  @return If the page was created, we go to the theme of the topic, if not, it remains
	 * on the same page and try again
	 *  
	 *  */	
	@PostMapping("/user/topics/createtopic")
	private String createTopicPost(@ModelAttribute("topicForm") TopicForm topicF, BindingResult result)
	{
		validator.validateTopic(topicF, result);
		String author = SecurityContextHolder.getContext().getAuthentication().getName();
		if(!result.hasErrors())
		{		Topic created = topicF.createTopica();
		User topicAuthor=userService.findByEmail(author);
		created.setAuthor(topicAuthor);
		Post firstPost = new Post();
		firstPost.setAuthor(topicAuthor);
		firstPost.setContent(topicF.getFirstPostContent());
	
		
	created.getPosts().add(firstPost);

	topicService.saveTopic(created);
	return "redirect:/user/topics/"+created.getTitle();
	}
		else {
			log.info("errors");
			return "/user/createTopic";
		}}
	/**    Displaying a specific topic
	 * @param topictitle is used to find a specific topic
	 * @return We return information about a specific topic  with posts
	 *  */
	@GetMapping("/user/topics/{topictitle}")
	private String topic(@PathVariable("topictitle") String topictitle, Model m) {
	Topic topic=topicService.findByTitle(topictitle);
	if(topic==null) {
		log.info("brak tematu");
		return "/user/createTopic";
	}
	List<Post> posts = topic.getPosts();
	m.addAttribute("topictitle",topictitle);
	m.addAttribute("postList", posts); 
	m.addAttribute("postAdd", new PostForm());
	return "/user/post";
	}
	/**    The ability to add posts to a specific topic
	 * @param topictitle is used to find a specific topic
	 * @param form serves as a post creation model
	 * @return returns to us the topic with all added posts + our now added
	 *  */
	@PostMapping("/user/topics/{topictitle}")
	private String addPost(@PathVariable("topictitle") String topictitle, @ModelAttribute("postAdd") PostForm form, 
			BindingResult result)
	{	
		validator.validatePost(form, result);
		if(!result.hasErrors()) {
		Post post = form.createPost();
		String author = SecurityContextHolder.getContext().getAuthentication().getName();
		User postAuthor = userService.findByEmail(author);
		post.setAuthor(postAuthor);
		Topic topic = topicService.findByTitle(topictitle);
		topic.getPosts().add(post);
		topicService.updateTopic(topic);
		return "redirect:/user/topics/" + topictitle;
		}
		else {
			log.info("post error");
			return "/user/post";
		}	
	}
	/**
	 * Getting topics by user id
	 * @param id id of the user that we want to show topics
	 * @param m
	 * @return list of topics
	 */
	@GetMapping("/user/{id}/topics")
	private String UsersTopics(@PathVariable Integer id, Model m) {
		User user= userService.getUserById(id);
		List<Topic> top = topicService.findTopicByAuthor(user);
		m.addAttribute("topic", top);
		return "/user/topicsauthor";
	}
}
