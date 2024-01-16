package com.project.SmartPick.controllers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.SmartPick.classes.product.Product;
import com.project.SmartPick.classes.product.ProductRepository;
import com.project.SmartPick.classes.productCategory.ProductCategoryRepository;
import com.project.SmartPick.classes.user.UserRepository;

@Controller
public class IndexController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String getIndexPage(Model model) {

        List<Product> lastAddedProducts = productRepository.getLastAddedProducts();
        Map<Integer, Boolean> savedStatusMap = getSavedStatusMap(lastAddedProducts);

        model.addAttribute("lastAddedProducts", lastAddedProducts);
        model.addAttribute("categories", productCategoryRepository.getAllProductCategories());
        model.addAttribute("savedStatusMap", savedStatusMap);

        return "index";
    }

     private Map<Integer, Boolean> getSavedStatusMap(List<Product> products) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<Integer, Boolean> savedStatusMap = new HashMap<>();
    
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            String username = authentication.getName();
            int userId = userRepository.findByUsername(username).getUserId();
    
            for (Product product : products) {
                boolean isSaved = productRepository.hasUserSavedProduct(userId, product.getProductId());
                savedStatusMap.put(product.getProductId(), isSaved);
            }
        } else {
            for (Product product : products) {
                savedStatusMap.put(product.getProductId(), false);
            }
        }
    
        return savedStatusMap;
    }
}
