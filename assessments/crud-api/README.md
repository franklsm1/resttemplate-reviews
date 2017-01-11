# Building a CRUD API

Your challenge is to build and test a JSON API using Spring.

## Technical Requirements

- Your app may not use `spring-data-rest` or other packages that auto-generate the API
- Your app may use `spring-data-jpa` or JDBC Templates
- Your app should be tested with `@WebMvcTest` tests and tests should cover every endpoint
- You must either mock the database calls in the test OR clear the database between tests

## App Features

You must implement the following routes:

**POST /people and GET /people**

Given a POST to `/people` with the following body:

```json
{
  "firstName": "Hercules",
  "lastName": "Mulligan"
}
```

It should add a record to the database and return a JSON response like:

```json
{
  "id": 12,
  "firstName": "Hercules",
  "lastName": "Mulligan"
}
```

And a subsequent GET to `/people` should return a JSON response like:

```json
[
  {
    "id": 12,
    "firstName": "Hercules",
    "lastName": "Mulligan"
  }
]
```

**GET /people**

Given a GET to `/people`, when there are multiple people in the database, it should return a JSON response like:

```json
[
  {
    "id": 12,
    "firstName": "Hercules",
    "lastName": "Mulligan"
  },
  {
    "id": 14,
    "firstName": "John",
    "lastName": "Laurens"
  }
]
```

**GET /people/34**

Given a GET to `/people/34`, where `34` is the id of a person in the database, it should return a JSON response like:

```json
{
  "id": 34,
  "firstName": "Hercules",
  "lastName": "Mulligan"
}
```

**PATCH /people/34**

Given a PATCH to `/people/34` (where `34` is the id of a person in the database), and a request body like:

```json
{
  "id": 34,
  "firstName": "New",
  "lastName": "Name"
}
```

Then the record's `firstName` and `lastName` should be updated in the database, and it should return a JSON response like:

```json
{
  "id": 34,
  "firstName": "New",
  "lastName": "Name"
}
```

**DELETE /people/34**

Given a DELETE to `/people/34` (where `34` is the id of a person in the database), it should delete the person from the database and return an empty response with a status code of 200.

## Running the Assessment

When you believe you are done, run these two tasks:

```
./gradlew assess
./gradlew jacoco
```

The `assess` task runs a pre-written test suite that ensure that your API meets the requirements.

The `jacoco` task runs your tests, and checks them for sufficient code coverage.
