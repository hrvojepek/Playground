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

import tvz.naprednaJava.rozi.AutoServis.model.Receipt;
import tvz.naprednaJava.rozi.AutoServis.model.User;
import tvz.naprednaJava.rozi.AutoServis.repository.ReceiptRepository;
import tvz.naprednaJava.rozi.AutoServis.service.ReceiptService;
import tvz.naprednaJava.rozi.AutoServis.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReceiptServiceTest {

	@InjectMocks
	private ReceiptService receiptService;

	@Mock
	private ReceiptRepository receiptRepositoryMock;

	@Mock
	private UserService userServiceMock;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetById() {
		Receipt receipt = new Receipt();
		when(receiptRepositoryMock.findOne(any(Long.class))).thenReturn(receipt);
		Receipt receiptNew = receiptService.getById(1L);
		assertEquals(receiptNew, receipt);
	}

	@Test
	public void testGetAll() {
		Receipt receipt = new Receipt();
		List<Receipt> receipts = new ArrayList<>();
		receipts.add(receipt);
		when(receiptRepositoryMock.findAll()).thenReturn(receipts);
		Collection<Receipt> receiptsNew = receiptService.getAll();
		assertEquals(receiptsNew, receipts);
	}

	@Test
	public void testGetAllByCustomer() {
		Receipt receipt = new Receipt();
		List<Receipt> receipts = new ArrayList<>();
		receipts.add(receipt);
		when(receiptRepositoryMock.findAllByCustomer(any(User.class))).thenReturn(receipts);
		Collection<Receipt> receiptsNew = receiptService.getAllByCustomer(userServiceMock.getById(1L));
		assertEquals(receiptsNew, receipts);
	}

	@Test
	public void testCreate() {
		Receipt receipt = new Receipt();
		when(receiptRepositoryMock.saveAndFlush(any(Receipt.class))).thenReturn(receipt);
		Receipt receiptNew = receiptService.create(new Receipt());
		assertEquals(receiptNew, receipt);
	}

	@Test
	public void testUpdate() {
		Receipt receipt = new Receipt();
		when(receiptRepositoryMock.saveAndFlush(any(Receipt.class))).thenReturn(receipt);
		Receipt receiptNew = receiptService.update(new Receipt());
		assertEquals(receiptNew, receipt);
	}
}
