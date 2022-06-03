package team1.spring.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team1.spring.training.entities.User;
import team1.spring.training.exceptions.NameAlreadyExists;
import team1.spring.training.modal.ResponseModal;
import team1.spring.training.services.UserService;
import team1.spring.training.util.MessageRepository;
import javax.security.auth.login.FailedLoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;

import static org.springframework.http.HttpStatus.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService _userService;

    @Autowired
    private HttpServletRequest _request;

    @Autowired
    private HttpServletResponse _response;

    @PostMapping
    @RequestMapping("/register")
    public ResponseEntity<Object> register(@RequestBody User user) {
        try {
            _userService.addUser(user);
            return new ResponseEntity<>(user, OK);
        } catch(NameAlreadyExists e) {
            return new ResponseEntity<>(new ResponseModal(MessageRepository.NAME_ALREADY_TAKEN),
                                        UNPROCESSABLE_ENTITY);
        } catch(NoSuchAlgorithmException e) {
            return new ResponseEntity<>(new ResponseModal(MessageRepository.SERVER_ERROR),
                                        INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @RequestMapping("/login")
    public ResponseEntity<ResponseModal> login(@RequestBody User user){
        try {
            _userService.login(user, _request);
            return new ResponseEntity<>(OK);
        } catch (FailedLoginException e) {
            return new ResponseEntity<>(new ResponseModal(MessageRepository.INCORRECT_IDENTIFICATION_DETAILS),
                                        UNAUTHORIZED);
        }
    }

    @GetMapping
    @RequestMapping("/logout")
    public ResponseEntity<ResponseModal> logout() {
        try {
            _userService.logout(_request);
            return new ResponseEntity<>(OK);
        } catch(Exception e) {
            return new ResponseEntity<>(new ResponseModal(MessageRepository.SERVER_ERROR),
                                        INTERNAL_SERVER_ERROR);
        }
    }
}
