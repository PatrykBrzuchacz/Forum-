package Main;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import Main.model.User;
import Main.rest.Controller.UserRestController;
import Main.service.UserService;
import Main.web.controller.UserController;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class SecurityuwierzytelnianieApplicationTests {
/*
	private MockMvc mockMvc;
	 
    @Mock
    private UserService  userService;
    @InjectMocks
    private UserRestController userRestController;
    
    @Before
    public void setup() {
    	MockitoAnnotations.initMocks(this);
    mockMvc=MockMvcBuilders.standaloneSetup(userRestController).build();
    }
    
    @Test
    public void test_get_all_success() throws Exception {
        ArrayList<User> users = (ArrayList<User>) Arrays.asList(
                new User(1,"imie", "nazwisko", "emal", "pass"),
                new User(2,"imie2","nazwisko2", "email", "pass1"));

        when(userService.findAll()).thenReturn( users);

        mockMvc.perform(get("api/admin/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].username", is("imie")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].username", is("imie2")));

        verify(userService, times(1)).findAll();
        verifyNoMoreInteractions(userService);
    }
    /*
    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get("/admin/uzytkownicy"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("uzytkownicy", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("firstName", is("imie")),
                                hasProperty("lastName", is("nimie")),
                                hasProperty("email", is("email")),
                                hasProperty("password", is("passy"))
                        )
                )))
                .andExpect(model().attribute("uzytkownicy", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("firstName", is("2imie")),
                                hasProperty("lastName", is("2nimie")),
                                hasProperty("email", is("2email")),
                                hasProperty("password", is("2passy"))
                        )
                )))
                .andExpect(view().name("admin/uzytkownicy"))
                .andExpect(model().attribute("uzytkownicy", hasSize(2)))
              ;
    }
  
    @Test
    public void testList() throws Exception {
      List<User> user=new ArrayList<>();
    user.add(new User());
    user.add(new User());
    
    when(userService.findAll()).thenReturn((ArrayList<User>) user);
   mockMvc.perform(get("/admin/uzytkownicy"))
   .andExpect(status().isOk())
   .andExpect(view().name("admin/uzytkownicy"))
   .andExpect(model().attribute("uzytkownicy" , hasSize(2)));
   
    
    }
    
  
    @Test
    public void addUserForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("registerForm"))
                .andExpect(forwardedUrl("registerForm"))
                .andExpect(model().attribute("user", hasProperty("id", nullValue())))
                .andExpect(model().attribute("user", hasProperty("firstName", isEmptyOrNullString())))
                .andExpect(model().attribute("user", hasProperty("lastName", isEmptyOrNullString())))
               .andExpect(model().attribute("user", hasProperty("email", isEmptyOrNullString())))
               .andExpect(model().attribute("user", hasProperty("password", isEmptyOrNullString())));
hasSize(1);
        verifyZeroInteractions(userService);
    }*/
}
