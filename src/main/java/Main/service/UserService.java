
package Main.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;

import Main.model.User;




public interface UserService {
	void addWithDefaultRole(User user);
	ArrayList<User> findAll();
	User findByEmail(String email);
User saveUser(User user);
User getUserById(Integer id);
void addDetails(User user);
	public void deleteUser(int id);

}
