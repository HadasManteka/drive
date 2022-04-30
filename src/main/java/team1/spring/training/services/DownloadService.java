package team1.spring.training.services;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import team1.spring.training.entities.UploadFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class DownloadService {

    public FileSystemResource getFile(UploadFile fileToDownload) throws FileNotFoundException {
        if (Files.exists(Paths.get(fileToDownload.getLocation()))) {
            return new FileSystemResource(new File(fileToDownload.getLocation()));
        } else {
            throw new FileNotFoundException();
        }

    }
}
