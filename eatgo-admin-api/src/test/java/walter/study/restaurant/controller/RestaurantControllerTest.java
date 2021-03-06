package walter.study.restaurant.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import walter.study.restaurant.application.RestaurantService;
import walter.study.restaurant.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void list() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .name("JOKER HOUSE")
                .address("Seoul")
                .build());

        given(restaurantService.getRestaurants())
                .willReturn(restaurants);

        mockMvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1004")))
                .andExpect(content().string(containsString("\"name\":\"JOKER HOUSE\"")));

    }

    @Test
    public void detailWithExisted() throws Exception {

        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .categoryId(1L)
                .address("Seoul")
                .build();

        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant);

        mockMvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1004")))
                .andExpect(content().string(containsString("\"name\":\"Bob zip\"")));

    }

    @Test
    public void detailWithNotExisted() throws Exception {
        given(restaurantService.getRestaurant(404L))
                .willThrow(new RestaurantNotFoundException(404L));

        mockMvc.perform(get("/restaurants/404"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}"));
    }

    @Test
    public void createWithInValidData() throws Exception {

        given(restaurantService.addRestaurant(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            return Restaurant.builder()
                    .id(1234L)
                    .name(restaurant.getName())
                    .categoryId(1L)
                    .address(restaurant.getAddress())
                    .build();
        });

        mockMvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"categoryId\":1,\"name\":\"\"," +
                        "\"address\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createWithValidData() throws Exception {

        given(restaurantService.addRestaurant(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            return Restaurant.builder()
                    .id(1234L)
                    .name(restaurant.getName())
                    .categoryId(1L)
                    .address(restaurant.getAddress())
                    .build();
        });

        mockMvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"categoryId\":1,\"name\":\"Beryong\"," +
                        "\"address\":\"Busan\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/restaurants/1234"))
                .andExpect(content().string("{}"));

        verify(restaurantService).addRestaurant(any());
    }

    @Test
    public void updateWithInValid() throws Exception {

        mockMvc.perform(patch("/restaurants/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"categoryId\":1,\"name\":\"\", \"address\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWithOutName() throws Exception {

        mockMvc.perform(patch("/restaurants/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"categoryId\":1,\"name\":\"\", \"address\":\"Busan\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWithValid() throws Exception {

        mockMvc.perform(patch("/restaurants/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"categoryId\":1, \"name\":\"JOKER BAR\", \"address\":\"Busan\"}"))
                .andExpect(status().isOk());

        verify(restaurantService).updateRestaurant(1004L, "JOKER BAR", "Busan");
    }
}