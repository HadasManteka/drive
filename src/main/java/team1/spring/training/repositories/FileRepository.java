package team1.spring.training.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team1.spring.training.entities.UploadFile;

@Repository
public interface FileRepository extends CrudRepository<UploadFile, Long> {
    int countByFileName(String fileName);
}
