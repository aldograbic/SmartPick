package com.project.SmartPick.classes.user;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.SmartPick.classes.role.RoleRepository;

@Repository
public class JdbcUserRepository implements UserRepository{
    
    private final JdbcTemplate jdbcTemplate;
    private final RoleRepository roleRepository;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate, RoleRepository roleRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.roleRepository = roleRepository;
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT user_id, first_name, last_name, username, email, password, profile_image, address, city, email_verified, confirmation_token, created_at, role_id FROM user WHERE username = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(roleRepository), username);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User findByEmail(String email) {
        String sql = "SELECT user_id, first_name, last_name, username, email, password, profile_image, address, city, email_verified, confirmation_token, created_at, role_id FROM user WHERE email = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(roleRepository), email);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public void saveUser(User user) {
        String sql = "INSERT INTO user (first_name, last_name, username, email, password, address, city, email_verified, confirmation_token, role_id) " + 
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getPassword(), user.isEmailVerified(), user.getConfirmationToken(), user.getRoleId());
    }

    @Override
    public User findByConfirmationToken(String token) {
        String sql = "SELECT user_id, first_name, last_name, username, email, password, profile_image, address, city, email_verified, confirmation_token, created_at, role_id " +
                    "FROM user WHERE confirmation_token = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(roleRepository), token);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateEmailVerification(User user) {
        String sql = "UPDATE user SET email_verified = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, user.isEmailVerified(), user.getUserId());
    }

    @Override
    public void contactUs(String name, String email, String message) {
        String sql = "INSERT INTO contact_message(name, email, message) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, name, email, message);
    }
}
