package com.project.SmartPick.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String getSavedProductsPage() {
        return "saved-products";
    }

    @PostMapping("/saveProductForUser")
    public String saveProductForUser(@RequestParam("productId") int productId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        int userId = userRepository.findByUsername(username).getUserId();

        try {
            productRepository.saveProductForUser(productId, userId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred when saving the product. Please try again.");
            return "redirect:" + getPreviousPageUrl(request);
        }

        return "redirect:" + getPreviousPageUrl(request);
    }

    private String getPreviousPageUrl(HttpServletRequest request) {
        return request.getHeader("Referer");
    }
}