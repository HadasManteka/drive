package team1.spring.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team1.spring.training.modal.ResponseModal;
import team1.spring.training.services.UploadFileService;
import team1.spring.training.util.MessageRepository;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/api/upload")
public class UploadFileController {

    @Autowired
    private UploadFileService _uploadFileService;

    @PostMapping("/{user}")
    public ResponseEntity<ResponseModal> uploadFiles(@PathVariable("user") String user, @RequestBody MultipartFile[] uploadFiles) {
        try {
            _uploadFileService.uploadFiles(uploadFiles, user);
            return new ResponseEntity<>(new ResponseModal("Upload " + uploadFiles.length + " files"), HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>(new ResponseModal(MessageRepository.SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
