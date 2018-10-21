package tvz.naprednaJava.rozi.AutoServis.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import tvz.naprednaJava.rozi.AutoServis.enums.Status;
import tvz.naprednaJava.rozi.AutoServis.model.Manufacturer;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

	Manufacturer findByName(String name);

	Manufacturer findByNameAndStatus(String name, Status status);

	Collection<Manufacturer> findAllByStatus(Status status);
}
