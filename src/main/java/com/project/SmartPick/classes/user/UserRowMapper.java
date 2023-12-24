package com.project.SmartPick.classes.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.SmartPick.classes.role.Role;
import com.project.SmartPick.classes.role.RoleRepository;

public class UserRowMapper implements RowMapper<User> {

    private final RoleRepository roleRepository;

    public UserRowMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setProfileImage(rs.getString("profile_image"));
        user.setEmailVerified(rs.getBoolean("email_verified"));
        user.setConfirmationToken(rs.getString("confirmation_token"));
        user.setRoleId(rs.getInt("role_id"));

        int roleId = rs.getInt("role_id");
        Role role = roleRepository.findById(roleId);
        user.setRole(role);

        return user;
    }
}