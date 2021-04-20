package walter.study.restaurant.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import walter.study.restaurant.application.exception.EmailNotExistedException;
import walter.study.restaurant.application.exception.PasswordWrongException;
import walter.study.restaurant.domain.User;
import walter.study.restaurant.domain.UserRepository;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //패스워드 인코더
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    public User authenticate(String email, String password) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new EmailNotExistedException(email));

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new PasswordWrongException();
        };

        return user;
    }
}
