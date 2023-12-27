package com.project.SmartPick.classes.product;

import java.util.List;

public interface ProductRepository {

    List<Product> getAllProducts();

    List<ProductCategory> getAllProductCategories();

    List<Product> findByGender(String gender);

    List<Product> findByGenderAndCategoryName(String gender, String categoryName);

    List<Product> findByCategoryName(String categoryName);

    List<Product> gellAllSavedProductsByUserId(int userId);

    List<Product> getAllProductsInShoppingCartByUserId(int userId);

    void saveProductForUser(int productId, int userId);

    void putProductInShoppingCartForUser(int productId, int userId);
}
