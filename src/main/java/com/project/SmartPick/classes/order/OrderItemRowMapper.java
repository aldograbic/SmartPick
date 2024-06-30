package com.project.SmartPick.classes.order;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.SmartPick.classes.product.Product;
import com.project.SmartPick.classes.product.ProductRepository;

public class OrderItemRowMapper implements RowMapper<OrderItem> {

    private final ProductRepository productRepository;

    public OrderItemRowMapper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderItem orderItem = new OrderItem();

        orderItem.setOrderItemId(rs.getInt("order_item_id"));
        orderItem.setOrderId(rs.getInt("order_id"));
        orderItem.setProductId(rs.getInt("product_id"));
        orderItem.setQuantity(rs.getInt("quantity"));
        orderItem.setTotalPrice(rs.getBigDecimal("total_price"));

        int productId = rs.getInt("product_id");
        Product product = productRepository.getProductByProductId(productId);
        orderItem.setProduct(product);

        return orderItem;
    }
}