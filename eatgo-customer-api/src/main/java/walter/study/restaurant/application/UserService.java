package walter.study.restaurant.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import walter.study.restaurant.domain.User;
import walter.study.restaurant.domain.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public User registerUser(String email, String name, String password) {

        Optional<User> existed = userRepository.findByEmail(email);

        if(existed.isPresent()){
            throw new EmailExistedException(email);
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        User newUser = User.builder()
                .email(email)
                .name(name)
                .password(encodedPassword)
                .level(1L)
                .build();

        return userRepository.save(newUser);
    }
}
