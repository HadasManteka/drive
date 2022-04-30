package team1.spring.training.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import team1.spring.training.entities.UploadFile;
import team1.spring.training.services.DownloadService;
import team1.spring.training.services.UploadFileService;

import java.io.FileNotFoundException;

import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class DownloadControllerTest {

    @Autowired
    public MockMvc _mockMvc;

    @MockBean
    DownloadService _mockDownloadService;

    @Test
    public void getFile_succeed() throws Exception {

        _mockMvc.perform(MockMvcRequestBuilders
                .post("/api/download").session(new MockHttpSession())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writer().withDefaultPrettyPrinter()
                        .writeValueAsBytes(new UploadFile(55,"h.txt", "h.txt"))))
                .andExpect(status().isOk());
    }

    @Test
    public void getFile_nullEntity_badRequest() throws Exception {

        _mockMvc.perform(MockMvcRequestBuilders
                .post("/api/download").session(new MockHttpSession())
                .contentType(MediaType.APPLICATION_JSON)
                .content((byte[]) null))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getFile_notFound() throws Exception {

        Mockito.doThrow(new FileNotFoundException()).when(_mockDownloadService).getFile(any());

        _mockMvc.perform(MockMvcRequestBuilders
                .post("/api/download").session(new MockHttpSession())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writer().withDefaultPrettyPrinter()
                        .writeValueAsBytes(new UploadFile(55,"h.txt", "h.txt"))))
                .andExpect(status().isNotFound());
    }
}