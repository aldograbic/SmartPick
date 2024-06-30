package com.project.SmartPick.classes.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcRoleRepository implements RoleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Role findById(int roleId) {
        String sql = "SELECT role_id, name FROM roles WHERE role_id = ?";
        return jdbcTemplate.queryForObject(sql, new RoleRowMapper(), roleId);
    }
}