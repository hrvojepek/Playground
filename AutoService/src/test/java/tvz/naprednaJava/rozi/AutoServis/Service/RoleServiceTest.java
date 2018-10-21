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

import tvz.naprednaJava.rozi.AutoServis.model.Role;
import tvz.naprednaJava.rozi.AutoServis.repository.RoleRepository;
import tvz.naprednaJava.rozi.AutoServis.service.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RoleServiceTest {

	@Mock
	RoleRepository roleRepositoryMock;

	@InjectMocks
	RoleService roleService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetById() {
		Role role = new Role();
		role.setName("rola");

		when(roleRepositoryMock.getOne(any(Long.class))).thenReturn(role);

		Role roleNew = roleService.getById(1L);

		assertEquals(roleNew.getName(), role.getName());
	}

	@Test
	public void testGetByName() {
		Role role = new Role();
		role.setName("rola");

		when(roleRepositoryMock.findByName(any(String.class))).thenReturn(role);

		Role roleNew = roleService.getByName("veki");

		assertEquals(roleNew.getName(), role.getName());
	}

	@Test
	public void testGetAll() {
		Role role = new Role();
		role.setName("rola");
		List<Role> roleList = new ArrayList<>();
		roleList.add(role);

		when(roleRepositoryMock.findAll()).thenReturn(roleList);

		Collection<Role> roleListNew = roleService.getAll();

		assertEquals(roleListNew, roleList);
	}

	@Test
	public void create() {
		Role role = new Role();
		role.setName("Admin");

		when(roleRepositoryMock.saveAndFlush(any(Role.class))).thenReturn(role);

		Role role1 = new Role();
		role1.setName("rola");
		Role roleNew = roleService.create(role1);

		assertEquals(roleNew, role);
	}

	@Test
	public void update() {
		Role role = new Role();
		role.setName("Admin");

		when(roleRepositoryMock.saveAndFlush(any(Role.class))).thenReturn(role);

		role.setName("Manager");
		Role roleNew = roleService.update(role);

		assertEquals(roleNew, role);
	}
}
