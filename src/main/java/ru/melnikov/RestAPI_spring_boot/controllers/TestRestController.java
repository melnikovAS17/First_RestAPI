package ru.melnikov.RestAPI_spring_boot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller//Или @RestController - @Controller + @ResponseBody
@RequestMapping("/api")
public class TestRestController {

    @ResponseBody
    @GetMapping("/hello")
    public String getStringHello(){
        return "Hello!";
    }
}
