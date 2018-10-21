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

import tvz.naprednaJava.rozi.AutoServis.model.Permission;
import tvz.naprednaJava.rozi.AutoServis.repository.PermissionRepository;
import tvz.naprednaJava.rozi.AutoServis.service.PermissionService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PermissionServiceTest {

	@Mock
	PermissionRepository permissionRepositoryMock;

	@InjectMocks
	PermissionService permissionService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetById() {

		Permission permission = new Permission();
		permission.setName("Test");

		when(permissionRepositoryMock.findOne(any(Long.class))).thenReturn(permission);

		Permission permissionNew = permissionService.getById(1L);

		assertEquals(permissionNew.getName(), permission.getName());
	}

	@Test
	public void testGetByName() {

		Permission permission = new Permission();
		permission.setName("Test");

		when(permissionRepositoryMock.findByName(any(String.class))).thenReturn(permission);

		Permission permissionNew = permissionService.getByName("bla");

		assertEquals(permissionNew.getName(), permission.getName());
	}

	@Test
	public void getAll() {
		Permission permission = new Permission();
		permission.setName("Test");
		List<Permission> permissionList = new ArrayList<>();
		permissionList.add(permission);

		when(permissionRepositoryMock.findAll()).thenReturn(permissionList);

		Collection<Permission> permissionListNew = permissionService.getAll();

		assertEquals(permissionListNew, permissionList);
	}
}
