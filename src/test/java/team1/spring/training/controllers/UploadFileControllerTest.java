package team1.spring.training.controllers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import team1.spring.training.services.UploadFileService;

import java.io.File;
import java.io.FileInputStream;

import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class UploadFileControllerTest {

    @MockBean
    UploadFileService _mockUploadService;

    @Autowired
    public MockMvc _mockMvc;

    @Rule
    public TemporaryFolder _tempFile = new TemporaryFolder();

    @Test
    public void uploadFile_singleFile() throws Exception {

        Mockito.doNothing().when(_mockUploadService).saveFileToDb(any());
        Mockito.doNothing().when(_mockUploadService).saveFileToServer(any(), any());

        File tempFile = _tempFile.newFile("bab.txt");

        MockMultipartFile mockFile = new MockMultipartFile("uploadFile", "bab.txt",
                                "text/plain", new FileInputStream(new File(tempFile.getAbsolutePath())));

        _mockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/upload")
                                               .file(mockFile)
                                                .session(new MockHttpSession()))
                                                .andExpect(status().isOk());
    }

    @Test(expected = Exception.class)
    public void uploadFile_nullFile() throws Exception {
        _mockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/upload")
                .file(null).session(new MockHttpSession()));
    }

    @Test
    public void uploadFile_ArrayOfFiles() throws Exception {

        Mockito.doNothing().when(_mockUploadService).saveFileToDb(any());
        Mockito.doNothing().when(_mockUploadService).saveFileToServer(any(), any());

        File tempFile = _tempFile.newFile("bab.txt");

        MockMultipartFile mockFile = new MockMultipartFile("uploadFile", "bab.txt",
                "text/plain", new FileInputStream(new File(tempFile.getAbsolutePath())));

        _mockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/upload")
                .file(mockFile)
                .file(mockFile)
                .file(mockFile)
                .session(new MockHttpSession()))
                .andExpect(status().isOk());
    }
}