package com.project.SmartPick.classes.product;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ProductCategoryRowMapper implements RowMapper<ProductCategory> {
    
    @Override
    public ProductCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(rs.getInt("category_id"));
        productCategory.setName(rs.getString("name"));

        return productCategory;
    }
}