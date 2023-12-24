package com.project.SmartPick.classes.user;

import java.util.List;

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
        String sql = "SELECT user_id, first_name, last_name, username, email, password, profile_image, email_verified, confirmation_token, created_at, user_id FROM user WHERE username = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(roleRepository), username);

        return users.isEmpty() ? null : users.get(0);
    }
}
