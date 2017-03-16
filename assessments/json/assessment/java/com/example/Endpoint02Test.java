package com.example;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Endpoint02Test {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void it_returns_the_correctly_formatted_response() throws Exception {
        HashMap<String, String> tripJson = new HashMap<>();
        tripJson.put("date", "2016-03-04");
        tripJson.put("startAddress", "123 Main");
        tripJson.put("endAddress", "234 Elm");

        HashMap<String, HashMap<String, String>> requestJson = new HashMap<>();
        requestJson.put("trip", tripJson);

        ResponseEntity<String> response = template.postForEntity("/drivers/235/trips?verified=true", requestJson, String.class);

        assertThat(response.getStatusCode(), IsEqual.equalTo(HttpStatus.OK));
        assertThat(response.getBody(), hasJsonPath("$.driver.id", equalTo(235)));
        assertThat(response.getBody(), hasJsonPath("$.trip.startAddress", equalTo("123 Main")));
        assertThat(response.getBody(), hasJsonPath("$.trip.endAddress", equalTo("234 Elm")));
        assertThat(response.getBody(), hasJsonPath("$.trip.date", equalTo("2016-03-04")));
        assertThat(response.getBody(), hasJsonPath("$.trip.verified", equalTo(true)));


        tripJson.put("date", "2010-03-04");
        tripJson.put("startAddress", "23 Main");
        tripJson.put("endAddress", "11 Elm");
        ResponseEntity<String> response2 = template.postForEntity("/drivers/86/trips?verified=false", requestJson, String.class);

        assertThat(response2.getStatusCode(), IsEqual.equalTo(HttpStatus.OK));
        assertThat(response2.getBody(), hasJsonPath("$.driver.id", equalTo(86)));
        assertThat(response2.getBody(), hasJsonPath("$.trip.startAddress", equalTo("23 Main")));
        assertThat(response2.getBody(), hasJsonPath("$.trip.endAddress", equalTo("11 Elm")));
        assertThat(response2.getBody(), hasJsonPath("$.trip.date", equalTo("2010-03-04")));
        assertThat(response2.getBody(), hasJsonPath("$.trip.verified", equalTo(false)));
    }

}
