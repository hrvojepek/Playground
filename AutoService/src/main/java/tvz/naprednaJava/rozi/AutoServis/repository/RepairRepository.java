package tvz.naprednaJava.rozi.AutoServis.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import tvz.naprednaJava.rozi.AutoServis.enums.Status;
import tvz.naprednaJava.rozi.AutoServis.model.Repair;

public interface RepairRepository extends JpaRepository<Repair, Long> {

	Repair findByNameAndStatus(String name, Status status);

	Collection<Repair> findAllByStatus(Status status);
}
