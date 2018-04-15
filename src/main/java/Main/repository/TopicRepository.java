package Main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Main.model.Topic;


public interface TopicRepository extends JpaRepository<Topic, Integer> {

}
