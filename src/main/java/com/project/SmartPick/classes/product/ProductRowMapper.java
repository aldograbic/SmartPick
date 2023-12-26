package com.project.SmartPick.classes.product;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setProductId(rs.getInt("product_id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setSize(rs.getString("size"));
        product.setColor(rs.getString("color"));
        product.setGender(rs.getString("gender"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setCreatedAt(rs.getTimestamp("created_at"));

        ProductCategory category = new ProductCategory();
        category.setCategoryId(rs.getInt("category_id"));
        category.setName(rs.getString("name"));
        product.setCategory(category);

        return product;
    }
}
