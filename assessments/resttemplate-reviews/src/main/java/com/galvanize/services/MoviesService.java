package com.galvanize.services;

import com.galvanize.models.MovieResult;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

@Service
public class MoviesService {

    private final RestTemplate template = new RestTemplate();
    private final String API_URL = "http://localhost:9090/movies";
    private final String API_KEY = "18394928-6684-43ca-9026-e19ed3028b17";

    public RestTemplate getRestTemplate() {
        return template;
    }

    public MovieResult forTitleYear(String title, int year) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + API_KEY);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL + "/find")
                .queryParam("title", title)
                .queryParam("year", year);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            return template
                    .exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, MovieResult.class)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public MovieResult postMovie(MovieResult movie) {

        URI uri = UriComponentsBuilder
                .fromUriString("{host}")
                .buildAndExpand(API_URL)
                .toUri();

        RequestEntity<?> request = RequestEntity.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + API_KEY)
                .body(movie);

        ResponseEntity<MovieResult> response = template.exchange(request, MovieResult.class);

        return  response.getBody();
    }
}
