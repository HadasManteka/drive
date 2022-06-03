package team1.spring.training.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team1.spring.training.entities.User;
import team1.spring.training.exceptions.NameAlreadyExists;
import team1.spring.training.repositories.UserRepository;
import team1.spring.training.util.GenerateHashCode;

import javax.security.auth.login.FailedLoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    @Autowired
    private UserRepository _userRepository;

    public void addUser(User user) throws NoSuchAlgorithmException, NameAlreadyExists {
        String hashPassword = new GenerateHashCode().generateSHAHash(user.getPassword());

        // check if the name exists
        if (!_userRepository.existsByName(user.getName())) {
            _userRepository.save(new User(user.getName(), hashPassword));
        } else {
            throw new NameAlreadyExists();
        }

    }

    public void login(User user, HttpServletRequest request) throws FailedLoginException {
        User foundUser = _userRepository.findByName(user.getName());

        if (foundUser != null &&
            new GenerateHashCode().doesEquals(foundUser.getPassword(), user.getPassword())) {

            HttpSession oldSession = request.getSession(false);

            if (oldSession != null) {
                oldSession.invalidate();
            }
            HttpSession newSession = request.getSession(true);

        } else {
            throw new FailedLoginException();
        }
    }

    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }
}
