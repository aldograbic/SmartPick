package com.project.SmartPick.classes.product;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.SmartPick.classes.productCategory.ProductCategoryRepository;

import java.util.ArrayList;
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
        String sql = "SELECT * FROM product ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new ProductRowMapper(productCategoryRepository));
    }

    @Override
    public List<Product> findByGender(String gender) {
        String sql = "SELECT * FROM product WHERE gender = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new ProductRowMapper(productCategoryRepository), gender);
    }

    @Override
    public List<Product> findByGenderAndCategoryName(String gender, String categoryName) {
        String sql = """
                     SELECT p.*, c.name AS category_name FROM product p \
                     JOIN product_category c ON p.category_id = c.category_id \
                     WHERE p.gender = ? AND c.name = ? ORDER BY created_at DESC\
                     """;
        return jdbcTemplate.query(sql, new ProductRowMapper(productCategoryRepository), gender, categoryName);
    }

    @Override
    public List<Product> findByCategoryName(String categoryName) {
        String sql = """
                     SELECT p.*, c.name AS category_name FROM product p \
                     JOIN product_category c ON p.category_id = c.category_id \
                     WHERE c.name = ? ORDER BY created_at DESC\
                     """;
        return jdbcTemplate.query(sql, new ProductRowMapper(productCategoryRepository), categoryName);
    }

    @Override
    public List<Product> gellAllSavedProductsByUserId(int userId) {
        String sql = """
                     SELECT product.* FROM user_likes \
                     JOIN product ON user_likes.product_id = product.product_id \
                     WHERE user_likes.user_id = ?\
                     """;
        return jdbcTemplate.query(sql, new ProductRowMapper(productCategoryRepository), userId);
    }

    @Override
    public List<Product> getAllProductsInShoppingCartByUserId(int userId) {
        String sql = """
                     SELECT product.* FROM user_shopping_cart \
                     JOIN product ON user_shopping_cart.product_id = product.product_id \
                     WHERE user_shopping_cart.user_id = ?\
                     """;
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
    public List<Product> getAllFilteredProducts(String size, String color, Integer minPrice, Integer maxPrice) {
        String sql = "SELECT * FROM product WHERE 1=1 ";

        List<Object> params = new ArrayList<>();

        if (size != null && !size.isEmpty()) {
            sql += "AND size = ? ";
            params.add(size);
        }

        if (color != null && !color.isEmpty()) {
            sql += "AND color LIKE ? ";
            params.add("%" + color + "%");
        }

        if (minPrice != null) {
            sql += "AND price >= ? ";
            params.add(minPrice);
        }

        if (maxPrice != null) {
            sql += "AND price <= ? ";
            params.add(maxPrice);
        }

        return jdbcTemplate.query(sql, new ProductRowMapper(productCategoryRepository), params.toArray());
    }

    @Override
    public List<Product> getAllFilteredProductsWithGender(String gender, String size, String color, Integer minPrice, Integer maxPrice) {
        String sql = "SELECT * FROM product WHERE gender = ?";

        List<Object> params = new ArrayList<>();
        params.add(gender);

        if (size != null && !size.isEmpty()) {
            sql += " AND size = ? ";
            params.add(size);
        }

        if (color != null && !color.isEmpty()) {
            sql += " AND color LIKE ? ";
            params.add("%" + color + "%");
        }

        if (minPrice != null) {
            sql += " AND price >= ? ";
            params.add(minPrice);
        }

        if (maxPrice != null) {
            sql += " AND price <= ? ";
            params.add(maxPrice);
        }

        return jdbcTemplate.query(sql, new ProductRowMapper(productCategoryRepository), params.toArray());
    }

    @Override
    public List<Product> getAllFilteredProductsWithCategory(String category, String size, String color, Integer minPrice, Integer maxPrice) {
        String sql = "SELECT * FROM product WHERE category_id = (SELECT category_id FROM product_category WHERE name = ?)";

        List<Object> params = new ArrayList<>();
        params.add(category);

        if (size != null && !size.isEmpty()) {
            sql += " AND size = ? ";
            params.add(size);
        }

        if (color != null && !color.isEmpty()) {
            sql += " AND color LIKE ? ";
            params.add("%" + color + "%");
        }

        if (minPrice != null) {
            sql += " AND price >= ? ";
            params.add(minPrice);
        }

        if (maxPrice != null) {
            sql += " AND price <= ? ";
            params.add(maxPrice);
        }

        return jdbcTemplate.query(sql, new ProductRowMapper(productCategoryRepository), params.toArray());
    }

    @Override
    public List<Product> getAllFilteredProductsWithGenderAndCategory(String gender, String category, String size, String color, Integer minPrice, Integer maxPrice) {
        String sql = "SELECT * FROM product WHERE gender = ? AND category_id = (SELECT category_id FROM product_category WHERE name = ?)";

        List<Object> params = new ArrayList<>();
        params.add(gender);
        params.add(category);

        if (size != null && !size.isEmpty()) {
            sql += " AND size = ? ";
            params.add(size);
        }

        if (color != null && !color.isEmpty()) {
            sql += " AND color LIKE ? ";
            params.add("%" + color + "%");
        }

        if (minPrice != null) {
            sql += " AND price >= ? ";
            params.add(minPrice);
        }

        if (maxPrice != null) {
            sql += " AND price <= ? ";
            params.add(maxPrice);
        }

        return jdbcTemplate.query(sql, new ProductRowMapper(productCategoryRepository), params.toArray());
    }

    @Override
    public List<Product> getLastAddedProducts() {
        String sql = "SELECT * FROM product ORDER BY created_at DESC LIMIT 5";
        return jdbcTemplate.query(sql, new ProductRowMapper(productCategoryRepository));
    }

    @Override
    public void removeAllProductsFromShoppingCartForUser(int userId) {
        String sql = "DELETE FROM user_shopping_cart WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }

    @Override
    public List<Product> getMostViewedProducts() {
        String sql = """
                    SELECT p.*, COUNT(ub.behavior_id) AS view_count 
                    FROM product p
                    JOIN user_behavior ub ON p.product_id = ub.product_id
                    WHERE ub.behavior_type = 'view'
                    GROUP BY p.product_id, p.name, p.description, p.size, p.color, p.gender, p.price, p.image, p.created_at, p.category_id
                    ORDER BY view_count DESC
                    LIMIT 5
                    """;
        List<Product> products = jdbcTemplate.query(sql, new ProductRowMapper(productCategoryRepository));
        
        for (Product product : products) {
            int viewCount = getViewCountForProduct(product.getProductId());
            product.setViewCount(viewCount);
        }
        
        return products;
    }

    @Override
    public int getViewCountForProduct(int productId) {
        String sql = "SELECT COUNT(behavior_id) FROM user_behavior WHERE product_id = ? AND behavior_type = 'view'";
        return jdbcTemplate.queryForObject(sql, Integer.class, productId);
    }

    @Override
    public List<Product> getMostPurchasedProducts() {
        String sql = """
                    SELECT p.*, COUNT(ub.behavior_id) AS purchase_count 
                    FROM product p
                    JOIN user_behavior ub ON p.product_id = ub.product_id
                    WHERE ub.behavior_type = 'purchase'
                    GROUP BY p.product_id, p.name, p.description, p.size, p.color, p.gender, p.price, p.image, p.created_at, p.category_id
                    ORDER BY purchase_count DESC
                    LIMIT 5
                    """;
        List<Product> products = jdbcTemplate.query(sql, new ProductRowMapper(productCategoryRepository));
        
        for (Product product : products) {
            int purchaseCount = getPurchaseCountForProduct(product.getProductId());
            product.setPurchaseCount(purchaseCount);
        }
        
        return products;
    }

    @Override
    public int getPurchaseCountForProduct(int productId) {
        String sql = "SELECT COUNT(behavior_id) FROM user_behavior WHERE product_id = ? AND behavior_type = 'purchase'";
        return jdbcTemplate.queryForObject(sql, Integer.class, productId);
    }
}