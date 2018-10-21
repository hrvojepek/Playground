package tvz.naprednaJava.rozi.AutoServis.Service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tvz.naprednaJava.rozi.AutoServis.enums.UserStatus;
import tvz.naprednaJava.rozi.AutoServis.model.Role;
import tvz.naprednaJava.rozi.AutoServis.model.User;
import tvz.naprednaJava.rozi.AutoServis.repository.UserRepository;
import tvz.naprednaJava.rozi.AutoServis.service.RoleService;
import tvz.naprednaJava.rozi.AutoServis.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepositoryMock;

	@Mock
	private RoleService roleServiceMock;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetById() {
		User user = new User();
		user.setUsername("Stipe");
		user.setEmail("stipe@tvz.hr");
		user.setPassword("stipePass");

		when(userRepositoryMock.findOne(any(Long.class))).thenReturn(user);

		User userNew = userService.getById(1L);

		assertEquals(userNew, user);
	}

	@Test
	public void testGetByUsername() {
		User user = new User();
		user.setUsername("stipe");
		user.setEmail("stipe@tvz.hr");
		user.setPassword("stipePass");

		when(userRepositoryMock.findByUsername(any(String.class))).thenReturn(user);

		User userNew = userService.getByUsername("stipe");

		assertEquals(userNew, user);
	}

	@Test
	public void testGetByEmail() {
		User user = new User();
		user.setUsername("Stipe");
		user.setEmail("stipe@tvz.hr");
		user.setPassword("stipePass");

		when(userRepositoryMock.findByEmail(any(String.class))).thenReturn(user);

		User userNew = userService.getByEmail("stipe@tvz.hr");

		assertEquals(userNew, user);
	}

	@Test
	public void testGetAllWithRole() {
		User user = new User();
		user.setUsername("Stipe");
		user.setEmail("stipe@tvz.hr");
		user.setPassword("stipePass");

		List<User> users = new ArrayList<>();
		users.add(user);

		when(userRepositoryMock.findAllByRoleAndStatusNot(any(Role.class), any(UserStatus.class))).thenReturn(users);

		Collection<User> usersNew = userService.getAllWithRole(roleServiceMock.getById(1L));

		assertEquals(usersNew.size(), 1);
	}
	
	@Test
	public void testCreate() {
		User user = new User();
		user.setUsername("Stipe");
		user.setEmail("stipe@tvz.hr");
		user.setPassword("stipePass");

		when(userRepositoryMock.saveAndFlush(any(User.class))).thenReturn(user);

		User user1 = new User();
		user1.setUsername("Davorin");
		user1.setEmail("davorin@tvz.hr");
		user1.setPassword("davorinPass");
		User userNew = userService.create(user1);

		assertEquals(userNew, user);
	}
	
	@Test
	public void testUpdate() {
		User user = new User();
		user.setUsername("Stipe");
		user.setEmail("stipe@tvz.hr");
		user.setPassword("stipePass");

		when(userRepositoryMock.saveAndFlush(any(User.class))).thenReturn(user);

		user.setEmail("stipe1@tvz.hr");
		User userNew = userService.update(user);

		assertEquals(userNew, user);
	}
}
