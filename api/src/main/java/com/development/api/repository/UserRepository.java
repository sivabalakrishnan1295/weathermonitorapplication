package com.development.api.repository;

import com.development.api.Exception.EtAuthException;
import com.development.api.model.User;
import org.springframework.stereotype.Repository;


public interface UserRepository {
    Integer create(String username,String email,String password) throws EtAuthException;

    User findByEmailAndPassword(String email,String password) throws EtAuthException;

    Integer getCountByEmail(String Email);

    User findById(Integer userId);
}
