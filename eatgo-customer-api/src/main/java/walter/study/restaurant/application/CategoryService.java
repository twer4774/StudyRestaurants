package walter.study.restaurant.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import walter.study.restaurant.domain.Category;
import walter.study.restaurant.domain.CategoryRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public List<Category> getCategories() {

        return categoryRepository.findAll();
    }

}
