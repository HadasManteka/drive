package team1.spring.training.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import team1.spring.training.entities.UploadFile;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FileRepositoryTest {

    @Autowired
    private TestEntityManager _entityManager;

    @Autowired
    private FileRepository _FileRepository;

    private UploadFile _file;

    private long _fileId;

    @Before
    public void init() {
        // Create a file and saved it
         _file = new UploadFile(new Date().getTime(),"M:/test/hadas.txt", "hadas.txt");
         _entityManager.persistAndFlush(_file);
         _fileId = _file.getId();
    }

    @Test
    public void fileRepository_create() {
        UploadFile foundFile =  _FileRepository.findOne(_fileId);

        assertNotNull(foundFile);
        assertEquals(_file.getLocation(),  foundFile.getLocation());
    }

    @Test
    public void fileRepository_delete() {
        _FileRepository.delete(_fileId);
        _entityManager.flush();

        assertNull( _entityManager.find(UploadFile.class, _fileId));
    }

    @Test
    public void fileRepository_update() {
        _FileRepository.save(new UploadFile(_fileId, new Date().getTime(), "bla", "bla"));

        assertEquals(_entityManager.find(UploadFile.class, _fileId).getLocation(), "bla");
    }
}