package com.development.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/weather")
public class WeatherResourse {

    @GetMapping("/city")
    public String getWeatherDetails(HttpServletRequest httpServletRequest){
        int user_id = (Integer) httpServletRequest.getAttribute("User_id");
        return "User is logged in successfully "+user_id;
    }

}
