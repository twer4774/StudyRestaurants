package walter.study.restaurant.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import walter.study.restaurant.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class RestaurantServiceTest {


    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;


    @BeforeEach
    public void setUp(){
        //initMocks(deprecated) -> openMocks
        MockitoAnnotations.openMocks(this);

        mockRestaurantRepository();


        restaurantService = new RestaurantService(restaurantRepository);
    }


    private void mockRestaurantRepository() {
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .name("Bob zip")
                .address("Seoul")
                .build();

        restaurants.add(restaurant);

        given(restaurantRepository.findAll()).willReturn(restaurants);

        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));
    }


    @Test
    public void getRestaurants(){
        List<Restaurant> restaurants = restaurantService.getRestaurants();

        Restaurant restaurant = restaurants.get(0);
        assertEquals(restaurant.getId(), 1004L);
    }

    @Test
    public void getRestaurantWithExisted(){
        Restaurant restaurant = restaurantService.getRestaurant(1004L);

        assertEquals(restaurant.getId(), 1004L);

    }

    @Test
    public void getRestaurantWithNotExisted(){

        //service의 getRestaurant에서 throw 추가 필요
        assertThrows(RestaurantNotFoundException.class, ()-> {
            Restaurant restaurant = restaurantService.getRestaurant(404L);
        });
    }

    @Test
    public void addRestaurant(){

        given(restaurantRepository.save(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            restaurant.setId(1234L);
            return restaurant;
        });

        Restaurant restaurant = Restaurant.builder()
                .name("BeRyong")
                .address("Busan")
                .build();


        Restaurant created = restaurantService.addRestaurant(restaurant);

        assertEquals(created.getId(), 1234L);
    }

    @Test
    public void updateRestaurant(){
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();

        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));

        restaurantService.updateRestaurant(1004L, "Sool zip", "Busan");

        assertEquals(restaurant.getName(), "Sool zip");
        assertEquals(restaurant.getAddress(), "Busan");
    }
}