package com.project.SmartPick.classes.role;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcRoleRepository implements RoleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcRoleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Role findById(int roleId) {
        String sql = "SELECT role_id, name FROM role WHERE role_id = ?";
        return jdbcTemplate.queryForObject(sql, new RoleRowMapper(), roleId);
    }
}