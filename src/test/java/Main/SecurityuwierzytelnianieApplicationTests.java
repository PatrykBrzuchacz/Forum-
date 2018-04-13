package Main;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
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

@RunWith(MockitoJUnitRunner.class)
public class SecurityuwierzytelnianieApplicationTests {

	private MockMvc mockMvc;
	 
    @Mock
    private UserService  userService;
    @InjectMocks
    private UserController userController;
    
    @Before
    public void setup() {
    	MockitoAnnotations.initMocks(this);
    mockMvc=MockMvcBuilders.standaloneSetup(userController).build();
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
    }
}
