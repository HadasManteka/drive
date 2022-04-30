package team1.spring.training.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import team1.spring.training.entities.User;
import team1.spring.training.repositories.UserRepository;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    UserRepository _mockUserRepository;

    @Autowired
    public MockMvc _mockMvc;

    @Test
    public void register_successAndAuthorized() throws Exception {
        _mockMvc.perform(post("/user/register").session(new MockHttpSession())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writer().withDefaultPrettyPrinter()
                        .writeValueAsBytes(new User("hadas", "123"))))
                .andExpect(status().isOk());
    }

    @Test
    public void register_notAuthorized() throws Exception {
        _mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writer().withDefaultPrettyPrinter()
                        .writeValueAsBytes(new User("hadas", "123"))))
                .andExpect(status().isUnauthorized());
    }
}