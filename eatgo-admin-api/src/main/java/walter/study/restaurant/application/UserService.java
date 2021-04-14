package walter.study.restaurant.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import walter.study.restaurant.domain.User;
import walter.study.restaurant.domain.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;


    public List<User> getUserList(){
        return userRepository.findAll();
    }

    public User addUser(String email, String name) {
        return userRepository.save(User.builder()
                .email(email)
                .name(name)
                .level(1L)
                .build());

    }

    public User updateUser(Long id, String email, String name, Long level) {

        //Todo : restaurantService의 예외처리 참고
        User user = userRepository.findById(id).orElse(null);

        user.setName(name);
        user.setEmail(email);
        user.setLevel(level);

        return user;
    }

    public User deactiveUser(Long id) {

        User user = userRepository.findById(id).orElse(null);

        user.deactive();

        return user;
    }
}
