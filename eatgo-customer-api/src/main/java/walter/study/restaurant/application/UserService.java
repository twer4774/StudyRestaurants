package walter.study.restaurant.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import walter.study.restaurant.application.exception.EmailExistedException;
import walter.study.restaurant.application.exception.EmailNotExistedException;
import walter.study.restaurant.application.exception.PasswordWrongException;
import walter.study.restaurant.domain.User;
import walter.study.restaurant.domain.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

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

    public User registerUser(String email, String name, String password) {

        Optional<User> existed = userRepository.findByEmail(email);

        if(existed.isPresent()){
            throw new EmailExistedException(email);
        }

        String encodedPassword = passwordEncoder.encode(password);

        User newUser = User.builder()
                .email(email)
                .name(name)
                .password(encodedPassword)
                .level(1L)
                .build();

        return userRepository.save(newUser);
    }

    public User authenticate(String email, String password) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new EmailNotExistedException(email));

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new PasswordWrongException();
        };

        return user;
    }
}
