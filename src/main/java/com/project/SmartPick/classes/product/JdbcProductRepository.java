package com.project.SmartPick.classes.product;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    @Override
    public List<ProductCategory> getAllProductCategories() {
        String sql = "SELECT * FROM product_category";
        return jdbcTemplate.query(sql, new ProductCategoryRowMapper());
    }

    @Override
    public List<Product> findByGender(String gender) {
        String sql = "SELECT * FROM product WHERE gender = ?";
        return jdbcTemplate.query(sql, new ProductRowMapper(), gender);
    }

    @Override
    public List<Product> findByGenderAndCategoryName(String gender, String categoryName) {
        String sql = "SELECT p.*, c.name AS category_name FROM product p " +
                     "JOIN product_category c ON p.category_id = c.category_id " +
                     "WHERE p.gender = ? AND c.name = ?";
        return jdbcTemplate.query(sql, new ProductRowMapper(), gender, categoryName);
    }

    @Override
    public List<Product> findByCategoryName(String categoryName) {
        String sql = "SELECT p.*, c.name AS category_name FROM product p " +
                     "JOIN product_category c ON p.category_id = c.category_id " +
                     "WHERE c.name = ?";
        return jdbcTemplate.query(sql, new ProductRowMapper(), categoryName);
    }
}
