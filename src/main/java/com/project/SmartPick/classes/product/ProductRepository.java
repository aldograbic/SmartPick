package com.project.SmartPick.classes.product;

import java.util.List;

public interface ProductRepository {

    List<Product> getAllProducts();

    List<Product> findByGender(String gender);

    List<Product> findByGenderAndCategoryName(String gender, String categoryName);

    List<Product> findByCategoryName(String categoryName);

    List<Product> gellAllSavedProductsByUserId(int userId);

    List<Product> getAllProductsInShoppingCartByUserId(int userId);

    void saveProductForUser(int productId, int userId);

    void putProductInShoppingCartForUser(int productId, int userId);

    void removeSavedProductForUser(int userId, int productId);

    void removeProductFromShoppingCartForUser(int userId, int productId);

    boolean hasUserSavedProduct(int userId, int productId);

    boolean isProductInShoppingCart(int productId, int userId);

    Product getProductByProductId(int productId);

    List<Product> getAllProductsByPrompt(String prompt);

    List<Product> getAllFilteredProducts(String size, String color, Integer minPrice, Integer maxPrice);

    List<Product> getAllFilteredProductsWithGender(String gender, String size, String color, Integer minPrice, Integer maxPrice);

    List<Product> getAllFilteredProductsWithGenderAndCategory(String gender, String category, String size, String color, Integer minPrice, Integer maxPrice);

    List<Product> getAllFilteredProductsWithCategory(String category, String size, String color, Integer minPrice, Integer maxPrice);

    List<Product> getLastAddedProducts();

    void removeAllProductsFromShoppingCartForUser(int userId);

    List<Product> getMostViewedProducts();

    int getViewCountForProduct(int productId);

    List<Product> getMostPurchasedProducts();

    int getPurchaseCountForProduct(int productId);

    List<Product> findSimilarProducts(String gender, String category, int excludeProductId);
}
