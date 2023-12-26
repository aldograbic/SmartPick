package com.project.SmartPick.classes.product;

import java.util.List;

public interface ProductRepository {

    List<Product> getAllProducts();

    List<ProductCategory> getAllProductCategories();

    List<Product> findByGender(String gender);

    List<Product> findByGenderAndCategoryName(String gender, String categoryName);

    List<Product> findByCategoryName(String categoryName);
}
