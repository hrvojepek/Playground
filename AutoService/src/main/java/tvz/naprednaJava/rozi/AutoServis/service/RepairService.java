package tvz.naprednaJava.rozi.AutoServis.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tvz.naprednaJava.rozi.AutoServis.enums.Status;
import tvz.naprednaJava.rozi.AutoServis.model.Repair;
import tvz.naprednaJava.rozi.AutoServis.repository.RepairRepository;

@Service("repairService")
@Transactional
public class RepairService {

	@Autowired
	private RepairRepository repairRepository;

	public Repair getById(Long id) {
		return repairRepository.getOne(id);
	}
	
	public Repair getActiveByName(String name) {
		return repairRepository.findByNameAndStatus(name, Status.ACTIVE);
	}

	public Collection<Repair> getAllActive() {
		return repairRepository.findAllByStatus(Status.ACTIVE);
	}

	@Transactional(readOnly = false)
	public Repair create(Repair repair) {
		return repairRepository.saveAndFlush(repair);
	}

	@Transactional(readOnly = false)
	public Repair update(Repair repair) {
		// TODO implement custom logic
		return repairRepository.saveAndFlush(repair);
	}

	@Transactional(readOnly = false)
	public boolean delete(Repair repair) {
		repair.setStatus(Status.DELETED);
		if (repairRepository.saveAndFlush(repair) != null) {
			return true;
		}
		return false;
	}
}
