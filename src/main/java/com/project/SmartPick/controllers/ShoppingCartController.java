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
public class ShoppingCartController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;
    
    @GetMapping("/shopping-cart")
    public String getShoppingCartPage(Model model) {
        List<Product> products = productRepository.getAllProductsInShoppingCartByUserId(getUserIdFromAuthentication());
        Map<Integer, Boolean> savedStatusMap = getSavedStatusMap(products);
        model.addAttribute("savedStatusMap", savedStatusMap);

        return "shopping-cart";
    }

    private int getUserIdFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return userRepository.findByUsername(username).getUserId();
    }

    private Map<Integer, Boolean> getSavedStatusMap(List<Product> products) {
        int userId = getUserIdFromAuthentication();
        Map<Integer, Boolean> savedStatusMap = new HashMap<>();
        for (Product product : products) {
            boolean isSaved = productRepository.hasUserSavedProduct(userId, product.getProductId());
            savedStatusMap.put(product.getProductId(), isSaved);
        }

        return savedStatusMap;
    }

    @PostMapping("/putProductInShoppingCartForUser")
    public String putProductInShoppingCartForUser(@RequestParam("productId") int productId, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        int userId = userRepository.findByUsername(username).getUserId();
        String productName = productRepository.getProductByProductId(productId).getName();

        if (productRepository.isProductInShoppingCart(productId, userId)) {
            redirectAttributes.addFlashAttribute("infoMessage", "Item '" + productName + "' is already in the shopping cart.");
            return "redirect:" + getPreviousPageUrl(request);
        }
    
        try {
            productRepository.putProductInShoppingCartForUser(productId, userId);
            redirectAttributes.addFlashAttribute("successMessage", "Item '" + productName + "' has been successfully added to the shopping cart.");
    
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred when putting the product in the shopping cart. Please try again.");
            return "redirect:" + getPreviousPageUrl(request);
        }
    
        return "redirect:" + getPreviousPageUrl(request);
    }

    private String getPreviousPageUrl(HttpServletRequest request) {
        return request.getHeader("Referer");
    }

    @PostMapping("/removeProductFromShoppingCartForUser")
    public String removeProductFromShoppingCartForUser(@RequestParam("productId") int productId, RedirectAttributes redirectAttributes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        int userId = userRepository.findByUsername(username).getUserId();
        String productName = productRepository.getProductByProductId(productId).getName();

        try {
            productRepository.removeProductFromShoppingCartForUser(userId, productId);
            redirectAttributes.addFlashAttribute("successMessage", "Item '" + productName + "' has been successfully removed from shopping cart.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred when removing the product from the shopping cart. Please try again.");
            return "redirect:/shopping-cart";
        }

        return "redirect:/shopping-cart";
    }
}
