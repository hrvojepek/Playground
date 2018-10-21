package tvz.naprednaJava.rozi.AutoServis.repository;

import java.math.BigDecimal;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import tvz.naprednaJava.rozi.AutoServis.enums.Status;
import tvz.naprednaJava.rozi.AutoServis.model.Item;
import tvz.naprednaJava.rozi.AutoServis.model.Manufacturer;

public interface ItemRepository extends JpaRepository<Item, Long> {

	Item findByName(String name);
	
	Item findByNameAndStatus(String name, Status status);

	Collection<Item> findByPricePerUnitBetween(BigDecimal price1, BigDecimal price2);

	Collection<Item> findAllByStatus(Status status);

	Collection<Item> findAllByStatusAndManufacturer(Status status, Manufacturer manufacturer);
}
