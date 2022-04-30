package team1.spring.training.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import team1.spring.training.entities.UploadFile;
import team1.spring.training.services.SystemService;
import java.io.FileNotFoundException;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class SystemControllerTest {

    @MockBean
    SystemService _mockSystemService;

    @Autowired
    public MockMvc _mockMvc;

    @Test
    public void getAllFiles_succeed() throws Exception {
        when(_mockSystemService.getAllFiles()).thenReturn(Lists.newArrayList(
                new UploadFile(11, "a.txt", "a.txt"),
                new UploadFile(22, "b.txt",  "b.txt"))
        );

        _mockMvc.perform(get("/api/file") .session(new MockHttpSession()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].timestamp", is(11)))
                .andExpect(jsonPath("$[1].timestamp", is(22)));

        verify(_mockSystemService, times(1)).getAllFiles();
    }

    @Test
    public void getAllFiles_emptyListOfFiles() throws Exception {
        when(_mockSystemService.getAllFiles()).thenReturn(Lists.newArrayList());

        _mockMvc.perform(get("/api/file").session(new MockHttpSession()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(0)));

        verify(_mockSystemService, times(1)).getAllFiles();
    }


    @Test
    public void deleteFile_notFound() throws Exception {

        Mockito.doThrow(new FileNotFoundException()).when(_mockSystemService).deleteFile(any());

        _mockMvc.perform(MockMvcRequestBuilders
                .post("/api/delete").session(new MockHttpSession())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writer().withDefaultPrettyPrinter()
                        .writeValueAsBytes(new UploadFile(55,"h.txt", "h.txt"))))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteFile_succeed() throws Exception {

        _mockMvc.perform(MockMvcRequestBuilders
                .post("/api/delete").session(new MockHttpSession())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writer().withDefaultPrettyPrinter()
                        .writeValueAsBytes(new UploadFile(55,"h.txt", "h.txt"))))
                .andExpect(status().isOk());
    }
}
