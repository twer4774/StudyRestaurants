package walter.study.restaurant.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTest {

    @Test
    public void creation(){
        Category category = Category.builder().name("Korean Food").build();

        assertEquals(category.getName(), "Korean Food");
    }

}