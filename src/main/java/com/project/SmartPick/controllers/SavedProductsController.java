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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.SmartPick.classes.product.Product;
import com.project.SmartPick.classes.product.ProductRepository;
import com.project.SmartPick.classes.user.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SavedProductsController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/saved-products")
    public String getSavedProductsPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        int userId = userRepository.findByUsername(username).getUserId();

        List<Product> allProducts = productRepository.getAllProducts();

        Map<Integer, Boolean> savedStatusMap = new HashMap<>();
        for (Product product : allProducts) {
            boolean isSaved = productRepository.hasUserSavedProduct(userId, product.getProductId());
            savedStatusMap.put(product.getProductId(), isSaved);
        }

        model.addAttribute("products", allProducts);
        model.addAttribute("savedStatusMap", savedStatusMap);

        return "saved-products";
    }

    @PostMapping("/toggleSaveProductForUser")
    public String toggleSaveProductForUser(@RequestParam("productId") int productId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        int userId = userRepository.findByUsername(username).getUserId();

        try {
            if(!productRepository.hasUserSavedProduct(userId, productId)) {
                productRepository.saveProductForUser(productId, userId);
            } else {
                productRepository.removeSavedProductForUser(userId, productId);
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred when saving/removing the product. Please try again.");
            return "redirect:" + getPreviousPageUrl(request);
        }

        return "redirect:" + getPreviousPageUrl(request);
    }

    private String getPreviousPageUrl(HttpServletRequest request) {
        return request.getHeader("Referer");
    }
}