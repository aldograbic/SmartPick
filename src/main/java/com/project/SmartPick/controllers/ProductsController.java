package com.project.SmartPick.controllers;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.SmartPick.classes.product.Product;
import com.project.SmartPick.classes.product.ProductRepository;
import com.project.SmartPick.classes.productCategory.ProductCategoryRepository;
import com.project.SmartPick.classes.user.User;
import com.project.SmartPick.classes.user.UserRepository;
import com.project.SmartPick.classes.userBehavior.UserBehavior;
import com.project.SmartPick.classes.userBehavior.UserBehaviorRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/products")
public class ProductsController {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final UserRepository userRepository;
    private final UserBehaviorRepository userBehaviorRepository;

    public ProductsController(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository, UserRepository userRepository, UserBehaviorRepository userBehaviorRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.userRepository = userRepository;
        this.userBehaviorRepository = userBehaviorRepository;
    }

    @GetMapping
    public String getAllProducts(@RequestParam(required = false) String size,
                                @RequestParam(required = false) String color,
                                @RequestParam(required = false) Integer minPrice,
                                @RequestParam(required = false) Integer maxPrice,
                                Model model) {

        List<Product> products;

        if (size != null || color != null || minPrice != null || maxPrice != null) {
            products = productRepository.getAllFilteredProducts(size, color, minPrice, maxPrice);
        } else {
            products = productRepository.getAllProducts();
        }

        Map<Integer, Boolean> savedStatusMap = getSavedStatusMap(products);

        model.addAttribute("products", products);
        model.addAttribute("categories", productCategoryRepository.getAllProductCategories());
        model.addAttribute("savedStatusMap", savedStatusMap);
        model.addAttribute("productsController", this);

        return "products";
    }

    @GetMapping("/{gender}")
    public String getProductsByGender(@PathVariable String gender,
                                    @RequestParam(required = false) String size,
                                    @RequestParam(required = false) String color,
                                    @RequestParam(required = false) Integer minPrice,
                                    @RequestParam(required = false) Integer maxPrice,
                                    Model model) {

        List<Product> products;

        if (size != null || color != null || minPrice != null || maxPrice != null) {
            products = productRepository.getAllFilteredProductsWithGender(gender, size, color, minPrice, maxPrice);
        } else {
            products = productRepository.findByGender(gender);
        }
        
        Map<Integer, Boolean> savedStatusMap = getSavedStatusMap(products);

        model.addAttribute("products", products);
        model.addAttribute("categories", productCategoryRepository.getAllProductCategories());
        model.addAttribute("savedStatusMap", savedStatusMap);
        model.addAttribute("productsController", this);

        return "products";
    }

    @GetMapping("/{gender}/{category}")
    public String getProductsByGenderAndCategory(@PathVariable String gender,
                                                @PathVariable String category,
                                                @RequestParam(required = false) String size,
                                                @RequestParam(required = false) String color,
                                                @RequestParam(required = false) Integer minPrice,
                                                @RequestParam(required = false) Integer maxPrice,
                                                Model model) {
        
        List<Product> products;

        if ("all".equalsIgnoreCase(gender)) {
            products = productRepository.getAllFilteredProductsWithCategory(category, size, color, minPrice, maxPrice);
        } else if (size != null || color != null || minPrice != null || maxPrice != null) {
            products = productRepository.getAllFilteredProductsWithGenderAndCategory(gender, category, size, color, minPrice, maxPrice);
        } else {
            products = productRepository.findByGenderAndCategoryName(gender, category);
        }

        Map<Integer, Boolean> savedStatusMap = getSavedStatusMap(products);

        model.addAttribute("products", products);
        model.addAttribute("categories", productCategoryRepository.getAllProductCategories());
        model.addAttribute("savedStatusMap", savedStatusMap);
        model.addAttribute("productsController", this);

        return "products";
    }

    @GetMapping("/{gender}/{category}/{productId}")
    public String getProduct(
            @PathVariable String gender,
            @PathVariable String category,
            @PathVariable int productId,
            Model model,
            Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {

            String username = authentication.getName();
            User user = userRepository.findByUsername(username);
    
            UserBehavior userBehavior = new UserBehavior();
            userBehavior.setUserId(user.getUserId());
            userBehavior.setProductId(productId);
            userBehavior.setBehaviorType("view");
            userBehaviorRepository.saveBehavior(userBehavior);
        }
        
        List<Product> products;

        if ("all".equalsIgnoreCase(gender)) {
            products = productRepository.findByCategoryName(category);
        } else {
            products = productRepository.findByGenderAndCategoryName(gender, category);
        }

        Map<Integer, Boolean> savedStatusMap = getSavedStatusMap(products);

        model.addAttribute("products", products);
        model.addAttribute("categories", productCategoryRepository.getAllProductCategories());
        model.addAttribute("savedStatusMap", savedStatusMap);

        Product product = productRepository.getProductByProductId(productId);
        model.addAttribute("product", product);

        List<Product> similarProducts = productRepository.findSimilarProducts(gender, category, productId);
        model.addAttribute("similarProducts", similarProducts);

        Map<Integer, Boolean> similarProductsSavedStatusMap = getSavedStatusMap(similarProducts);
        model.addAttribute("similarProductsSavedStatusMap", similarProductsSavedStatusMap);

        model.addAttribute("productsController", this);

        return "product-details";
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

    @ModelAttribute("highlightedCategory")
    public String getHighlightedCategory(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/men")) {
            return "men";
        } else if (requestURI.contains("/women")) {
            return "women";
        } else if (requestURI.contains("/children")) {
            return "children";
        } else {
            return "";
        }
    }
    
    @GetMapping("/search")
    public String searchProducts(@RequestParam String search,
                                @RequestParam(required = false) String size,
                                @RequestParam(required = false) String color,
                                @RequestParam(required = false) Integer minPrice,
                                @RequestParam(required = false) Integer maxPrice,
                                Model model) {
        
        List<Product> searchResults;

        if (size != null || color != null || minPrice != null || maxPrice != null) {
            searchResults = productRepository.getAllFilteredProducts(size, color, minPrice, maxPrice);
        } else {
            searchResults = productRepository.getAllProductsByPrompt(search);
        }

        Map<Integer, Boolean> savedStatusMap = getSavedStatusMap(searchResults);

        model.addAttribute("products", searchResults);
        model.addAttribute("categories", productCategoryRepository.getAllProductCategories());
        model.addAttribute("savedStatusMap", savedStatusMap);
        model.addAttribute("productsController", this);

        return "products";
    }
}