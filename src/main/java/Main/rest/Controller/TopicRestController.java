package Main.rest.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import Main.Validation.RegisterFormValidator;
import Main.Validation.TopicFormValidator;
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
	private TopicFormValidator validator;
	@Autowired
	private UserRepository userRepo;
	@Autowired TopicRepository topicRepo;
	private TopicService topicService;
	@Autowired
	public void setTopicService(TopicService topicService) {
		this.topicService = topicService;
	}
	private UserService userService;
		@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
		/**
		 * a function that gives us the appearance of list topics
		 * 
		 * @return we return the look of the list of topics
		 */
		@GetMapping("/user/topics")
		public ResponseEntity<List<Topic>> topicRest() {
			log.info("getting all topics");
			List<Topic> topics=topicService.findAlltopics();
			if(topics==null || topics.isEmpty()) {
				return new ResponseEntity<List<Topic>>(HttpStatus.NOT_FOUND);
			}
			else return new ResponseEntity<List<Topic>>(topics,HttpStatus.OK);
			
		}
		/**    Displaying a specific topic
		 * @param topictitle is used to find a specific topic
		 * @return We return information about http status: OK or NOT FOUND
		 *  */
		@GetMapping("/user/topics/{topictitle}")
		public ResponseEntity<Topic> topic(@PathVariable("topictitle") String topictitle) {
		Topic topic=topicService.findByTitle(topictitle);
		if(topic==null) {
			return new ResponseEntity<Topic>(HttpStatus.NOT_FOUND);
		}
		else return new ResponseEntity<Topic>(topic ,HttpStatus.OK);
		
		}
		
		
		@PostMapping("/user/topics/{id}/createtopic")
		public ResponseEntity<Void> createTopicPost(@RequestBody TopicForm topicF,
				@PathVariable Integer id, BindingResult result,BindingResult result2, UriComponentsBuilder ucBuilder)
		{
			Topic created = topicF.createTopica();
			User topicAuthor=userService.getUserById(id);
			if(topicAuthor==null) {
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			}
			created.setAuthor(topicAuthor);
			Post firstPost = new Post();
			firstPost.setAuthor(topicAuthor);
			firstPost.setContent(topicF.getFirstPostContent());
		
		created.getPosts().add(firstPost);
		validator.validateTopic(created,result2);
		if(result2.hasErrors()) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		else {
		topicService.saveTopic(created);
		HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/users//topics/{id}").buildAndExpand(created.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		
		}}
	
		//OK
		@PutMapping("/user/topics/{id}/{topictitle}")
		public ResponseEntity<Void> addPost(@PathVariable Integer id, @PathVariable("topictitle") String topictitle,
				@RequestBody PostForm form,BindingResult result,BindingResult result2, UriComponentsBuilder ucBuilder)
		{
			Post post = form.createPost();

			User postAuthor = userService.getUserById(id);
if(postAuthor==null) {
	return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
}
			post.setAuthor(postAuthor);
			validator.validatePost(post,result);
			if(result.hasErrors()) {
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			}
			
			Topic topic = topicService.findByTitle(topictitle);
			if(topic==null) {
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			}
			topic.getPosts().add(post);
			validator.validateTopic(topic,result);
			if(result.hasErrors()) {
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			}
			topicService.updateTopic(topic);
			HttpHeaders headers = new HttpHeaders();
	        headers.setLocation(ucBuilder.path("/users//topics/{id}").buildAndExpand(topic.getId()).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
			
			
			
		}}
