package com.project.SmartPick.classes.product;

import org.springframework.jdbc.core.RowMapper;

import com.project.SmartPick.classes.productCategory.ProductCategory;
import com.project.SmartPick.classes.productCategory.ProductCategoryRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductRowMapper(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

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
        product.setCategoryId(rs.getInt("category_id"));
        
        int categoryId = rs.getInt("category_id");
        ProductCategory category = productCategoryRepository.getProductCategoryByCategoryId(categoryId);
        product.setCategory(category);

        return product;
    }
}
