package com.project.SmartPick.controllers;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.SmartPick.classes.product.Product;
import com.project.SmartPick.classes.product.ProductRepository;
import com.project.SmartPick.classes.productCategory.ProductCategoryRepository;
import com.project.SmartPick.classes.user.UserRepository;
import com.project.SmartPick.modelServices.RecommendationService;

@Controller
public class IndexController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/")
    public String getIndexPage(Model model, @AuthenticationPrincipal User user) {
        List<Product> menProducts = productRepository.findByGender("Men");
        List<Product> womenProducts = productRepository.findByGender("Women");
        List<Product> childrenProducts = productRepository.findByGender("Children");
        List<Product> lastAddedProducts = productRepository.getLastAddedProducts();
        Map<Integer, Boolean> savedStatusMap = getSavedStatusMap(lastAddedProducts);

        model.addAttribute("menProducts", menProducts);
        model.addAttribute("womenProducts", womenProducts);
        model.addAttribute("childrenProducts", childrenProducts);
        model.addAttribute("lastAddedProducts", lastAddedProducts);
        model.addAttribute("categories", productCategoryRepository.getAllProductCategories());
        model.addAttribute("savedStatusMap", savedStatusMap);

        if (user != null) {
            com.project.SmartPick.classes.user.User loggedUser = userRepository.findByUsername(user.getUsername());
            int userId = loggedUser.getUserId();
            List<Integer> recommendedProductIds = recommendationService.getRecommendations(userId);

            List<Product> recommendedProducts = new ArrayList<>();
            for (Integer productId : recommendedProductIds) {
                Product product = productRepository.getProductByProductId(productId);
                if (product != null) {
                    recommendedProducts.add(product);
                }
            }
            model.addAttribute("recommendedProducts", recommendedProducts);
        }

        model.addAttribute("indexController", this);
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

    public static boolean isWithinLastWeek(LocalDateTime createdAt) {
        LocalDateTime now = LocalDateTime.now();
        long daysDifference = ChronoUnit.DAYS.between(createdAt, now);
        return daysDifference < 7;
    }
}
