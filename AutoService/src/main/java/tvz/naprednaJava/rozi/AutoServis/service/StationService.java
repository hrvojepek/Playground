package tvz.naprednaJava.rozi.AutoServis.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tvz.naprednaJava.rozi.AutoServis.enums.Status;
import tvz.naprednaJava.rozi.AutoServis.model.Station;
import tvz.naprednaJava.rozi.AutoServis.model.User;
import tvz.naprednaJava.rozi.AutoServis.repository.StationRepository;

@Service
@Transactional
public class StationService {

	@Autowired
	private StationRepository stationRepository;

	public Station getById(Long id) {
		return stationRepository.getOne(id);
	}

	public Station getActiveByName(String name) {
		return stationRepository.findByNameAndStatus(name, Status.ACTIVE);
	}

	public Station getActiveByAddress(String address) {
		return stationRepository.findByAddressAndStatus(address, Status.ACTIVE);
	}

	public Station getActiveByGeolocation(String geolocation) {
		return stationRepository.findByGeolocationAndStatus(geolocation, Status.ACTIVE);
	}

	public Station getByManager(User user) {
		return stationRepository.findByManager(user);
	}

	public Collection<Station> getAll() {
		return stationRepository.findAll();
	}

	public Collection<Station> getAllActive() {
		return stationRepository.findAllByStatus(Status.ACTIVE);
	}

	@Transactional(readOnly = false)
	public Station create(Station station) {
		return stationRepository.saveAndFlush(station);
	}

	@Transactional(readOnly = false)
	public Station update(Station station) {
		return stationRepository.saveAndFlush(station);
	}

	@Transactional(readOnly = false)
	public boolean delete(Station station) {
		station.setStatus(Status.DELETED);
		if (stationRepository.saveAndFlush(station) != null) {
			return true;
		}
		return false;
	}
}
