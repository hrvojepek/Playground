package tvz.naprednaJava.rozi.AutoServis.service;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tvz.naprednaJava.rozi.AutoServis.model.Permission;
import tvz.naprednaJava.rozi.AutoServis.model.Role;
import tvz.naprednaJava.rozi.AutoServis.model.User;
import tvz.naprednaJava.rozi.AutoServis.repository.RoleRepository;

@Service
@Transactional
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private PermissionService permissionService;

	public Role getById(Long id) {
		return roleRepository.getOne(id);
	}

	public Collection<Role> getAll() {
		return roleRepository.findAll();
	}

	public Role getByName(String name) {
		return roleRepository.findByName(name);
	}

	@Transactional(readOnly = false)
	public Role create(Role role) {
		roleRepository.saveAndFlush(role);
		if (role.getUsers() != null) {
			addRoleToUsers(role);
		}
		return role;
	}

	@Transactional(readOnly = false)
	public Role update(Role role) {
		adminRoleChecks(role);
		roleRepository.saveAndFlush(role);
		if (role.getUsers() != null) {
			addRoleToUsers(role);
		}
		return role;
	}

	private void adminRoleChecks(Role role) {
		// Admin role needs to have all permissions and root user
		if (role.getName() != null && role.getName().equals("Admin")) {
			if (role.getPermissions() != null && role.getPermissions().size() != permissionService.getAll().size()) {
				List<Permission> permissions = (List<Permission>) permissionService.getAll();
				role.setPermissions(new LinkedHashSet<Permission>(permissions));
			}
			User rootUser = userService.getByUsername("root");
			if (role.getUsers() == null) {
				Set<User> users = new LinkedHashSet<>();
				users.add(rootUser);
				role.setUsers(users);
			} else if (!role.getUsers().contains(rootUser)) {
				Set<User> users = role.getUsers();
				users.add(rootUser);
				role.setUsers(users);
			}

		}
	}

	private void addRoleToUsers(Role role) {
		for (User user : role.getUsers()) {
			user.setRole(role);
			userService.update(user);
		}
	}
}
