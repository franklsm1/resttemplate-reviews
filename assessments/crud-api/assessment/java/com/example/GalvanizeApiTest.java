package com.example;

import com.google.gson.*;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GalvanizeApiTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    TestRestTemplate template;

    // This is here to add support for sending `PATCH` requests with a RestTemplate
    // It requires the org.apache.httpcomponents:httpclient
    // This can be removed once the app is in Spring 5: https://github.com/spring-projects/spring-boot/issues/6578
    @Before
    public void setupTestRestTemplateSoItCanSendPatchRequests() {
        this.template.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Before
    public void clearDatabase() {
        this.jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");

        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList("SELECT TABLE_NAME " +
                "FROM INFORMATION_SCHEMA.TABLES " +
                "WHERE TABLE_SCHEMA='PUBLIC'");

        for (Map<String, Object> row : rows) {
            this.jdbcTemplate.execute("TRUNCATE TABLE " + row.get("TABLE_NAME"));
        }

        List<Map<String, Object>> sequenceRows = this.jdbcTemplate.queryForList("SELECT SEQUENCE_NAME " +
                "FROM INFORMATION_SCHEMA.SEQUENCES " +
                "WHERE SEQUENCE_SCHEMA='PUBLIC';");

        for (Map<String, Object> row : sequenceRows) {
            this.jdbcTemplate.execute("ALTER SEQUENCE " + row.get("SEQUENCE_NAME") + " RESTART WITH 1");
        }

        this.jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }

    protected JsonElement get(String url) {
        ResponseEntity<String> responseEntity = requestWithoutBody(url, HttpMethod.GET);
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        return new JsonParser().parse(responseEntity.getBody());
    }

    protected JsonElement post(String url, JsonObject payload) {
        return requestWithBody(url, payload, HttpMethod.POST);
    }

    protected JsonElement patch(String url, JsonObject payload) {
        return requestWithBody(url, payload, HttpMethod.PATCH);
    }

    protected void delete(String url) {
        ResponseEntity<String> responseEntity = requestWithoutBody(url, HttpMethod.DELETE);
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
    }

    protected ResponseEntity<String> requestWithoutBody(String url, HttpMethod method) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        return template.exchange(url, method, entity, String.class);
    }

    protected JsonElement requestWithBody(String url, JsonObject payload, HttpMethod method) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Gson builder = new GsonBuilder().create();
        String jsonString = builder.toJson(payload);

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonString, headers);
        ResponseEntity<String> responseEntity = template.exchange(url, method, requestEntity, String.class);

        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        return new JsonParser().parse(responseEntity.getBody());
    }
}
