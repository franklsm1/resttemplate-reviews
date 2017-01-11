package com.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest extends GalvanizeApiTest {

    @Test
    public void testCreate() throws Exception {
        JsonObject person = new JsonObject();
        person.addProperty("firstName", "Edna" + String.valueOf(new Random().nextInt()));

        String url = "/people";
        JsonObject createdPerson = post(url, person).getAsJsonObject();
        JsonArray personList = get(url).getAsJsonArray();

        Long idFromCreate = createdPerson.get("id").getAsLong();
        Long idFromIndex = personList.get(0).getAsJsonObject().get("id").getAsLong();
        assertThat(idFromCreate, equalTo(idFromIndex));
    }

    @Test
    public void testShow() throws Exception {
        JsonObject person = new JsonObject();
        person.addProperty("firstName", "Edna" + String.valueOf(new Random().nextInt()));

        JsonObject createdPerson = post("/people", person).getAsJsonObject();
        String idFromCreate = createdPerson.get("id").getAsString();

        JsonObject showPerson = get("/people/" + idFromCreate).getAsJsonObject();

        assertThat(showPerson.get("id").getAsString(), equalTo(idFromCreate));
        assertThat(showPerson.get("firstName").getAsString(), equalTo(person.get("firstName").getAsString()));
    }

    @Test
    public void testUpdate() throws Exception {
        JsonObject person = new JsonObject();
        String originalValue = "Aaron" + String.valueOf(new Random().nextInt());
        String newValue = "Theodosia" + String.valueOf(new Random().nextInt());

        person.addProperty("firstName", originalValue);
        JsonObject createdPerson = post("/people", person).getAsJsonObject();
        String idFromCreate = createdPerson.get("id").getAsString();

        person.addProperty("id", idFromCreate);
        person.addProperty("firstName", newValue);

        patch("/people/" + idFromCreate, person).getAsJsonObject();
        JsonObject showPerson = get("/people/" + idFromCreate).getAsJsonObject();

        assertThat(showPerson.get("id").getAsString(), equalTo(idFromCreate));
        assertThat(showPerson.get("firstName").getAsString(), equalTo(newValue));
    }

    @Test
    public void testDelete() throws Exception {
        JsonObject person = new JsonObject();

        person.addProperty("firstName", "Aaron" + String.valueOf(new Random().nextInt()));
        JsonObject createdPerson = post("/people", person).getAsJsonObject();
        String idFromCreate = createdPerson.get("id").getAsString();

        delete("/people/" + idFromCreate);
        JsonArray personList = get("/people").getAsJsonArray();

        assertThat(personList.size(), equalTo(0));
    }

}
