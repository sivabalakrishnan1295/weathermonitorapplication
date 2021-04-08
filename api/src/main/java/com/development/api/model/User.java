package com.development.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;


public class User {

    private int User_id;
    private String Username;
    private String Email;
    private String Password;

    public User(int user_id, String username, String email, String password) {
        User_id = user_id;
        Username = username;
        Email = email;
        Password = password;
    }


    public int getUser_id() {
        return User_id;
    }

    public void setUser_id(int user_id) {
        User_id = user_id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
