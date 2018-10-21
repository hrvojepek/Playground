package tvz.naprednaJava.rozi.AutoServis.Controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import tvz.naprednaJava.rozi.AutoServis.controller.StationsController;
import tvz.naprednaJava.rozi.AutoServis.enums.FormMode;
import tvz.naprednaJava.rozi.AutoServis.form.StationForm;
import tvz.naprednaJava.rozi.AutoServis.model.Station;
import tvz.naprednaJava.rozi.AutoServis.model.User;
import tvz.naprednaJava.rozi.AutoServis.service.RoleService;
import tvz.naprednaJava.rozi.AutoServis.service.StationService;
import tvz.naprednaJava.rozi.AutoServis.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StationsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private StationsController stationsController;

	@Mock
	private StationService stationServiceMock;

	@Mock
	private UserService userServiceMock;

	@Mock
	private RoleService roleServiceMock;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(stationsController).build();
	}
	
	@Test
    public void testIndex() throws Exception{

        Station station = new Station();
        when(stationServiceMock.getAllActive()).thenReturn(Arrays.asList(station,station));

        this.mockMvc
                .perform(get("/stations"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("stations"))
                .andExpect(model().attribute("stations", Matchers.hasSize(2)))
                .andExpect(view().name("stations/index"));
    }
	
	 @Test
    public void testPrivateStationsTable() throws Exception{

		Station station = new Station();
        when(stationServiceMock.getAllActive()).thenReturn(Arrays.asList(station,station));

        this.mockMvc
                .perform(get("/private/stations"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("stations"))
                .andExpect(model().attribute("stations", Matchers.hasSize(2)))
                .andExpect(view().name("authorized_pages/stations/index"));
    }
	 
	 @Test
    public void testAdd() throws Exception{

		User user = new User();
        when(userServiceMock.getAllWithRole(roleServiceMock.getByName("Manager"))).thenReturn(Arrays.asList(user,user));

        this.mockMvc
                .perform(get("/private/station/new"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("authorized_pages/stations/add-station"))
                .andExpect(model().attributeExists("managers"))
                .andExpect(model().attribute("managers", Matchers.hasSize(2)))
                .andExpect(view().name("authorized_pages/stations/add-station"))
                .andExpect(model().attribute("form", is(new StationForm(FormMode.NEW))));
    }
}
