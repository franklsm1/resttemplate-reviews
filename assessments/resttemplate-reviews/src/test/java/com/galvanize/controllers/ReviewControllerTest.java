package com.galvanize.controllers;

import com.galvanize.models.MovieResult;
import com.galvanize.models.Review;
import com.galvanize.repositories.ReviewRepository;
import com.galvanize.services.MoviesService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.Arrays;

import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    MockMvc mockMvc;

    Review review = new Review();
    MovieResult movie = new MovieResult();
    JSONObject mockReview;
    JSONObject mockMovie;

    @Before
    public void before() throws JSONException {
        review.setReviewer("Yarrl");
        review.setComment("THAT WAS AWESOMEEEE");
        review.setStarRating(5.0);

        movie.setTitle("Gremlins");
        movie.setYear(1984);

        mockReview = new JSONObject();
        mockReview.put("title", movie.getTitle());
        mockReview.put("year", movie.getYear());
        mockReview.put("reviewer", review.getReviewer());
        mockReview.put("comment", review.getComment());
        mockReview.put("starRating", review.getStarRating());

        mockMovie = new JSONObject();
        mockMovie.put("title", movie.getTitle());
        mockMovie.put("year", movie.getYear());

    }

    @Test
    @Transactional
    @Rollback
    public void testCreateExistingMovieReviewController() throws Exception {

        MockHttpServletRequestBuilder request = post("/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mockReview.toString());

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movie.title", equalTo(movie.getTitle())))
                .andExpect(jsonPath("$.movie.year", equalTo(movie.getYear())))
                .andExpect(jsonPath("$.movie.id", equalTo(5)));

    }

    @Test
    @Transactional
    @Rollback
    public void testCreateNewMovieReviewController() throws Exception {

        movie.setTitle("fake15");
        movie.setId(15);
        mockReview.put("title", movie.getTitle());

        MoviesService service = new MoviesService();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(service.getRestTemplate());

        mockServer.expect(requestTo(stringContainsInOrder(Arrays.asList("http://localhost:9090/movies/find?", "title=" + movie.getTitle(), "year=" + movie.getYear()))))
                .andExpect(header("Authorization", "Bearer 18394928-6684-43ca-9026-e19ed3028b17"))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        mockServer.expect(requestTo("http://localhost/movies"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header("Authorization", "Bearer 18394928-6684-43ca-9026-e19ed3028b17"))
                .andExpect(MockRestRequestMatchers.jsonPath("$.title", equalTo(movie.getTitle())))
                .andExpect(MockRestRequestMatchers.jsonPath("$.year", equalTo(movie.getYear())))
                .andRespond(withStatus(HttpStatus.FOUND).body(mockMovie.toString()));

        MockHttpServletRequestBuilder request = post("/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mockReview.toString());

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movie.title", equalTo(movie.getTitle())))
                .andExpect(jsonPath("$.movie.year", equalTo(movie.getYear())))
                .andExpect(jsonPath("$.movie.id", instanceOf(Number.class)))
                .andExpect(jsonPath("$.review.id", instanceOf(Number.class)))
                .andExpect(jsonPath("$.review.reviewer", equalTo(review.getReviewer())))
                .andExpect(jsonPath("$.review.comment", equalTo(review.getComment())));

        mockServer.verify();
    }


}
