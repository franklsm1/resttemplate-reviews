package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndpointsTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void testCreate() throws Exception {
        //
        // ResponseEntity<Person> createPersonResponse = template.postForEntity("/people", person, Person.class);
        //
        // ResponseEntity<List<Person>> response = template.exchange(
        //         "/people",
        //         HttpMethod.GET,
        //         null,
        //         new ParameterizedTypeReference<List<Person>>() {
        //         }
        // );
        //
        // assertThat(response.getBody().get(0).getId(), equalTo(createPersonResponse.getBody().getId()));
    }
}
