package com.development.api.service;

import com.development.api.Exception.EtAuthException;
import com.development.api.model.User;
import com.development.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public User ValidateUser(String email, String password) throws EtAuthException {
        if(email != null) email = email.toLowerCase(Locale.ROOT);

        return userRepository.findByEmailAndPassword(email,password);
    }

    @Override
    public User RegisterUser(String Username, String email, String password) throws EtAuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if(email != null) email = email.toLowerCase(Locale.ROOT);

        if(!pattern.matcher(email).matches())
            throw new EtAuthException("Invalid Email format");

        Integer count = userRepository.getCountByEmail(email);
        if(count>0)
            throw new EtAuthException("Already registered");
        Integer UserId = userRepository.create(Username,email,password);

        return userRepository.findById(UserId);
    }
}
