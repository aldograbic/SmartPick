package com.project.SmartPick.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.project.SmartPick.classes.product.Product;
import com.project.SmartPick.classes.product.ProductRepository;
import com.project.SmartPick.classes.user.User;
import com.project.SmartPick.classes.user.UserRepository;

import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public GlobalControllerAdvice(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            
            User user = userRepository.findByUsername(username);

            if (user != null) {
                int userId = user.getUserId();

                List<Product> productsSavedByUser = productRepository.gellAllSavedProductsByUserId(userId);
                model.addAttribute("productsSavedByUser", productsSavedByUser);

                List<Product> productsInShoppingCart = productRepository.getAllProductsInShoppingCartByUserId(userId);
                model.addAttribute("productsInShoppingCart", productsInShoppingCart);
            }
        }
    }
}