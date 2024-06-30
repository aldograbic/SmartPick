package com.project.SmartPick.classes.productCategory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcProductCategoryRepository implements ProductCategoryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ProductCategory> getAllProductCategories() {
        String sql = "SELECT * FROM product_categories";
        return jdbcTemplate.query(sql, new ProductCategoryRowMapper());
    }

    @Override
    public ProductCategory getProductCategoryByCategoryId(int categoryId) {
        String sql = "SELECT * FROM product_categories WHERE category_id = ?";
        return jdbcTemplate.queryForObject(sql, new ProductCategoryRowMapper(), categoryId);
    } 
}