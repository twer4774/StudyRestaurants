package walter.study.restaurant.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import walter.study.restaurant.domain.User;
import walter.study.restaurant.domain.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class UserServiceTest {


    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        //순서 주의. 순서바꾸면 안된다.
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    public void registerUser(){

        String email = "tester@example.com";
        String name = "Tester";
        String password = "test";

        userService.registerUser(email, name, password);

        verify(userRepository).save(any());
    }

    @Test
    public void registerUserWithExistedEmail(){

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