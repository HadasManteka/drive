package team1.spring.training.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import team1.spring.training.entities.User;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager _entityManager;

    @Autowired
    private UserRepository _userRepository;

    private User _user;

    private long _userId;

    @Before
    public void saveTheUserEntityToTheDb() {
        // Create a user and saved it
        _user = new User("hadas","123");
        _entityManager.persistAndFlush(_user);
        _userId = _user.getId();
    }

    @Test
    public void userRepository_create() {
        User foundFile =  _userRepository.findOne(_userId);

        assertNotNull(foundFile);
        assertEquals(_user.getName(),  foundFile.getName());
    }

    @Test
    public void userRepository_delete() {
        _userRepository.delete(_userId);
        _entityManager.flush();

        assertNull( _entityManager.find(User.class, _userId));
    }

    @Test
    public void userRepository_update() {
        _userRepository.save(new User(_userId, "hadas", "newPass"));

        assertEquals("newPass", _entityManager.find(User.class, _userId).getPassword());
    }
}