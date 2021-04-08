package com.development.api.controller;

import com.development.api.model.User;
import com.development.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/users")
public class UserResource {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> registerUser(@RequestBody Map<String,Object> userMap){
        String Username = (String) userMap.get("Username");
        String Email = (String) userMap.get("Email");
        String Password = (String) userMap.get("Password");
        User user = userService.RegisterUser(Username,Email,Password);
        Map<String,String> map = new HashMap<>();
        map.put("message","Registered Successfully");
        return  new ResponseEntity<>(map, HttpStatus.OK);

    }
}
