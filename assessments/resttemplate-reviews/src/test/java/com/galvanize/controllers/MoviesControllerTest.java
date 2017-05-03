package com.galvanize.controllers;

import com.galvanize.models.MovieResult;
import com.galvanize.services.MoviesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class MoviesControllerTest {

    @MockBean
    MoviesService moviesService;

    @Test
    public void testGetReviewGetsGremlins() throws Exception {

        when(moviesService.forTitleYear("Gremlins", 1984)).thenReturn(new MovieResult());

        MoviesController moviesController = new MoviesController(moviesService);
        moviesController.getMovie("Gremlins", 1984);

        verify(moviesService).forTitleYear("Gremlins", 1984);
    }
}
