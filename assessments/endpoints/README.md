# Spring Endpoints

Imagine that you are developing an API for a hypothetical ride-sharing application named TrollRide.

The CTO of TrollRide is a forward-thinking individual who has decided to use Spring Boot to build the API, but nobody on the team has used it before, so they issue the following challenge:

> Whoever builds the endpoints below (in Spring Boot) gets $1000 TrollCar Dollars

You decide to accept the challenge!

## How will you know you are done?

Run `./gradlew test jacoco` to run _your_ tests and check coverage.

Run `./gradlew assess` to run our test suite that verifies that your code meets the requirements.

## Technical Requirements

- You must write `@WebMvcTest`s for all endpoints
- You can name your controllers whatever you like

## Features

### Endpoint #1

- Create a route for `GET /drivers`
- Accept two query parameters, `status` and `rating`
- Render the string `Looking for <status> drivers with a rating of <rating>`
- `rating` is required
- `status` is optional, and defaults to `active`

**Example 1**

When given:

```
GET /drivers?status=inactive&rating=4
```

Then return:

```
Looking for inactive drivers with a rating of 4
```

**Example 2**

When given:

```
GET /drivers?rating=12
```

Then return:

```
Looking for active drivers with a rating of 12
```

**Example 3**

When given:

```
GET /drivers
```

Then return a "400 Bad Request"


### Endpoint #2

- Create a route for `GET /drivers/235/trips/2008/04/02`
- Accept path variables for `driverId`, `year`, `month` and `day`
- Render the string `Showing trips for driver <driverId> on <year>-<month>-<day>`

**Example**

When given:

```
GET /drivers/235/trips/2008/04/02
```

Then return:

```
Showing trips for driver 235 on 2008-04-02
```

### Endpoint #3

- Create a route for `POST /drivers` with a JSON body of:

  ```json
  {"fname": "some first name", "lname": "some last name"}
  ```
- Render the JSON response

  ```json
  {"firstName": "some first name", "lastName": "some last name"}
  ```

**Example**

When given a request like:

```
POST /drivers

{
  "fname": "Ty",
  "lname": "Taylor"
}
```

Then return:

```json
{
  "firstName": "Ty",
  "lastName": "Taylor"
}
```

### Endpoint #4

- Create a route for `POST /drivers/235/trips?verified=true` with a JSON body of:

 ```json
 {"trip": {"date": "2016-03-04", "startAddress": "123 Main", "endAddress": "234 Elm"}}
 ```
- Render the JSON response

  ```json
  {
    "driver": {
      "id": <driverId>
    },
    "trip": {
      "startAddress": "<startAddress>",
      "endAddress": "<endAddress>",
      "date": "<date>",
      "verified": true
    }
  }
  ```

**Example**

When given a request like:

```
POST /drivers/235/trips?verified=false

{
  "trip": {
    "date": "2016-03-04",
    "startAddress": "123 Main",
    "endAddress": "234 Elm"
  }
}
```

Then return:

```json
{
  "driver": {
    "id": 235
  },
  "trip": {
    "startAddress": "123 Main",
    "endAddress": "234 Elm",
    "date": "2016-03-04",
    "verified": false
  }
}
```
