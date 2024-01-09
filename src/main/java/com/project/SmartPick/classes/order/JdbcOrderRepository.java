package com.project.SmartPick.classes.order;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createOrder(Order order) {
        String sql = "INSERT INTO order (user_id, order_total, order_date) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, order.getUserId(), order.getOrderTotal(), order.getOrderDate());
    }

    @Override
    public int getLastInsertedOrderId() {
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    @Override
    public void createOrderItem(OrderItem orderItem) {
        String sql = "INSERT INTO order_item (order_id, product_id, quantity, total_price) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, orderItem.getOrderId(), orderItem.getProductId(), orderItem.getQuantity(), orderItem.getTotalPrice());
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        String sql = "SELECT * FROM order_item WHERE order_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrderItem.class), orderId);
    }
}