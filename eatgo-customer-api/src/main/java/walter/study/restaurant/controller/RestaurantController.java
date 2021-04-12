package walter.study.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import walter.study.restaurant.application.RestaurantService;
import walter.study.restaurant.domain.Region;
import walter.study.restaurant.domain.Restaurant;

import java.awt.*;
import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
public class RestaurantController {


    private final RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public List<Restaurant> list(@RequestParam("region") String region) {
        List<Restaurant> restaurants = restaurantService.getRestaurants(region);

        return restaurants;
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant detail(@PathVariable("id") Long id) {

        Restaurant restaurant = restaurantService.getRestaurant(id);

        return restaurant;
    }

}

