package tvz.naprednaJava.rozi.AutoServis.Service;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
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
import tvz.naprednaJava.rozi.AutoServis.model.Manufacturer;
import tvz.naprednaJava.rozi.AutoServis.repository.ManufacturerRepository;
import tvz.naprednaJava.rozi.AutoServis.service.ManufacturerService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ManufacturerServiceTest {

	@Mock
	ManufacturerRepository manufacturerRepositoryMock;

	@InjectMocks
	ManufacturerService manufacturerService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetById() {
		Manufacturer manufacturer = new Manufacturer();
		manufacturer.setName("Vedrana");

		when(manufacturerRepositoryMock.getOne(any(Long.class))).thenReturn(manufacturer);

		Manufacturer manufacturerNew = manufacturerService.getById(1L);

		assertEquals(manufacturerNew.getName(), manufacturer.getName());
	}

	@Test
	public void testGetByName() {
		Manufacturer manufacturer = new Manufacturer();
		manufacturer.setName("Vedrana");

		when(manufacturerRepositoryMock.findByName(any(String.class))).thenReturn(manufacturer);

		Manufacturer manufacturerNew = manufacturerService.getByName("test");

		assertEquals(manufacturerNew.getName(), manufacturer.getName());
	}

	@Test
	public void testGetActiveByName() {
		Manufacturer manufacturer = new Manufacturer();
		manufacturer.setName("Vedrana");

		when(manufacturerRepositoryMock.findByNameAndStatus(any(String.class), any(Status.class))).thenReturn(manufacturer);

		Manufacturer manufacturerNew = manufacturerService.getActiveByName("test");

		assertEquals(manufacturerNew.getName(), manufacturer.getName());
	}

	@Test
	public void getAllActive() {
		Manufacturer manufacturer = new Manufacturer();
		manufacturer.setName("Vedrana");
		List<Manufacturer> manufacturerList = new ArrayList<>();

		when(manufacturerRepositoryMock.findAllByStatus(any(Status.class))).thenReturn(manufacturerList);

		Collection<Manufacturer> manufacturerListNew = manufacturerService.getAllActive();

		assertEquals(manufacturerListNew, manufacturerList);
	}

	@Test
	public void create() {
		Manufacturer manufacturer = new Manufacturer();
		manufacturer.setName("Vedrana");

		when(manufacturerRepositoryMock.saveAndFlush(any(Manufacturer.class))).thenReturn(manufacturer);

		Manufacturer manufacturerNew = manufacturerService.create(new Manufacturer("Samsung"));

		assertEquals(manufacturerNew.getName(), manufacturer.getName());
	}

	@Test
	public void update() {
		Manufacturer manufacturer = new Manufacturer();
		manufacturer.setName("Vedrana");

		when(manufacturerRepositoryMock.saveAndFlush(any(Manufacturer.class))).thenReturn(manufacturer);

		Manufacturer manufacturerNew = manufacturerService.update(new Manufacturer("Vedrana"));

		assertEquals(manufacturerNew.getName(), manufacturer.getName());
	}

	@Test
	public void delete() {
		Manufacturer manufacturer = new Manufacturer();
		manufacturer.setName("Vedrana");
		when(manufacturerRepositoryMock.findOne(any(Long.class))).thenReturn(manufacturer);
		when(manufacturerRepositoryMock.saveAndFlush(any(Manufacturer.class))).thenReturn(manufacturer);

		Boolean status = manufacturerService.delete(manufacturer);

		assertTrue(status);
	}

	@Test
	public void deleteUnsuccesful() {
		Manufacturer manufacturer = new Manufacturer();
		manufacturer.setName("Vedrana");

		when(manufacturerRepositoryMock.findOne(any(Long.class))).thenReturn(manufacturer);
		when(manufacturerRepositoryMock.saveAndFlush(any(Manufacturer.class))).thenReturn(null);

		Boolean status = manufacturerService.delete(manufacturer);

		assertFalse(status);
	}
}
