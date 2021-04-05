package walter.study.restaurant.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import walter.study.restaurant.domain.MenuItem;
import walter.study.restaurant.domain.MenuItemRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public void bulkUpdate(Long restaurantId, List<MenuItem> menuItems){

        for(MenuItem menuItem: menuItems){
            menuItem.setRestaurantId(restaurantId);
            menuItemRepository.save(menuItem);
        }

    }
}
