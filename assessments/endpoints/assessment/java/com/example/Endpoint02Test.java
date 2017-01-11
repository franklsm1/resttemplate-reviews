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
public class Endpoint02Test {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void it_returns_the_correctly_formatted_response() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/drivers/235/trips/2008/04/02", String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody(), containsString("Showing trips for driver 235 on 2008-04-02"));

        ResponseEntity<String> response2 = template.getForEntity("/drivers/100/trips/2010/10/11", String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response2.getBody(), containsString("Showing trips for driver 100 on 2010-10-11"));
    }

}
