package team1.spring.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team1.spring.training.entities.UploadFile;
import team1.spring.training.modal.ResponseModal;
import team1.spring.training.services.SystemService;
import team1.spring.training.util.MessageRepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@CrossOrigin
@RestController
@RequestMapping("/api/")
public class SystemController {

    @Autowired
    private SystemService _systemService;

    @GetMapping
    @RequestMapping("file")
    public Iterable<UploadFile> getAllFiles() {
        return _systemService.getAllFiles();
    }

    @PostMapping
    @RequestMapping("delete")
    public ResponseEntity<ResponseModal> deleteFile(@RequestBody UploadFile file)
    {
        try {
            _systemService.deleteFile(file);
            return new ResponseEntity<>(new ResponseModal(file.getFileName() + " deleted successfully"),
                                        HttpStatus.OK);
        } catch (FileNotFoundException e) {
            return new ResponseEntity<>(new ResponseModal(MessageRepository.FILE_NOT_FOUND),
                                        HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseModal(MessageRepository.DELETE_ERROR),
                                        HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
