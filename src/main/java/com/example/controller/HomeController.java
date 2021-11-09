package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home() {
        return "welcome to e-commerce-shop";
    }

    @GetMapping("secured")
    public String homeSecured() {
        return "SECURED: welcome to e-commerce-shop";
    }
}
