package tvz.naprednaJava.rozi.AutoServis.Service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tvz.naprednaJava.rozi.AutoServis.enums.ReservationStatus;
import tvz.naprednaJava.rozi.AutoServis.model.Reservation;
import tvz.naprednaJava.rozi.AutoServis.model.Station;
import tvz.naprednaJava.rozi.AutoServis.model.User;
import tvz.naprednaJava.rozi.AutoServis.repository.ReservationRepository;
import tvz.naprednaJava.rozi.AutoServis.service.ReservationService;
import tvz.naprednaJava.rozi.AutoServis.service.StationService;
import tvz.naprednaJava.rozi.AutoServis.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReservationServiceTest {

	@InjectMocks
	private ReservationService reservationService;

	@Mock
	private ReservationRepository reservationRepositoryMock;
	
	@Mock
	private UserService userServiceMock;
	
	@Mock
	private StationService stationServiceMock;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetById() {
		Reservation reservation = new Reservation();
		when(reservationRepositoryMock.findOne(any(Long.class))).thenReturn(reservation);
		Reservation reservationNew = reservationService.getById(1L);
		assertEquals(reservationNew, reservation);
	}

	@Test
	public void testGetAll() {
		Reservation reservation = new Reservation();
		List<Reservation> reservations = new ArrayList<>();
		reservations.add(reservation);

		when(reservationRepositoryMock.findAll()).thenReturn(reservations);
		
		Collection<Reservation> reservationsNew = reservationService.getAll();

		assertEquals(reservationsNew, reservations);
	}

	@Test
	public void testGetAllWithStatus() {
		Reservation reservation = new Reservation();
		List<Reservation> reservations = new ArrayList<>();
		reservations.add(reservation);

		when(reservationRepositoryMock.findAllByReservationStatus(any(ReservationStatus.class))).thenReturn(reservations);
		
		Collection<Reservation> reservationsNew = reservationService.getAllWithStatus(ReservationStatus.IN_PROGRESS);

		assertEquals(reservationsNew, reservations);
	}

	@Test
	public void testGetAllByCustomer() {
		Reservation reservation = new Reservation();
		List<Reservation> reservations = new ArrayList<>();
		reservations.add(reservation);

		when(reservationRepositoryMock.findAllByCustomer(any(User.class))).thenReturn(reservations);
		
		Collection<Reservation> reservationsNew = reservationService.getAllByCustomer(userServiceMock.getById(1L));

		assertEquals(reservationsNew, reservations);
	}

	@Test
	public void testGetAllByServiceStationAndRepairStartDateBetween() {
		Reservation reservation = new Reservation();
		List<Reservation> reservations = new ArrayList<>();
		reservations.add(reservation);

		when(reservationRepositoryMock.findAllByStationAndRepairStartDateBetween(any(Station.class),any(LocalDateTime.class),any(LocalDateTime.class))).thenReturn(reservations);
		
		Collection<Reservation> reservationsNew = reservationService.getAllByServiceStationAndRepairStartDateBetween(stationServiceMock.getById(1L), LocalDateTime.now(), LocalDateTime.now());

		assertEquals(reservationsNew, reservations);
	}
	
	@Test
	public void testCreate() {
		Reservation reservation = new Reservation();
		when(reservationRepositoryMock.saveAndFlush(any(Reservation.class))).thenReturn(reservation);
		Reservation reservationNew = reservationService.create(new Reservation());
		assertEquals(reservationNew, reservation);
	}
	
	@Test
	public void testUpdate() {
		Reservation reservation = new Reservation();
		when(reservationRepositoryMock.saveAndFlush(any(Reservation.class))).thenReturn(reservation);
		Reservation reservationNew = reservationService.update(new Reservation());
		assertEquals(reservationNew, reservation);
	}
}
