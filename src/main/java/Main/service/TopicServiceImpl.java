package Main.service;

import java.util.ArrayList;

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
	private UserRepository userRepository;
	@Autowired
	private TopicRepository topicRepository;
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public ArrayList<Topic> findAlltopics() {
	return (ArrayList<Topic>) topicRepository.findAll();
	}
	

}
