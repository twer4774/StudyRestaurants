package walter.study.restaurant.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import walter.study.restaurant.application.exception.EmailNotExistedException;
import walter.study.restaurant.application.exception.PasswordWrongException;
import walter.study.restaurant.domain.User;
import walter.study.restaurant.domain.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class UserServiceTest {


    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp(){
        //순서 주의. 순서바꾸면 안된다.
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    @DisplayName("인증 - 올바른 인증")
    public void authenticateWithValidAttribute(){
        String email = "tester@example.com";
        String password = "test";

        User mockUser = User.builder()
                .email(email)
                .build();

        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));

        given(passwordEncoder.matches(any(), any())).willReturn(true);

        User user = userService.authenticate(email, password);

        assertEquals(user.getEmail(), email);
    }

    @Test
    @DisplayName("인증-이메일 없음")
    public void authenticateWithNotExistedEmail(){
        String email = "x@example.com";
        String password = "test";


        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        given(passwordEncoder.matches(any(), any())).willReturn(false);

        assertThrows(EmailNotExistedException.class, () -> {
            userService.authenticate(email, password);
        });

    }

    @Test
    @DisplayName("인증-패스워드 틀림")
    public void authenticateWithWrongPassword(){
        String email = "tester@example.com";
        String password = "x";


        User mockUser = User.builder().email(email).build();

        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));


        assertThrows(PasswordWrongException.class, () -> {
            userService.authenticate(email, password);
        });

    }
}