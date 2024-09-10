package com.example.Actuators.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Controller
@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping("/getdata")
    public Map<String,String> getData(){
        return Map.of("Name","Sandeep dhanni");
    }
}
