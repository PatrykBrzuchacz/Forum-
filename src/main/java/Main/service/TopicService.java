package Main.service;

import java.util.ArrayList;
import java.util.List;

import Main.model.Topic;
import Main.model.User;

public interface TopicService {
	List<Topic> findAlltopics();
void saveTopic(Topic topic);
Topic findByTitle(String title);
boolean updateTopic(Topic topic);
List<Topic> findTopicByAuthor(User user);
}
