package team1.spring.training.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team1.spring.training.entities.UploadFile;
import team1.spring.training.entities.User;

@Repository
public interface FileRepository extends CrudRepository<UploadFile, Long> {
    int countByFileName(String fileName);
//    UploadFile findByFileNameAndUser(String name, String user);
}
