package walter.study.restaurant.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import walter.study.restaurant.application.ReviewService;
import walter.study.restaurant.application.UserService;
import walter.study.restaurant.domain.Review;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import walter.study.restaurant.application.ReviewService;
import walter.study.restaurant.domain.Review;
import walter.study.restaurant.domain.User;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserControllerTest {



    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;


    @Test
    public void create() throws Exception {

        String email = "tester@example.com";
        String name = "Tester";
        String password = "test";

        User mockUser = User.builder()
                .id(1004L)
                .email(email)
                .name(name)
                .password(password)
                .build();

        given(userService.registerUser(email, name, password)).willReturn(mockUser);

        //Email. Name, Password
        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"tester@example.com\", \"name\":\"Tester\", \"password\":\"test\"}")
        )
                .andExpect(status().isCreated())         //return 201(create 성공)
                .andExpect(header().string("location", "/users/1004"));


        verify(userService).registerUser(eq("tester@example.com"), eq("Tester"), eq("test"));

    }

}