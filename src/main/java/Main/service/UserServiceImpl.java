package Main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	@Autowired
	private PasswordEncoder passwordEncoder;
	public UserServiceImpl(UserRepository repository) {
		userRepository=repository;
	}

	public User addWithDefaultRole(User user) {
		UserRole role = new UserRole(2,"ROLE_USER");
		roleRepository.save(role);
		UserRole defaultRole = roleRepository.findByRole(DEFAULT_ROLE);
		user.getRoles().add(defaultRole);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

/** 
 * finding all Users 
 * @return list of users
 */
	@Override
	public List<User> findAll(){
		return userRepository.findAll();

}
	/** find user by email
	 * @return found user
	 */
	public User findByEmail(String email) {
	return userRepository.findByEmail(email);
	}
/**
 * saving user without default role
 */
	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	/**
	 * deleting user by id
	 * 
	 */
public void deleteUser(int id) {
	userRepository.deleteById(id);
	
}
/**
 * adding details to user
 * saving him
 */
public void addDetails(User user) {
	userRepository.save(user);
}

/**
 * getting user by id
 * @return found user by id
 */
public User getUserById(Integer id) {
	return userRepository.getUserById(id);
}

/** 
 * Update user
 * getting user by id
 * updating his data
 * @return updated user
 */
public User update(User user) {
User us= getUserById(user.getId());
us.update(user.getFirstName(),user.getLastName(),user.getEmail(),user.getPassword());
return us;
}



	}