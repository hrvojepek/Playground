package tvz.naprednaJava.rozi.AutoServis.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tvz.naprednaJava.rozi.AutoServis.enums.UserStatus;
import tvz.naprednaJava.rozi.AutoServis.model.Role;
import tvz.naprednaJava.rozi.AutoServis.model.User;
import tvz.naprednaJava.rozi.AutoServis.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User getById(Long id) {
		return userRepository.findOne(id);
	}

	public User getByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public User getByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public Collection<User> getAllWithRole(Role role) {
		return userRepository.findAllByRoleAndStatusNot(role, UserStatus.DELETED);
	}

	@Transactional(readOnly = false)
	public User create(User newUser) {
		return userRepository.saveAndFlush(newUser);
	}

	@Transactional(readOnly = false)
	public User update(User updatedUser) {
		return userRepository.saveAndFlush(updatedUser);
	}

	@Transactional(readOnly = false)
	public boolean delete(User user) {
		user.setStatus(UserStatus.DEACTIVATED);
		if (userRepository.saveAndFlush(user) != null) {
			return true;
		}
		return true;
	}
}
