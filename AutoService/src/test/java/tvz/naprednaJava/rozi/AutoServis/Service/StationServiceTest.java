package tvz.naprednaJava.rozi.AutoServis.Service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

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

import tvz.naprednaJava.rozi.AutoServis.enums.Status;
import tvz.naprednaJava.rozi.AutoServis.model.Station;
import tvz.naprednaJava.rozi.AutoServis.model.User;
import tvz.naprednaJava.rozi.AutoServis.repository.StationRepository;
import tvz.naprednaJava.rozi.AutoServis.service.StationService;
import tvz.naprednaJava.rozi.AutoServis.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StationServiceTest {

	@Mock
	StationRepository stationRepositoryMock;

	@Mock
	UserService userServiceMock;

	@InjectMocks
	StationService stationService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetById() {
		Station station = new Station();
		station.setName("Gornjogradska");

		when(stationRepositoryMock.getOne(any(Long.class))).thenReturn(station);

		Station stationNew = stationService.getById(1L);

		assertEquals(stationNew.getName(), station.getName());
	}

	@Test
	public void testGetActiveByName() {
		Station station = new Station();
		station.setName("Gornjogradska");

		when(stationRepositoryMock.findByNameAndStatus(any(String.class), any(Status.class))).thenReturn(station);

		Station stationNew = stationService.getActiveByName("gornjogradska");

		assertEquals(stationNew.getName(), station.getName());
	}

	@Test
	public void testGetActiveByAddress() {
		Station station = new Station();
		station.setAddress("Domani 6");

		when(stationRepositoryMock.findByAddressAndStatus(any(String.class), any(Status.class))).thenReturn(station);

		Station stationNew = stationService.getActiveByAddress("test");

		assertEquals(stationNew.getAddress(), station.getAddress());
	}

	@Test
	public void testGetActiveByGeolocation() {
		Station station = new Station();
		station.setGeolocation("12.1");

		when(stationRepositoryMock.findByGeolocationAndStatus(any(String.class), any(Status.class))).thenReturn(station);

		Station stationNew = stationService.getActiveByGeolocation("4.2");

		assertEquals(stationNew.getGeolocation(), station.getGeolocation());
	}

	@Test
	public void getByManager() {
		Station station = new Station();
		station.setName("Gornjogradska");

		when(stationRepositoryMock.findByManager(any(User.class))).thenReturn(station);

		Station stationNew = stationService.getByManager(userServiceMock.getById(3L));

		assertEquals(stationNew.getGeolocation(), station.getGeolocation());
	}

	@Test
	public void getAll() {
		Station station = new Station();
		station.setName("Gornjogradska");

		List<Station> stationList = new ArrayList<>();
		stationList.add(station);

		when(stationRepositoryMock.findAll()).thenReturn(stationList);

		Collection<Station> stationListNew = stationService.getAll();

		assertEquals(stationListNew, stationList);
	}

	@Test
	public void getAllActive() {
		Station station = new Station();
		station.setName("Gornjogradska");
		station.setStatus(Status.ACTIVE);

		List<Station> stationList = new ArrayList<>();
		stationList.add(station);

		when(stationRepositoryMock.findAllByStatus(any(Status.class))).thenReturn(stationList);

		Collection<Station> stationListNew = stationService.getAllActive();

		assertEquals(stationListNew, stationList);
	}

	@Test
	public void create() {
		Station station = new Station();
		station.setName("Gornjogradska");
		when(stationRepositoryMock.saveAndFlush(any(Station.class))).thenReturn(station);

		Station station1 = new Station();
		station1.setName("Gornjogradska");
		Station stationNew = stationService.create(station1);

		assertEquals(stationNew.getName(), station.getName());
	}

	@Test
	public void update() {
		Station station = new Station();
		station.setName("Gornjogradska");
		when(stationRepositoryMock.saveAndFlush(any(Station.class))).thenReturn(station);

		station.setAddress("Ilica 50");
		Station stationNew = stationService.update(station);

		assertEquals(stationNew.getName(), station.getName());
	}
}
