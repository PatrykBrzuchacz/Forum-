package Main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Main.model.User;
import Main.model.UserRole;
import Main.repository.UserRepository;
import Main.repository.UserRoleRepository;
import dao.UserDao;

@Service
public class UserServiceImpl implements UserService{

	private static final String DEFAULT_ROLE = "ROLE_USER";
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserRoleRepository roleRepository;



	public void addWithDefaultRole(User user) {
		UserRole defaultRole = roleRepository.findByRole(DEFAULT_ROLE);
		user.getRoles().add(defaultRole);
		userRepository.save(user);
	}

	@Override

	public ArrayList<User> findAll(){
		return (ArrayList<User>) userRepository.findAll();

}
	public User findByEmail(String email) {
	return userRepository.findByEmail(email);
	}

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

public void deleteUser(int id) {
	userRepository.deleteById(id);
	
}
public void addDetails(User user) {
	userRepository.save(user);
}

@Override
public User getUserById(Integer id) {
	return userRepository.getUserById(id);
}



	}