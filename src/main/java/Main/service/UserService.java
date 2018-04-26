
package Main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;

import Main.model.Topic;
import Main.model.User;




public interface UserService {
	User addWithDefaultRole(User user);
	List<User> findAll();
	User findByEmail(String email);
User saveUser(User user);
User getUserById(Integer id);
void addDetails(User user);
	void deleteUser(int id);
	User update(User user);

}
