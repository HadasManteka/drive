package team1.spring.training.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team1.spring.training.entities.UploadFile;
import team1.spring.training.repositories.FileRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Date;

@Service
public class UploadFileService {

    @Autowired
    private FileRepository _fileRepository;

    public void uploadFiles(MultipartFile[] uploadFiles) throws IOException {
        String uploadFolder = FileSystems.getDefault().getPath("Upload").toAbsolutePath().toString();
        new File(uploadFolder).mkdir();

       for (MultipartFile file : uploadFiles) {
           uploadSingleFile(uploadFolder, file);
       }
    }

    public void uploadSingleFile(String uploadFolder, MultipartFile uploadFile) throws IOException {

        String originName = uploadFile.getOriginalFilename();
        String destName = findDestNameForSaving(originName);

        // Download the file and save it's details to the db
        saveFileToServer(uploadFile, uploadFolder + File.separator + destName);
        saveFileToDb(new UploadFile(new Date().getTime(), "Upload" + File.separator + destName, originName));
    }

    /**
     * Create a name for the uploaded file' if the file is already exists
     * than it will add the number no to the file name
     * @param name - origin name of file
     * @return the destination name of the file
     */
    public String findDestNameForSaving(String name) {

        String destName = name;
        int numOfExistingFileName = _fileRepository.countByFileName(name);

        // If the file is already exists
        if (numOfExistingFileName > 0) {
            String endOfFile = destName.substring(destName.lastIndexOf("."));

            // Adds number to the file's name
            destName = destName.replace(endOfFile,
                "(" + numOfExistingFileName + ")" + endOfFile);
        }

        return destName;
    }

    public void saveFileToServer(MultipartFile multipartFile, String destPath) throws IOException {
        multipartFile.transferTo(new File(destPath));
    }

    public void saveFileToDb(UploadFile file) {
        _fileRepository.save(file);
    }
}
