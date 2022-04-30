package team1.spring.training.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team1.spring.training.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByName(String name);
    boolean existsByName(String name);

}
