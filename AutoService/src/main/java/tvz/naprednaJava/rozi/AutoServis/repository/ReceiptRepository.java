package tvz.naprednaJava.rozi.AutoServis.repository;

import java.math.BigDecimal;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import tvz.naprednaJava.rozi.AutoServis.model.Receipt;
import tvz.naprednaJava.rozi.AutoServis.model.User;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

	Collection<Receipt> findAllByCustomer(User customer);

	Collection<Receipt> findAllByTotalLessThan(BigDecimal total);
}
