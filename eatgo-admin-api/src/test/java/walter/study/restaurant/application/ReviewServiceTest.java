package walter.study.restaurant.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import walter.study.restaurant.domain.Review;
import walter.study.restaurant.domain.ReviewRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ReviewServiceTest {


    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        reviewService = new ReviewService(this.reviewRepository);
    }

    @Test
    public void getReviews(){
        List<Review> mockReviews = new ArrayList<>();
        mockReviews.add(Review.builder().description("Cool").build());
        given(reviewRepository.findAll()).willReturn(mockReviews);


        List<Review> reviews = reviewService.getReviews();
        Review review = reviews.get(0);

        assertEquals(review.getDescription(), "Cool");
    }

}