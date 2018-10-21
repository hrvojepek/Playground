package tvz.naprednaJava.rozi.AutoServis.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tvz.naprednaJava.rozi.AutoServis.enums.Status;
import tvz.naprednaJava.rozi.AutoServis.model.Receipt;
import tvz.naprednaJava.rozi.AutoServis.model.User;
import tvz.naprednaJava.rozi.AutoServis.repository.ReceiptRepository;

@Service
@Transactional
public class ReceiptService {

	@Autowired
	private ReceiptRepository receiptRepository;

	public Receipt getById(Long id) {
		return receiptRepository.findOne(id);
	}

	public Collection<Receipt> getAll() {
		return receiptRepository.findAll();
	}

	public Collection<Receipt> getAllByCustomer(User customer) {
		return receiptRepository.findAllByCustomer(customer);
	}

	@Transactional(readOnly = false)
	public Receipt create(Receipt receipt) {
		return receiptRepository.saveAndFlush(receipt);
	}

	@Transactional(readOnly = false)
	public Receipt update(Receipt receipt) {
		return receiptRepository.saveAndFlush(receipt);
	}

	@Transactional(readOnly = false)
	public boolean delete(Receipt receipt) {
		receipt.setStatus(Status.DELETED);
		if (receiptRepository.saveAndFlush(receipt) != null) {
			return true;
		}
		return false;
	}
}
