package com.galvanize.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.models.MovieResult;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

public class MoviesServiceTest {
    private final MoviesService service = new MoviesService();

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void callsMockRestTemplateWithTitleAndReturnsAMovie() throws Exception {
        String title = "Gremlins";
        int year = 1984;
        int id = 5;

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(service.getRestTemplate());

        mockServer.expect(requestTo(stringContainsInOrder(Arrays.asList("http://localhost:9090/movies/find?", "title=Gremlins", "year=1984"))))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(getJSON("/" + title + ".json"), MediaType.APPLICATION_JSON));

        MovieResult result = service.forTitleYear("Gremlins", 1984);

        assertEquals(result.getTitle(), title);
        assertEquals(result.getYear(), year);
        assertEquals(result.getId(), id);

        mockServer.verify();
    }

    @Test
    public void callsMockRestTemplateWithNonExistentMovieShouldReturnNoContent() throws Exception {
        String title = "abc";
        int year = 0;

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(service.getRestTemplate());

        mockServer.expect(requestTo(stringContainsInOrder(Arrays.asList("http://localhost:9090/movies/find?", "title=" + title, "year=" + year))))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        MovieResult result = service.forTitleYear(title, year);

        assertEquals(null, result);

        mockServer.verify();
    }

    @Test
    public void callsMockRestTemplatePostANewMovie() throws Exception {
        String title = "abc";
        int year = 2017;
        long id = 1;

        MovieResult movie = new MovieResult();
        movie.setTitle(title);
        movie.setYear(year);
        movie.setId(id);

        String movieJson = objectMapper.writeValueAsString(movie);

        String createdLocation = "/movies/" + 1;


        MockRestServiceServer mockServer = MockRestServiceServer.createServer(service.getRestTemplate());

        mockServer.expect(requestTo(stringContainsInOrder(Arrays.asList("http://localhost:9090/movies"))))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withCreatedEntity(URI.create(createdLocation)).body(movieJson).contentType(MediaType.APPLICATION_JSON));

        MovieResult result = service.postMovie(movie);

        assertEquals(result.getTitle(), title);
        assertEquals(result.getYear(), year);
        assertEquals(result.getId(), id);

        mockServer.verify();
    }

    private String getJSON(String path) throws Exception {
        URL url = this.getClass().getResource(path);
        return new String(Files.readAllBytes(Paths.get(url.getFile())));
    }
}
