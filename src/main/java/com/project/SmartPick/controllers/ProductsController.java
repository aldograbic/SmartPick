package com.project.SmartPick.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.SmartPick.classes.product.Product;
import com.project.SmartPick.classes.product.ProductRepository;

@Controller
@RequestMapping("/products")
public class ProductsController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductsController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String getAllProducts(Model model) {
        List<Product> products = productRepository.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("categories", productRepository.getAllProductCategories());
        return "products";
    }

    @GetMapping("/{gender}")
    public String getProductsByGender(@PathVariable String gender, Model model) {
        List<Product> products = productRepository.findByGender(gender);
        model.addAttribute("products", products);
        model.addAttribute("categories", productRepository.getAllProductCategories());
        return "products";
    }

    @GetMapping("/{gender}/{category}")
    public String getProductsByGenderAndCategory(
            @PathVariable String gender,
            @PathVariable String category,
            Model model) {
        
        List<Product> products;

        if ("all".equalsIgnoreCase(gender)) {
            products = productRepository.findByCategoryName(category);
        } else {
            products = productRepository.findByGenderAndCategoryName(gender, category);
        }

        model.addAttribute("products", products);
        model.addAttribute("categories", productRepository.getAllProductCategories());
        return "products";
    }

}
