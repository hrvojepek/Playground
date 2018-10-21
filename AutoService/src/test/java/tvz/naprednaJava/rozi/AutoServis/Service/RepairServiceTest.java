package tvz.naprednaJava.rozi.AutoServis.Service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import tvz.naprednaJava.rozi.AutoServis.model.Repair;
import tvz.naprednaJava.rozi.AutoServis.repository.RepairRepository;
import tvz.naprednaJava.rozi.AutoServis.service.RepairService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RepairServiceTest {

	@Mock
	RepairRepository repairRepositoryMock;

	@InjectMocks
	RepairService repairService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetById() {
		Repair repair = new Repair();
		repair.setName("Test");

		when(repairRepositoryMock.getOne(any(Long.class))).thenReturn(repair);

		Repair repairNew = repairService.getById(1L);

		assertEquals(repairNew.getName(), repair.getName());
	}

	@Test
	public void testGetActiveByName() {
		Repair repair = new Repair();
		repair.setName("Test");

		when(repairRepositoryMock.findByNameAndStatus(any(String.class), any(Status.class))).thenReturn(repair);

		Repair repairNew = repairService.getActiveByName("bla");

		assertEquals(repairNew.getName(), repair.getName());
	}

	@Test
	public void testGetAllActive() {
		List<Repair> repairList = new ArrayList<>();
		Repair repair = new Repair();
		repair.setStatus(Status.ACTIVE);

		repairList.add(repair);

		when(repairRepositoryMock.findAllByStatus(any(Status.class))).thenReturn(repairList);

		List<Repair> repairListNew = (List<Repair>) repairService.getAllActive();

		assertEquals(repairListNew.get(0).getStatus(), repairList.get(0).getStatus());

	}

	@Test
	public void create() {
		Repair repair = new Repair();
		repair.setName("Test");

		when(repairRepositoryMock.saveAndFlush(any(Repair.class))).thenReturn(repair);

		Repair repair1 = new Repair();
		repair1.setName("Test1");
		Repair repairNew = repairService.create(repair1);

		assertEquals(repairNew, repair);
	}

	@Test
	public void update() {
		Repair repair = new Repair();
		repair.setName("Test");

		when(repairRepositoryMock.saveAndFlush(any(Repair.class))).thenReturn(repair);

		repair.setDescription("Test test test");
		Repair repairNew = repairService.update(repair);

		assertEquals(repairNew, repair);
	}
}
