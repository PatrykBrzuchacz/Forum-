package Main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Main.model.Post;



public interface PostRepository extends JpaRepository<Post, Integer> {

}
