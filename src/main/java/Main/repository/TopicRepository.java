package Main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Main.model.Topic;
import Main.model.User;


public interface TopicRepository extends JpaRepository<Topic, Integer> {

	Topic findByTitle(String title);
List<Topic> findTopicByAuthor(User user);
}
