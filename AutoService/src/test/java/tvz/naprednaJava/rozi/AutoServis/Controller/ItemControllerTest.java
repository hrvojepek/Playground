package tvz.naprednaJava.rozi.AutoServis.Controller;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import tvz.naprednaJava.rozi.AutoServis.controller.ItemController;
import tvz.naprednaJava.rozi.AutoServis.enums.FormMode;
import tvz.naprednaJava.rozi.AutoServis.form.ItemForm;
import tvz.naprednaJava.rozi.AutoServis.model.Item;
import tvz.naprednaJava.rozi.AutoServis.model.Manufacturer;
import tvz.naprednaJava.rozi.AutoServis.service.ItemService;
import tvz.naprednaJava.rozi.AutoServis.service.ManufacturerService;

import java.util.Arrays;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemService itemServiceMock;

    @Mock
    private ManufacturerService manufacturerServiceMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    public void testIndex() throws Exception {

        Item item = new Item();
        when(itemServiceMock.getAllActive()).thenReturn(Arrays.asList(item,item));

        this.mockMvc
                .perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("items"))
                .andExpect(model().attribute("items", Matchers.hasSize(2)))
                .andExpect(view().name("items/index"));
    }

    @Test
    public void testPrivateProductTable() throws Exception {

        Item item = new Item();
        when(itemServiceMock.getAllActive()).thenReturn(Arrays.asList(item,item));

        this.mockMvc
                .perform(get("/private/items"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("items"))
                .andExpect(model().attribute("items", Matchers.hasSize(2)))
                .andExpect(view().name("authorized_pages/items/index"));
    }
    @Test
    public void testPrivateProductTableOfManufacturer() throws Exception {

        Item item = new Item();
        when(itemServiceMock.getAllActiveByManufacturer(any(Manufacturer.class))).thenReturn(Arrays.asList(item,item));

        this.mockMvc
                .perform(get("/private/items/manufacturer/{manufacturer}",1))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("items"))
                .andExpect(model().attribute("items", Matchers.hasSize(2)))
                .andExpect(view().name("authorized_pages/items/index"));
    }

    @Test
    public void testAdd() throws Exception {

        Manufacturer manufacturer = new Manufacturer();
        when(manufacturerServiceMock.getAllActive()).thenReturn(Arrays.asList(manufacturer,manufacturer));

        this.mockMvc
                .perform(get("/private/item/new"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("authorized_pages/items/add-item"))
                .andExpect(model().attributeExists("manufacturers"))
                .andExpect(model().attribute("manufacturers", Matchers.hasSize(2)))
                .andExpect(view().name("authorized_pages/items/add-item"));
    }

    @Test
    public void testCreate() throws Exception{

        Item item = new Item();
        ItemForm itemForm = new ItemForm();
        itemForm.setItem(item);
        itemForm.setMode(FormMode.NEW);
        itemForm.setUnitsInStock("2");
        when(itemServiceMock.create(any(Item.class))).thenReturn(item);

        this.mockMvc
                .perform(post("/private/item/create")
                        .param("item","1")
                        .param("mode", "NEW")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/private/items"));
    }

    @Test
    public void testUpdate() throws Exception{

        Item item = new Item();
        ItemForm itemForm = new ItemForm();
        itemForm.setItem(item);
        itemForm.setMode(FormMode.NEW);
        itemForm.setUnitsInStock("2");
        //when(itemServiceMock.create(any(Item.class))).thenReturn(item);

        this.mockMvc
                .perform(post("/private/item/update")
                        .param("action","")
                        .param("mode", "NEW")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/private/items"));
    }

    @Test
    public void testUpdateWhenActionDelete() throws Exception{

        Item item = new Item();
        ItemForm itemForm = new ItemForm();
        itemForm.setItem(item);
        itemForm.setMode(FormMode.NEW);
        itemForm.setUnitsInStock("2");
        when(itemServiceMock.delete(any(Item.class))).thenReturn(true);

        this.mockMvc
                .perform(post("/private/item/update")
                        .param("action","delete")
                        .param("mode", "NEW")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/private/items"));
    }

    @Test
    public void testUpdateWhenActionNotSupported() throws Exception{

        Item item = new Item();
        ItemForm itemForm = new ItemForm();
        itemForm.setItem(item);
        itemForm.setMode(FormMode.NEW);
        itemForm.setUnitsInStock("2");
        when(itemServiceMock.delete(any(Item.class))).thenReturn(true);

        this.mockMvc
                .perform(post("/private/item/update")
                        .param("action","unsupportedAction")
                        .param("mode", "NEW")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/private/items"));
    }
//    @TestConfiguration
//    static class InternalConfig {
//
//        @Bean
//        WebMvcConfigurer configurer() {
//            return new WebMvcConfigurerAdapter() {
//                @Override
//                public void addFormatters(FormatterRegistry registry) {
//                    registry.addConverter(String.class, Item.class, item -> {
//                        if (item.equals("123")) {
//                            Item itemNew = new Item();
//                            itemNew.setName("My Item");
//                            return itemNew;
//                        }
//                        throw new IllegalArgumentException();
//                    });
//                }
//            };
//        }
//    }
}