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

import tvz.naprednaJava.rozi.AutoServis.controller.RepairController;
import tvz.naprednaJava.rozi.AutoServis.model.Repair;
import tvz.naprednaJava.rozi.AutoServis.service.RepairService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RepairControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private RepairController repairController;

	@Mock
	private RepairService repairServiceMock;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(repairController).build();
	}
	
	@Test
    public void testIndex() throws Exception {

		Repair repair = new Repair();
        when(repairServiceMock.getAllActive()).thenReturn(Arrays.asList(repair,repair));

        this.mockMvc
                .perform(get("/repairs"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("repairs"))
                .andExpect(model().attribute("repairs", Matchers.hasSize(2)))
                .andExpect(view().name("repair/index"));
    }
	
	@Test
    public void testIndexPrivate() throws Exception {

		Repair repair = new Repair();
        when(repairServiceMock.getAllActive()).thenReturn(Arrays.asList(repair,repair));

        this.mockMvc
                .perform(get("/private/repairs"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("repairs"))
                .andExpect(model().attribute("repairs", Matchers.hasSize(2)))
                .andExpect(view().name("authorized_pages/repair/index"));
    }
	
	@Test
    public void testAdd() throws Exception {
		Repair repair = new Repair();
        when(repairServiceMock.getAllActive()).thenReturn(Arrays.asList(repair,repair));

        this.mockMvc
                .perform(get("/private/repair/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("authorized_pages/repair/add-repair"));
    }
}
