package tvz.naprednaJava.rozi.AutoServis.service;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tvz.naprednaJava.rozi.AutoServis.enums.ReservationStatus;
import tvz.naprednaJava.rozi.AutoServis.model.Reservation;
import tvz.naprednaJava.rozi.AutoServis.model.Station;
import tvz.naprednaJava.rozi.AutoServis.model.User;
import tvz.naprednaJava.rozi.AutoServis.repository.ReservationRepository;

@Service
@Transactional
public class ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;

	public Reservation getById(Long id) {
		return reservationRepository.findOne(id);
	}

	public Collection<Reservation> getAll() {
		return (Collection<Reservation>) reservationRepository.findAll();
	}

	public Collection<Reservation> getAllWithStatus(ReservationStatus reservationStatus) {
		return reservationRepository.findAllByReservationStatus(reservationStatus);
	}

	public Collection<Reservation> getAllByCustomer(User customer) {
		return reservationRepository.findAllByCustomer(customer);
	}

	public Collection<Reservation> getAllByServiceStationAndRepairStartDateBetween(Station serviceStation, LocalDateTime repairStartDate1,
			LocalDateTime repairStartDate2) {
		return reservationRepository.findAllByStationAndRepairStartDateBetween(serviceStation, repairStartDate1, repairStartDate2);
	}

	@Transactional(readOnly = false)
	public Reservation create(Reservation reservation) {
		return reservationRepository.saveAndFlush(reservation);
	}

	@Transactional(readOnly = false)
	public Reservation update(Reservation reservation) {
		return reservationRepository.saveAndFlush(reservation);
	}

	@Transactional(readOnly = false)
	public boolean delete(Reservation reservation) {
		reservation.setReservationStatus(ReservationStatus.DELETED);
		if (reservationRepository.saveAndFlush(reservation) != null) {
			return true;
		}
		return false;
	}
}
