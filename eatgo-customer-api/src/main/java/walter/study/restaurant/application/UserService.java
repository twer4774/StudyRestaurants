package walter.study.restaurant.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import walter.study.restaurant.domain.User;
import walter.study.restaurant.domain.UserRepository;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public User registerUser(String email, String name, String password) {
        User newUser = User.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();

        return userRepository.save(newUser);
    }
}
