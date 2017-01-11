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
public class Endpoint03Test {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void it_returns_the_correctly_formatted_response() throws Exception {
        HashMap<String, String> requestJson = new HashMap<>();
        requestJson.put("fname", "Ty");
        requestJson.put("lname", "Taylor");

        ResponseEntity<String> response = template.postForEntity("/drivers", requestJson, String.class);

        assertThat(response.getStatusCode(), IsEqual.equalTo(HttpStatus.OK));
        assertThat(response.getBody(), hasJsonPath("$.firstName", equalTo("Ty")));
        assertThat(response.getBody(), hasJsonPath("$.lastName", equalTo("Taylor")));

        requestJson.put("fname", "Ann");
        requestJson.put("lname", "Anders");
        ResponseEntity<String> response2 = template.postForEntity("/drivers", requestJson, String.class);

        assertThat(response2.getStatusCode(), IsEqual.equalTo(HttpStatus.OK));
        assertThat(response2.getBody(), hasJsonPath("$.firstName", equalTo("Ann")));
        assertThat(response2.getBody(), hasJsonPath("$.lastName", equalTo("Anders")));
    }

}
