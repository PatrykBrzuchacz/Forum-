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

	public User addWithDefaultRole(User user) {
		UserRole role = new UserRole(1,"ROLE_USER");
		roleRepository.save(role);
		UserRole defaultRole = roleRepository.findByRole(DEFAULT_ROLE);
		user.getRoles().add(defaultRole);
		return userRepository.save(user);
	}

	@Override
	public List<User> findAll(){
		return userRepository.findAll();

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


public User getUserById(Integer id) {
	return userRepository.getUserById(id);
}


public User update(User user) {
User us= getUserById(user.getId());
us.update(user.getFirstName(),user.getLastName(),user.getEmail(),user.getPassword());
return us;
}



	}