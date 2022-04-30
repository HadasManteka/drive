package team1.spring.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team1.spring.training.entities.UploadFile;
import team1.spring.training.modal.ResponseModal;
import team1.spring.training.services.DownloadService;
import team1.spring.training.util.MessageRepository;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

@CrossOrigin
@RestController
@RequestMapping("/api/download")
public class DownloadController {

    @Autowired
    private DownloadService _downloadService;

    @Autowired
    private HttpServletResponse _response;


    @PostMapping
    public ResponseEntity<FileSystemResource> getFile(@RequestBody UploadFile fileToDownload) {
        try {
            MimetypesFileTypeMap m = new MimetypesFileTypeMap();
            String contentType = m.getContentType(fileToDownload.getFileName());
            _response.setContentType(contentType);
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION)
                    .body(_downloadService.getFile(fileToDownload));
        } catch (FileNotFoundException e) {
            return new ResponseEntity(new ResponseModal(MessageRepository.FILE_NOT_FOUND),
                                      HttpStatus.NOT_FOUND);
        } catch(Exception e) {
            return new ResponseEntity(MessageRepository.SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
