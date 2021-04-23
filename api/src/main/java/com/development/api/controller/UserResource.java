package com.development.api.controller;

import com.development.api.Constants;
import com.development.api.model.User;
import com.development.api.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/users")
public class UserResource {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> LoginUser(@RequestBody Map<String, Object> userMap) {
        String email = (String) userMap.get("Email");
        String Password = (String) userMap.get("Password");
        User user = userService.ValidateUser(email,Password);
//        Map<String,String> map = new HashMap<>();
//        map.put("message","Logged in Successfully");
        return new ResponseEntity<>(generateJWTToken(user),HttpStatus.OK);
    }


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

    private Map<String ,String > generateJWTToken(User user){
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.SECRET_KEY_API)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp+Constants.TOKEN_VALIDITY))
                .claim("User_id",user.getUser_id())
                .claim("Username",user.getUsername())
                .claim("Email",user.getEmail())
                .compact();

        Map<String,String> map = new HashMap<>();
        map.put("token",token);
        return map;

    }
}
