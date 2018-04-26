package Main.web.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import Main.Validation.RegisterFormValidator;
import Main.model.User;
import Main.model.UserRole;
import Main.rest.Controller.UserRestController;
import Main.service.UserService;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRestControllerTest {

	private MockMvc mockMvc;
	@Mock private RegisterFormValidator validator;
    @Mock
    private UserService userService;

    @InjectMocks
    private UserRestController userRestController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userRestController)
                
                .build();
    }

    // =========================================== Get All Users ==========================================
    @Test
    public void test_get_all_success() throws Exception {
       List<User> users =  Arrays.asList(
                new User(1, "Daenerys","Targaryes","emaill","haslo"),
                new User(2, "John", "Snow","email2","haselko"));

        when(userService.findAll()).thenReturn(users);

        mockMvc.perform(get("/api/admin/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("Daenerys")))
                .andExpect(jsonPath("$[0].lastName", is("Targaryes")))
                .andExpect(jsonPath("$[0].email", is("emaill")))
                .andExpect(jsonPath("$[0].password", is("haslo")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("John")))
                .andExpect(jsonPath("$[1].lastName", is("Snow")))
                .andExpect(jsonPath("$[1].email", is("email2")))
                .andExpect(jsonPath("$[1].password", is("haselko")));
        

        verify(userService, times(1)).findAll();
        verifyNoMoreInteractions(userService);
    }
    // =========================================== Get User By ID =========================================
    @Test
    public void test_get_by_id_success() throws Exception {
        User user =  new User(1, "Daenerys","Targaryes","emaill","haslo");
       


        when(userService.getUserById(1)).thenReturn(user);

        mockMvc.perform(get("/api/user/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Daenerys")))
                .andExpect(jsonPath("$.lastName", is("Targaryes")))
                .andExpect(jsonPath("$.email", is("emaill")))
                .andExpect(jsonPath("$.password", is("haslo")));

        verify(userService, times(1)).getUserById(1);
        verifyNoMoreInteractions(userService);
    }
    
    @Test
    public void test_get_by_id_fail_404_not_found() throws Exception {
        when(userService.getUserById(1)).thenReturn(null);

        mockMvc.perform(get("/api/user/{id}", 1))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).getUserById(1);
        verifyNoMoreInteractions(userService);
    }
    
 // =========================================== Create New User ========================================

    @Test
    public void test_create_user_success() throws Exception {
    	 User user =  new User("Daenerys","Targaryes","emai@asdll","cxzhaslo");
         

        
      when(userService.addWithDefaultRole(user)).thenReturn(user);

        mockMvc.perform(
                post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isCreated());
                

   
        verify(userService, times(1)).addWithDefaultRole(user);
        verifyNoMoreInteractions(userService);
    }
   
    // =========================================== Update Existing User ===================================
       @Test
    public void test_update_user_success() throws Exception {
 	User user =  new User(1, "Daenerys","Targaryes","ema@dasil","hadaslo");

 	when(userService.addWithDefaultRole(userService.getUserById(user.getId()))).thenReturn(user);

        mockMvc.perform(
                put("/api/admin/edit/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isOk());

        verify(userService, times(1)).getUserById(user.getId());
        verify(userService, times(1)).addWithDefaultRole(userService.getUserById(user.getId()));
        verifyNoMoreInteractions(userService);
    }
       // =========================================== Delete User ============================================
    
    @Test
    public void test_delete_user_success() throws Exception {
    	User user =  new User(1, "Daenerys","Targaryes","em@dadaill","hasldao");

    	when(userService.getUserById(user.getId())).thenReturn(user);
    	doNothing().when(userService).deleteUser(user.getId());
       

        mockMvc.perform(
                delete("/api/admin/delete/{id}", user.getId()))
                .andExpect(status().isOk());

        verify(userService, times(1)).getUserById(user.getId());
        verify(userService, times(1)).deleteUser(1);
        verifyNoMoreInteractions(userService);
    }
    
    @Test
    public void test_delete_user_fail() throws Exception {
    	User user =  new User(999, "Dys","Targaryes","emdadaill","hasldao");

    	when(userService.getUserById(user.getId())).thenReturn(null);
       

        mockMvc.perform(
                delete("/api/admin/delete/{id}", user.getId()))
        .andExpect(status().isNotFound());

        verify(userService, times(1)).getUserById(user.getId());
    
        verifyNoMoreInteractions(userService);
    }
//converter
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}