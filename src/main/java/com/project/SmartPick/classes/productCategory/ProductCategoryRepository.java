package com.project.SmartPick.classes.productCategory;

import java.util.List;

public interface ProductCategoryRepository {
    
    List<ProductCategory> getAllProductCategories();

    ProductCategory getProductCategoryByCategoryId(int categoryId);
}
