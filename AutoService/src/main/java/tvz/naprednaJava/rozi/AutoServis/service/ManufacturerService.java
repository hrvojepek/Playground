package tvz.naprednaJava.rozi.AutoServis.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tvz.naprednaJava.rozi.AutoServis.enums.Status;
import tvz.naprednaJava.rozi.AutoServis.model.Manufacturer;
import tvz.naprednaJava.rozi.AutoServis.repository.ManufacturerRepository;

@Service
@Transactional
public class ManufacturerService {

	@Autowired
	private ManufacturerRepository manufacturerRepository;

	public Manufacturer getById(Long id) {
		return manufacturerRepository.getOne(id);
	}

	public Manufacturer getByName(String name) {
		return manufacturerRepository.findByName(name);
	}

	public Manufacturer getActiveByName(String name) {
		return manufacturerRepository.findByNameAndStatus(name, Status.ACTIVE);
	}

	public Collection<Manufacturer> getAllActive() {
		return manufacturerRepository.findAllByStatus(Status.ACTIVE);
	}

	@Transactional(readOnly = false)
	public Manufacturer create(Manufacturer manufacturer) {
		return manufacturerRepository.saveAndFlush(manufacturer);
	}

	@Transactional(readOnly = false)
	public Manufacturer update(Manufacturer manufacturer) {
		return manufacturerRepository.saveAndFlush(manufacturer);
	}

	@Transactional(readOnly = false)
	public boolean delete(Manufacturer manufacturer) {
		//TODO što se događa kada "obrišemo" proizvođača koji ima puno proizvoda? Sve proizvode isto brišemo?
		Manufacturer manufacturerDb = manufacturerRepository.findOne(manufacturer.getId());
		manufacturerDb.setStatus(Status.DELETED);
		if (manufacturerRepository.saveAndFlush(manufacturerDb) != null) {
			return true;
		}
		return false;
	}
}
