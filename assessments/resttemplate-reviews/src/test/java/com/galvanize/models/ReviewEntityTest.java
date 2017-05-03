package com.galvanize.models;


import com.galvanize.repositories.ReviewRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReviewEntityTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    public void testBuildReviewEntity(){

        Long movieId = 1L;
        String reviewer = "Yarrl";
        String comment = "THAT WAS AWESOMEEEE";
        Double starRating = 5.0;

        Review review = new Review();
        review.setMovieId(movieId);
        review.setReviewer(reviewer);
        review.setComment(comment);
        review.setStarRating(starRating);

//        Review reviewedMovie = reviewRepository.findOne(1L);

        assertEquals(review.getReviewer(), reviewer);
        assertEquals(review.getComment(), comment);
        assertEquals(review.getMovieId(), movieId);
        assertEquals(review.getStarRating(), starRating);
    }
}
