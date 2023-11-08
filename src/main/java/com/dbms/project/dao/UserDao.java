package com.dbms.project.dao;

import com.dbms.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
@Repository
public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertUser(User user) {
        final String sql = "INSERT INTO User(username, password, designation) VALUES(?, ?, ?)";
        KeyHolder keyholder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql.toUpperCase(), Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, Integer.parseInt(user.getUsername()));
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getDesignation());
            return ps;
        }, keyholder);
    }

    public void CreateTable(){
        String sql = "CREATE TABLE IF NOT EXISTS USER(USERNAME int primary key, " +
                "PASSWORD varchar(255), " +
                "DESIGNATION ENUM('Student', 'Admin', 'Company') NOT NULL DEFAULT ('Student'))";
        jdbcTemplate.execute(sql);
    }


    public List<User> getAllUsers() {
        final String sql = "SELECT * from USER";
        return jdbcTemplate.query(sql.toUpperCase(), new BeanPropertyRowMapper<>(User.class));
    }

    public Optional<User> findUserByUsername(Integer username) {
        final String sql = "SELECT * from user WHERE username = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql.toUpperCase(), new Object[] {username}, new BeanPropertyRowMapper<>(User.class)));
    }

    public boolean alreadyExists(String username){
        String query = "SELECT COUNT(*) FROM user WHERE username = ?";
        int rs =  this.jdbcTemplate.queryForObject(query.toUpperCase(), new Object[]{username}, Integer.class);
        return (rs != 0);
    }
    public String getDesignationFromID(Integer ID){
        String sql = "SELECT DESIGNATION FROM USER WHERE username = ?";
        return jdbcTemplate.queryForObject(sql.toUpperCase(), new Object[]{ID}, String.class);
    }
    public void updateUser(User user){
        final String updateSql = "UPDATE user SET " +
                "password = ? " +
                "WHERE username = ?";

        jdbcTemplate.update(updateSql.toUpperCase(),
                user.getPassword(),
                user.getUsername()
        );
    }

    public void insertAdmin(){
        String sql = "SELECT COUNT(*) FROM USER WHERE DESIGNATION = \"Admin\"";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        if(count > 0) return;
        User user = new User();
        user.setUsername(1);
        user.setPassword("$2a$10$nIf1GsCNzWEvT1dijSQpuOrkS6.e5OEXkDIuC3n5k56LLa4cqE3D2");
        user.setDesignation("Admin");
        insertUser(user);
    }

}
