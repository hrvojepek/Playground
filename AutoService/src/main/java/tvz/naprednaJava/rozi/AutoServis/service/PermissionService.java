package tvz.naprednaJava.rozi.AutoServis.service;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tvz.naprednaJava.rozi.AutoServis.model.Permission;
import tvz.naprednaJava.rozi.AutoServis.model.Role;
import tvz.naprednaJava.rozi.AutoServis.repository.PermissionRepository;

@Service
@Transactional
public class PermissionService {

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private RoleService roleService;

	public Permission getById(Long id) {
		return permissionRepository.findOne(id);
	}

	public Collection<Permission> getAll() {
		return permissionRepository.findAll();
	}

	public Permission getByName(String name) {
		return permissionRepository.findByName(name);
	}

	@Transactional(readOnly = false)
	public Permission create(Permission permission) {
		permissionRepository.saveAndFlush(permission);
		addPermissionToAdmin(permission);
		return permission;
	}

	@Transactional(readOnly = false)
	public void addPermissionToAdmin(Permission permission) {
		Role adminRole = roleService.getByName("Admin");
		if (adminRole != null) {
			Set<Permission> permissions = (Set<Permission>) adminRole.getPermissions();
			permissions.add(permission);
			roleService.update(adminRole);
		}
	}
}
