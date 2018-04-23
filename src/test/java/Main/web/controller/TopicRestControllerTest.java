package Main.web.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import Main.model.Topic;
import Main.model.User;
import Main.rest.Controller.TopicRestController;
import Main.service.TopicService;
import Main.service.UserService;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TopicRestControllerTest {

	private MockMvc mockMvc;

    @Mock
    private TopicService topicService;

    @InjectMocks
    private TopicRestController topicRestController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(topicRestController)
                
                .build();
    }
    @Test
    public void test_get_all_success() throws Exception {
   User us=new User(1, "Daenerys","Targaryes","emaill","haslo");
        User us2=new User(2, "John", "Snow","email2","haselko");
   
        List<Topic> users =  Arrays.asList(
                 new Topic(1, "tytul",us),
                 new Topic(2, "John", us2));

         when(topicService.findAlltopics()).thenReturn(users);

         mockMvc.perform(get("/api/user/topics"))
                 .andExpect(status().isOk())
                 .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                 .andExpect(jsonPath("$", hasSize(2)))
         .andExpect(jsonPath("$[0].id", is(1)))
         .andExpect(jsonPath("$[0].title", is("tytul")))
         .andExpect(jsonPath("$[1].id", is(2)))
         .andExpect(jsonPath("$[1].title", is("John")));
        
         verify(topicService, times(1)).findAlltopics();
         verifyNoMoreInteractions(topicService);
     }
    
    @Test
    public void test_get_by_id_success() throws Exception {
        User us =  new User(1, "Daenerys","Targaryes","emaill","haslo");
       Topic topic= new Topic(1, "tytul",us);


        when(topicService.findByTitle("tytul")).thenReturn(topic);

        mockMvc.perform(get("/api/user/topics/{topictitle}", "tytul"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("tytul")));

        verify(topicService, times(1)).findByTitle("tytul");
        verifyNoMoreInteractions(topicService);
    }
}
