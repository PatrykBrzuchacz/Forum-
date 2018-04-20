package Main.service;

import java.util.ArrayList;

import Main.model.Topic;

public interface TopicService {
	ArrayList<Topic> findAlltopics();
void saveTopic(Topic topic);
Topic findByTitle(String title);
boolean updateTopic(Topic topic);
}
