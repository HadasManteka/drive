package team1.spring.training.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team1.spring.training.entities.UploadFile;
import team1.spring.training.repositories.FileRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class SystemService {

    @Autowired
    private FileRepository _fileRepository;

    public Iterable<UploadFile> getAllFiles() {
        return _fileRepository.findAll();
    }

    public void deleteFile(UploadFile fileToDel) throws FileNotFoundException {
        if (Files.exists(Paths.get(fileToDel.getLocation()))) {
            _fileRepository.delete(fileToDel.getId());
            new File(fileToDel.getLocation()).delete();
        } else {
            throw new FileNotFoundException();
        }
    }
}
