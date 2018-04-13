package Main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Main.model.User;


public interface UserRepository extends JpaRepository<User, Integer> {
	User getUserById(Integer id);
	User findByEmail(String email);

}