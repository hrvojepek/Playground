package tvz.naprednaJava.rozi.AutoServis.repository;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import tvz.naprednaJava.rozi.AutoServis.enums.ReservationStatus;
import tvz.naprednaJava.rozi.AutoServis.model.Reservation;
import tvz.naprednaJava.rozi.AutoServis.model.Station;
import tvz.naprednaJava.rozi.AutoServis.model.User;

/**
 * Created by Hrvoje
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	Collection<Reservation> findAllByReservationStatus(ReservationStatus reservationStatus);

	Collection<Reservation> findAllByReservationStatusAndEstimatedRepairEndDateBetween(ReservationStatus reservationStatus,
			LocalDateTime EstimatedRepairEndDate1, LocalDateTime EstimatedRepairEndDate2);

	Collection<Reservation> findAllByCustomer(User customer);

	Collection<Reservation> findAllByStationAndRepairStartDateBetween(Station serviceStation, LocalDateTime repairStartDate1,
			LocalDateTime repairStartDate2);

}
