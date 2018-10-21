package tvz.naprednaJava.rozi.AutoServis.Service;

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
import tvz.naprednaJava.rozi.AutoServis.model.Item;
import tvz.naprednaJava.rozi.AutoServis.model.Manufacturer;
import tvz.naprednaJava.rozi.AutoServis.repository.ItemRepository;
import tvz.naprednaJava.rozi.AutoServis.service.ItemService;
import tvz.naprednaJava.rozi.AutoServis.service.ManufacturerService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ItemServiceTest {

	@Mock
	ItemRepository itemRepositoryMock;

	@Mock
	ManufacturerService manufacturerServiceMock;

	@InjectMocks
	ItemService itemService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetById() {
		Item item = new Item();
		item.setName("Test");

		when(itemRepositoryMock.getOne(any(Long.class))).thenReturn(item);

		Item itemNew = itemService.getById(1L);

		assertEquals(itemNew.getName(), item.getName());
	}

	@Test
	public void testGetByName() {
		Item item = new Item();
		item.setName("Test");

		when(itemRepositoryMock.findByName(any(String.class))).thenReturn(item);

		Item itemNew = itemService.getByName("test");

		assertEquals(itemNew.getName(), item.getName());
	}

	@Test
	public void testGetActiveByName() {
		Item item = new Item();
		item.setName("Test");

		when(itemRepositoryMock.findByNameAndStatus(any(String.class), any(Status.class))).thenReturn(item);

		Item itemNew = itemService.getActiveByName("test");

		assertEquals(itemNew.getName(), item.getName());
	}

	@Test
	public void testGetByPricePerUnitBetween() {

		Item item = new Item();
		item.setName("Test");
		List<Item> items = new ArrayList<>();
		items.add(item);
		when(itemRepositoryMock.findByPricePerUnitBetween(any(BigDecimal.class), any(BigDecimal.class))).thenReturn(items);

		Collection<Item> itemsNew = itemService.getByPricePerUnitBetween(BigDecimal.ONE, BigDecimal.TEN);

		assertEquals(itemsNew.size(), 1);
	}

	@Test
	public void getAll() {
		Item item = new Item();
		item.setName("Test");

		List<Item> itemList = new ArrayList<>();
		itemList.add(item);

		when(itemRepositoryMock.findAll()).thenReturn(itemList);

		Collection<Item> itemListNew = itemService.getAll();

		assertEquals(itemListNew, itemList);
	}

	@Test
	public void getAllActive() {
		Item item = new Item();
		item.setName("Test");

		List<Item> itemList = new ArrayList<>();
		itemList.add(item);

		when(itemRepositoryMock.findAllByStatus(any(Status.class))).thenReturn(itemList);

		Collection<Item> itemListNew = itemService.getAllActive();

		assertEquals(itemListNew, itemList);
	}

	@Test
	public void getAllActiveByManufacturer() {
		Item item = new Item();
		item.setName("Test");
		List<Item> itemList = new ArrayList<>();
		itemList.add(item);

		when(itemRepositoryMock.findAllByStatusAndManufacturer(any(Status.class), any(Manufacturer.class))).thenReturn(itemList);

		Collection<Item> itemListNew = itemService.getAllActiveByManufacturer(manufacturerServiceMock.getById(1L));

		assertEquals(itemListNew, itemList);
	}

	@Test
	public void create() {
		Item item = new Item();
		item.setName("Test");

		when(itemRepositoryMock.saveAndFlush(any(Item.class))).thenReturn(item);

		item.setDescription("Test test");
		Item itemNew = itemService.create(item);

		assertEquals(itemNew, item);
	}

	@Test
	public void update() {
		Item item = new Item();
		item.setName("Test");

		when(itemRepositoryMock.saveAndFlush(any(Item.class))).thenReturn(item);

		Item item1 = new Item();
		item1.setName("Test267");
		Item itemNew = itemService.update(item1);

		assertEquals(itemNew, item);
	}

	@Test
	public void delete() {
		Item item = new Item();
		item.setName("Test");

		when(itemRepositoryMock.saveAndFlush(any(Item.class))).thenReturn(item);

		item.setDescription("Test test");
		Boolean status = itemService.delete(item);

		assertTrue(status);
	}

	@Test
	public void deleteUnsuccesful() {
		Item item = new Item();
		item.setName("Test");

		when(itemRepositoryMock.saveAndFlush(any(Item.class))).thenReturn(null);

		item.setDescription("Test test");
		Boolean status = itemService.delete(item);

		assertFalse(status);
	}
}
