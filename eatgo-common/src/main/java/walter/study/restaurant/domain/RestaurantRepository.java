package walter.study.restaurant.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    List<Restaurant> findAll();

    List<Restaurant> findAllByAddressContainingAndCategoryId(String region, long categoryId);

    Optional<Restaurant> findById(Long id);

    Restaurant save(Restaurant Restaurant);
}
