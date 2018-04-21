package Main.rest.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Main.Validation.RegisterFormValidator;
import Main.model.Post;
import Main.model.Topic;
import Main.model.User;
import Main.model.form.PostForm;
import Main.model.form.TopicForm;
import Main.repository.TopicRepository;
import Main.repository.UserRepository;
import Main.service.TopicService;
import Main.service.UserService;

@RestController
@RequestMapping("/api")
public class TopicRestController {

	private static Logger log = Logger.getLogger(TopicRestController.class.getName());
	@Autowired
	private RegisterFormValidator validator;
	@Autowired
	private UserRepository userRepo;
	@Autowired TopicRepository topicRepo;
	private TopicService topicService;
	@Autowired
	public void setTopicService(TopicService topicService) {
		this.topicService = topicService;
	}
	public UserService userService;
		@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
		@GetMapping("/user/topics")
		public ArrayList<Topic> topicRest() {
			return (ArrayList<Topic>)topicService.findAlltopics();
			
		
		}

		
		@PostMapping("/user/topics/{id}/createtopic")
		public String createTopicPost(TopicForm topicF, @PathVariable Integer id)
		{
			Topic created = topicF.createTopica();
			User topicAuthor=userService.getUserById(id);
			created.setAuthor(topicAuthor);
			Post firstPost = new Post();
			firstPost.setAuthor(topicAuthor);
			firstPost.setContent(topicF.getFirstPostContent());
		
		created.getPosts().add(firstPost);
		topicService.saveTopic(created);
		return "redirect:/user/topics/"+created.getTitle();
		}
	
		@GetMapping("/user/topics/{topictitle}")
		public Topic topic(@PathVariable("topictitle") String topictitle) {
		return topicService.findByTitle(topictitle);
		}
		
		@PostMapping("/user/topics/{topictitle}")
		public String addPost(@PathVariable("topictitle") String topictitle, @ModelAttribute("postAdd") PostForm form)
		{
			Post post = form.createPost();
			String author = SecurityContextHolder.getContext().getAuthentication().getName();
			User postAuthor = userService.findByEmail(author);
			post.setAuthor(postAuthor);
			Topic topic = topicService.findByTitle(topictitle);
			topic.getPosts().add(post);
			topicService.updateTopic(topic);
			return "redirect:/user/topics/" + topictitle;
			
			
			
		}}
