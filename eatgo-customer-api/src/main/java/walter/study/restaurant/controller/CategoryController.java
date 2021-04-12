package walter.study.restaurant.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import walter.study.restaurant.application.CategoryService;
import walter.study.restaurant.domain.Category;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CategoryController {


    private final CategoryService categoryService;

    @GetMapping("/categories")
    public List<Category> list(){


        List<Category> categories = categoryService.getCategories();

        return categories;
    }

}
