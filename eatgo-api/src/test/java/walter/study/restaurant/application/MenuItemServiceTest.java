package walter.study.restaurant.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import walter.study.restaurant.domain.MenuItem;
import walter.study.restaurant.domain.MenuItemRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MenuItemServiceTest {

    private MenuItemService menuItemService;

    @Mock
    private MenuItemRepository menuItemRepository;

    @BeforeEach
    public void setUp(){

        MockitoAnnotations.openMocks(this);

         menuItemService = new MenuItemService(menuItemRepository);
    }

    @Test
    public void bulkUpdate(){
        List<MenuItem> menuItems = new ArrayList<MenuItem>();

        //추가
        menuItems.add(MenuItem.builder()
                .name("Kimchi")
                .build());

        //수정
        menuItems.add(MenuItem.builder()
                .id(12L)
                .name("Gukbob")
                .build());

        //삭제
        menuItems.add(MenuItem.builder()
                .id(1004L)
                .destroy(true)
                .build());

        menuItemService.bulkUpdate(1L, menuItems);

        verify(menuItemRepository, times(2)).save(any());
        verify(menuItemRepository, times(1)).deleteById(eq(1004L));
    }
}