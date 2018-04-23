package Main.service;

import java.util.ArrayList;
import java.util.List;

import Main.model.Topic;

public interface TopicService {
	List<Topic> findAlltopics();
void saveTopic(Topic topic);
Topic findByTitle(String title);
boolean updateTopic(Topic topic);
}
