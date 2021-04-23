package com.development.api.repository;

import com.development.api.Exception.EtAuthException;
import com.development.api.model.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private static final String SQL_CREATE = "INSERT INTO UserAccountInformation(User_id,Username,Email,Password) VALUES (NEXTVAL('user_seq'),?,?,?)";

    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM UserAccountInformation WHERE EMAIL = ?";

    private static final String SQL_FIND_BY_ID = "SELECT USER_ID, Username, Email, Password FROM UserAccountInformation WHERE USER_ID = ?";

    private static final String SQL_FIND_BY_EMAIL = "SELECT USER_ID, Username, Email, Password FROM UserAccountInformation WHERE EMAIL = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(String username, String email, String password) throws EtAuthException {
        String hashedPassword = BCrypt.hashpw(password,BCrypt.gensalt(10));
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,username);
                ps.setString(2,email);
                ps.setString(3,hashedPassword);
                return ps;
            },keyHolder);
            return (Integer) keyHolder.getKeys().get("USER_ID");
        }catch (Exception e){
            throw new EtAuthException("Invalid details"+e.getMessage());
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws EtAuthException {
        try{
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL,new Object[]{email},userRowMapper);
            if(!BCrypt.checkpw(password,user.getPassword()))
                throw  new EtAuthException("Password is invalid");
            return user;
        }catch (EmptyResultDataAccessException e){
            throw new EtAuthException("Invalid Email and Password");
        }
    }

    @Override
    public Integer getCountByEmail(String Email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL,new Object[]{Email},Integer.class);

    }

    @Override
    public User findById(Integer userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID,new Object[]{userId},userRowMapper);
    }

    private RowMapper<User> userRowMapper = ((rs,rowNum)->{
        return new User (rs.getInt("user_id"),
                rs.getString("Username"),
                rs.getString("Email"),
                rs.getString("Password"));
    });
}
