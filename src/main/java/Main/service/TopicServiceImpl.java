package Main.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Main.model.Topic;
import Main.repository.PostRepository;
import Main.repository.TopicRepository;
import Main.repository.UserRepository;
import Main.repository.UserRoleRepository;

@Service
public class TopicServiceImpl implements TopicService{
	
	@Autowired
	private TopicRepository topicRepository;

	
	@Override
	public List<Topic> findAlltopics() {
	return  topicRepository.findAll();
	}

	@Override
	public void saveTopic(Topic topic) {
topicRepository.save(topic);
		
	}

	@Override
	public Topic findByTitle(String title) {
		return topicRepository.findByTitle(title);
	}
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

}
