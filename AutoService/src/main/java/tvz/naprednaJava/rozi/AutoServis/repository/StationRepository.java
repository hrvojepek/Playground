package tvz.naprednaJava.rozi.AutoServis.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import tvz.naprednaJava.rozi.AutoServis.enums.Status;
import tvz.naprednaJava.rozi.AutoServis.model.Station;
import tvz.naprednaJava.rozi.AutoServis.model.User;

public interface StationRepository extends JpaRepository<Station, Long> {

	Station findByNameAndStatus(String name, Status status);

	Station findByAddressAndStatus(String address, Status status);

	Station findByGeolocationAndStatus(String geolocation, Status status);

	Station findByManager(User user);

	Collection<Station> findAllByStatus(Status status);
}
