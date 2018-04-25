package Main.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Main.model.Topic;
import Main.model.User;
import Main.repository.PostRepository;
import Main.repository.TopicRepository;
import Main.repository.UserRepository;
import Main.repository.UserRoleRepository;


@Service
public class TopicServiceImpl implements TopicService{
	
	@Autowired
	private TopicRepository topicRepository;
@Autowired
private UserRepository userRepository;
	/**
	 * finding all topics
	 * @return list of all topics
	 */
	@Override
	public List<Topic> findAlltopics() {
	return  topicRepository.findAll();
	}
/**
 * saving topic
 * 
 */
	@Override
	public void saveTopic(Topic topic) {
topicRepository.save(topic);
		
	}
/**
 * finding topic by title
 * @return returning found topic
 */
	@Override
	public Topic findByTitle(String title) {
		return topicRepository.findByTitle(title);
	}
	/**
	 * updating topic
	 * @return returning true if found, false if not
	 */
	@Override
	public boolean updateTopic(Topic topic) {
		boolean updated = false;
		
		for(Topic t : topicRepository.findAll()) {
			if(topic.getId() == t.getId()) {
				topicRepository.save(topic);
				updated = true;
				break;
			}
		}
		
		return updated;
			
	}
	/**
	 * getting topic by author
	 */
	public List<Topic> findTopicByAuthor(User user) {
		return topicRepository.findTopicByAuthor(user);
		
	
	}
}
