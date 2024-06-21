package com.project.SmartPick.classes.userBehavior;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserBehaviorRepository implements UserBehaviorRepository{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveBehavior(UserBehavior userBehavior) {
        String sql = "INSERT INTO user_behavior (user_id, product_id, behavior_type) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, userBehavior.getUserId(), userBehavior.getProductId(), userBehavior.getBehaviorType());
    }

    @Override
    public List<UserBehavior> findAll() {
        String sql = "SELECT * FROM user_behavior";
        return jdbcTemplate.query(sql, new UserBehaviorRowMapper());
    }

    @Override
    public List<UserBehavior> findByUserId(int userId) {
        String sql = "SELECT * FROM user_behavior WHERE user_id = ?";
        return jdbcTemplate.query(sql, new UserBehaviorRowMapper(), userId);
    }
}