package walter.study.restaurant.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import walter.study.restaurant.application.exception.EmailExistedException;
import walter.study.restaurant.domain.User;
import walter.study.restaurant.domain.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class UserServiceTest {


    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        //순서 주의. 순서바꾸면 안된다.
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    @DisplayName("유저등록-정상적인 동작")
    public void registerUser() {

        String email = "tester@example.com";
        String name = "Tester";
        String password = "test";

        userService.registerUser(email, name, password);

        verify(userRepository).save(any());
    }

    @Test
    @DisplayName("유저등록-이미 존재하는 이미메일")
    public void registerUserWithExistedEmail() {

        String email = "tester@example.com";
        String name = "Tester";
        String password = "test";

        User mockUser = User.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();

        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));


        assertThrows(EmailExistedException.class, () -> {
            userService.registerUser(email, name, password);
        });

        verify(userRepository, never()).save(any());
    }
}