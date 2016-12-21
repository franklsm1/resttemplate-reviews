package com.galvanize;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PagesController {

    @GetMapping("/")
    public String getHomePage() {
        return "Home page";
    }

    @GetMapping("/about")
    public String getAboutPage() {
        return "About page";
    }

}
