package com.example;

import org.springframework.web.bind.annotation.*;

@RestController
public class DriversController {

    @GetMapping("/drivers")
    public String getDrivers(@RequestParam(defaultValue = "active") String status, @RequestParam int rating) {
        return String.format("Looking for %s drivers with a rating of %d", status, rating);
    }

    @PostMapping("/drivers")
    public Driver createDriver(@RequestBody Driver driver) {
        return driver;
    }

    @GetMapping("/drivers/{driverId}/trips/{year}/{month}/{day}")
    public String getTrips(@PathVariable int driverId, @PathVariable int year, @PathVariable String month, @PathVariable String day) {
        return String.format("Showing trips for driver %d on %d-%s-%s", driverId, year, month, day);
    }

}
