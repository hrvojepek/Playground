package tvz.naprednaJava.rozi.AutoServis.service;

import java.math.BigDecimal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tvz.naprednaJava.rozi.AutoServis.enums.Status;
import tvz.naprednaJava.rozi.AutoServis.model.Item;
import tvz.naprednaJava.rozi.AutoServis.model.Manufacturer;
import tvz.naprednaJava.rozi.AutoServis.repository.ItemRepository;

@Service
@Transactional
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	public Item getById(Long id) {
		return itemRepository.getOne(id);
	}

	public Item getByName(String name) {
		return itemRepository.findByName(name);
	}
	
	public Item getActiveByName(String name) {
		return itemRepository.findByNameAndStatus(name, Status.ACTIVE);
	}

	public Collection<Item> getByPricePerUnitBetween(BigDecimal price1, BigDecimal price2) {
		return itemRepository.findByPricePerUnitBetween(price1, price2);
	}

	public Collection<Item> getAll() {
		return itemRepository.findAll();
	}

	public Collection<Item> getAllActive() {
		return itemRepository.findAllByStatus(Status.ACTIVE);
	}

	public Collection<Item> getAllActiveByManufacturer(Manufacturer manufacturer) {
		return itemRepository.findAllByStatusAndManufacturer(Status.ACTIVE, manufacturer);
	}

	@Transactional(readOnly = false)
	public Item create(Item item) {
		return itemRepository.saveAndFlush(item);
	}

	@Transactional(readOnly = false)
	public Item update(Item item) {
		// TODO implement custom logic
		return itemRepository.saveAndFlush(item);
	}

	@Transactional(readOnly = false)
	public boolean delete(Item item) {
		item.setStatus(Status.DELETED);
		if (itemRepository.saveAndFlush(item) != null) {
			return true;
		}
		return false;
	}
}
