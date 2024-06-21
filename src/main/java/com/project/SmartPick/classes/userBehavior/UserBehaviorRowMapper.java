package com.project.SmartPick.classes.userBehavior;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserBehaviorRowMapper implements RowMapper<UserBehavior> {

    @Override
    public UserBehavior mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        UserBehavior userBehavior = new UserBehavior();
        userBehavior.setBehaviorId(rs.getInt("behavior_id"));
        userBehavior.setUserId(rs.getInt("user_id"));
        userBehavior.setProductId(rs.getInt("product_id"));
        userBehavior.setBehaviorType(rs.getString("behavior_type"));
        userBehavior.setBehaviorDate(rs.getTimestamp("behavior_date"));

        return userBehavior;
    }
}