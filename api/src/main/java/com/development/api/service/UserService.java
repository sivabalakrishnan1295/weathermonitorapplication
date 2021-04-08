package com.development.api.service;

import com.development.api.Exception.EtAuthException;
import com.development.api.model.User;

public interface UserService {

    User ValidateUser(String email, String password) throws EtAuthException;

    User RegisterUser(String Username,String email,String password) throws EtAuthException;

}
