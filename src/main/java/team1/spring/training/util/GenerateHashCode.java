package team1.spring.training.util;

import java.security.NoSuchAlgorithmException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
public class GenerateHashCode {

    BCryptPasswordEncoder _bcrypt = new BCryptPasswordEncoder();

    public String generateSHAHash(String data) throws NoSuchAlgorithmException {
        return _bcrypt.encode(data);
    }

    public boolean doesEquals(String dataAfterEncrypt, String dataBeforeEncrypt) {
        return _bcrypt.matches(dataBeforeEncrypt, dataAfterEncrypt);
    }
}
