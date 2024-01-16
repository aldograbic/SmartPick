package com.project.SmartPick.classes.product;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.SmartPick.classes.productCategory.ProductCategoryRepository;

import java.util.List;

@Repository
public class JdbcProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ProductCategoryRepository productCategoryRepository;

    public JdbcProductRepository(JdbcTemplate jdbcTemplate, ProductCategoryRepository productCategoryRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, new ProductRowMapper(productCategoryRepository));
    }

    @Override
    public List<Product> findByGender(String gender) {
        String sql = "SELECT * FROM product WHERE gender = ?";
        return jdbcTemplate.query(sql, new ProductRowMapper(productCategoryRepository), gender);
    }

    @Override
    public List<Product> findByGenderAndCategoryName(String gender, String categoryName) {
        String sql = "SELECT p.*, c.name AS category_name FROM product p " +
                     "JOIN product_category c ON p.category_id = c.category_id " +
                     "WHERE p.gender = ? AND c.name = ?";
        return jdbcTemplate.query(sql, new ProductRowMapper(productCategoryRepository), gender, categoryName);
    }

    @Override
    public List<Product> findByCategoryName(String categoryName) {
        String sql = "SELECT p.*, c.name AS category_name FROM product p " +
                     "JOIN product_category c ON p.category_id = c.category_id " +
                     "WHERE c.name = ?";
        return jdbcTemplate.query(sql, new ProductRowMapper(productCategoryRepository), categoryName);
    }

    @Override
    public List<Product> gellAllSavedProductsByUserId(int userId) {
        String sql = "SELECT product.* FROM user_likes " + 
                     "JOIN product ON user_likes.product_id = product.product_id " +
                     "WHERE user_likes.user_id = ?";
        return jdbcTemplate.query(sql, new ProductRowMapper(productCategoryRepository), userId);
    }

    @Override
    public List<Product> getAllProductsInShoppingCartByUserId(int userId) {
        String sql = "SELECT product.* FROM user_shopping_cart " +
                     "JOIN product ON user_shopping_cart.product_id = product.product_id " +
                     "WHERE user_shopping_cart.user_id = ?";
        return jdbcTemplate.query(sql, new ProductRowMapper(productCategoryRepository), userId);
    }

    @Override
    public void saveProductForUser(int productId, int userId) {
        String sql = "INSERT INTO user_likes (user_id, product_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, productId);
    }

    @Override
    public void putProductInShoppingCartForUser(int productId, int userId) {
        String sql = "INSERT INTO user_shopping_cart (user_id, product_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, productId);
    }

    @Override
    public void removeSavedProductForUser(int userId, int productId) {
        String sql = "DELETE FROM user_likes WHERE user_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, userId, productId);
    }

    @Override
    public void removeProductFromShoppingCartForUser(int userId, int productId) {
        String sql = "DELETE FROM user_shopping_cart WHERE user_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, userId, productId);
    }

    @Override
    public boolean hasUserSavedProduct(int userId, int productId) {
        String sql = "SELECT COUNT(*) FROM user_likes WHERE user_id = ? AND product_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId, productId);
        return count != null && count > 0;
    }

    @Override
    public Product getProductByProductId(int productId) {
        String sql = "SELECT * FROM product WHERE product_id = ?";
        return jdbcTemplate.queryForObject(sql, new ProductRowMapper(productCategoryRepository), productId);
    }

    @Override
    public boolean isProductInShoppingCart(int productId, int userId) {
        String sql = "SELECT COUNT(*) FROM user_shopping_cart WHERE user_id = ? AND product_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId, productId);
        return count != null && count > 0;
    }


    @Override
    public List<Product> getAllProductsByPrompt(String prompt) {
        String sql = "SELECT * FROM product WHERE name LIKE ? OR description LIKE ?";
        String searchTerm = "%" + prompt + "%";
        
        return jdbcTemplate.query(sql, new ProductRowMapper(productCategoryRepository), searchTerm, searchTerm);
    }

    @Override
    public List<Product> getAllProductsBySize(String size) {
        String sql = "SELECT * FROM product WHERE size = ?";
        
        return jdbcTemplate.query(sql, new ProductRowMapper(productCategoryRepository), size);
    }

    @Override
    public List<Product> getAllProductsByColor(String color) {
        String sql = "SELECT * FROM product WHERE color = ?";
        
        return jdbcTemplate.query(sql, new ProductRowMapper(productCategoryRepository), color);
    }

    @Override
    public List<Product> getLastAddedProducts() {
        String sql = "SELECT * FROM product ORDER BY created_at DESC LIMIT 5";
        return jdbcTemplate.query(sql, new ProductRowMapper(productCategoryRepository));
    }
}
