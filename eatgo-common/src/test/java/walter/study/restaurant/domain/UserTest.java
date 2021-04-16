package walter.study.restaurant.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    public void creation(){
        User user = User.builder()
                .email("tester@example.com")
                .name("tester")
                .level(100L)
                .build();

        assertEquals(user.getName(), "tester");
        assertEquals(user.isAdmin(), true);
    }


    @Test
    public void accessTokenWithPassword(){
        User user = User.builder().password("ACCESSTOKE").build();

        assertEquals(user.getAccessToken(), "ACCESSTOKE");
    }

    @Test
    public void accessTokenWithoutPassword(){
        User user = new User();

        assertEquals(user.getAccessToken(), "");
    }

}