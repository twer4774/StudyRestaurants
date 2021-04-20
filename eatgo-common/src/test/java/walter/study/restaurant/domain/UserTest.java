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
    public void restaurantOwner() {
        User user = User.builder().level(1L).build();



        assertEquals(user.isRestaurantOwner(), false);

        user.setRestaurantId(1004L);
        assertEquals(user.isRestaurantOwner(), true);
        assertEquals(user.getRestaurantId(), 1004L);

    }

}