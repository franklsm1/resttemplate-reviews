package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Endpoint01Test {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void it_returns_correctly_when_both_parameters_are_present() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/drivers?status=inactive&rating=4", String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody(), containsString("Looking for inactive drivers with a rating of 4"));

        ResponseEntity<String> response2 = template.getForEntity("/drivers?status=paused&rating=3", String.class);

        assertThat(response2.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response2.getBody(), containsString("Looking for paused drivers with a rating of 3"));
    }

    @Test
    public void it_defaults_status_to_active() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/drivers?rating=12", String.class);

        assertThat("/drivers?rating=12 did not return a 200", response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody(), containsString("Looking for active drivers with a rating of 12"));
    }

    @Test
    public void it_errors_out_if_rating_is_missing() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/drivers", String.class);

        assertThat("/drivers did not return a BAD_REQUEST", response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }
}
