package tvz.naprednaJava.rozi.AutoServis.Controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import tvz.naprednaJava.rozi.AutoServis.controller.UserController;
import tvz.naprednaJava.rozi.AutoServis.model.User;
import tvz.naprednaJava.rozi.AutoServis.service.RoleService;
import tvz.naprednaJava.rozi.AutoServis.service.StationService;
import tvz.naprednaJava.rozi.AutoServis.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private UserController usercontroller;

	@Mock
	private UserService userServiceMock;
	
	@Mock
	private RoleService roleServiceMock;
	
	@Mock
	private StationService stationServiceMock;
	
	@Mock
	private PasswordEncoder passwordEncoderMock;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(usercontroller).build();
	}
	
	@Test
    public void testIndex() throws Exception{

        User user = new User();
        when(userServiceMock.getAllWithRole(roleServiceMock.getByName("Customer"))).thenReturn(Arrays.asList(user,user));

        this.mockMvc
                .perform(get("/private/clients"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", Matchers.hasSize(2)))
                .andExpect(view().name("authorized_pages/users/index"));
    }
}
