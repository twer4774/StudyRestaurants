package walter.study.restaurant.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import walter.study.restaurant.application.UserService;
import walter.study.restaurant.application.exception.EmailNotExistedException;
import walter.study.restaurant.application.exception.PasswordWrongException;
import walter.study.restaurant.domain.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SessionController.class)
class SessionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;


    @Test
    @DisplayName("정상적인 사용자 인증 성공")
    public void createWithValidAttributes() throws Exception {

        String email = "tester@example.com";
        String password = "test";

        User mockUser = User.builder()
                .password("ACCESSTOKEN")
                .build();

        given(userService.authenticate(email, password)).willReturn(mockUser);


        //Email. Password
        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"tester@example.com\", \"password\":\"test\"}")
        )
                .andExpect(status().isCreated())         //return 201(create 성공)
                .andExpect(header().string("location", "/session"))
                .andExpect(content().string("{\"accessToken\":\"ACCESSTOKE\"}"));

        verify(userService).authenticate(eq(email), eq(password));

    }


    @Test
    @DisplayName("이메일이 없는 경우")
    public void createWithNotExistedEmail() throws Exception {

        given(userService.authenticate("x@example.com", "test")).willThrow(EmailNotExistedException.class);

        //Email. Password
        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"x@example.com\", \"password\":\"test\"}")
        )
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(eq("x@example.com"), eq("test"));

    }

    @Test
    @DisplayName("패스워드가 틀린경우")
    public void createWithWrongPassword() throws Exception {

        given(userService.authenticate("tester@example.com", "x")).willThrow(PasswordWrongException.class);

        //Email. Password
        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"tester@example.com\", \"password\":\"x\"}")
        )
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(eq("tester@example.com"), eq("x"));

    }
}