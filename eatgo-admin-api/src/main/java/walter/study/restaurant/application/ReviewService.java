package walter.study.restaurant.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import walter.study.restaurant.domain.Review;
import walter.study.restaurant.domain.ReviewRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;


    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }
}
