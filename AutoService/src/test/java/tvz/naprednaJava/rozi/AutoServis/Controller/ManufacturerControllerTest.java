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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import tvz.naprednaJava.rozi.AutoServis.controller.ManufacturerController;
import tvz.naprednaJava.rozi.AutoServis.model.Manufacturer;
import tvz.naprednaJava.rozi.AutoServis.service.ManufacturerService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ManufacturerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private ManufacturerController manufacturerController;

	@Mock
	private ManufacturerService manufacturerServiceMock;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(manufacturerController).build();
	}
	
	@Test
    public void testIndex() throws Exception {
		Manufacturer manufacturer = new Manufacturer();
		 when(manufacturerServiceMock.getAllActive()).thenReturn(Arrays.asList(manufacturer,manufacturer));

	        this.mockMvc
	                .perform(get("/private/manufacturers"))
	                .andExpect(status().isOk())
	                .andExpect(model().attributeExists("manufacturers"))
	                .andExpect(model().attribute("manufacturers", Matchers.hasSize(2)))
	                .andExpect(view().name("/manufacturers/manufacturer-list"));
	}
}
