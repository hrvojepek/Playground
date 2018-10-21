package tvz.naprednaJava.rozi.AutoServis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tvz.naprednaJava.rozi.AutoServis.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

	Permission findByName(String name);
}
