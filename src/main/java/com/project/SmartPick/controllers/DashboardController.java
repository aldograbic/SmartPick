package com.project.SmartPick.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.SmartPick.classes.order.Order;
import com.project.SmartPick.classes.order.OrderItem;
import com.project.SmartPick.classes.order.OrderRepository;
import com.project.SmartPick.classes.product.Product;
import com.project.SmartPick.classes.product.ProductRepository;
import com.project.SmartPick.classes.user.User;
import com.project.SmartPick.classes.user.UserRepository;
import com.project.SmartPick.modelServices.RecommendationService;



@Controller
public class DashboardController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RecommendationService recommendationService;
    
    @GetMapping("/user-dashboard")
    public String getUserDashboardPage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        List<Integer> recommendedProductIds = recommendationService.getRecommendations(user.getUserId());
        List<Product> recommendedProducts = new ArrayList<>();
        for (Integer productId : recommendedProductIds) {
            Product product = productRepository.getProductByProductId(productId);
            if (product != null) {
                recommendedProducts.add(product);
            }
        }
        model.addAttribute("recommendedProducts", recommendedProducts);

        List<Order> previousOrders = orderRepository.getAllOrdersByUserId(user.getUserId());

        for (Order order : previousOrders) {
            List<OrderItem> orderItems = orderRepository.getOrderItemsByOrderId(order.getOrderId());
            order.setOrderItems(orderItems);

            for (OrderItem orderItem : orderItems) {
                int productId = orderItem.getProductId();
                Product product = productRepository.getProductByProductId(productId);
                orderItem.setProduct(product);
            }
        }

        model.addAttribute("previousOrders", previousOrders);

        Map<Integer, Boolean> savedStatusMap = getSavedStatusMap(recommendedProducts);
        model.addAttribute("savedStatusMap", savedStatusMap);

        return "user-dashboard";
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

    @GetMapping("/admin-dashboard")
    public String getAdminDashboardPage(Model model) {

        List<Product> mostViewedProducts = productRepository.getMostViewedProducts();
        model.addAttribute("mostViewedProducts", mostViewedProducts);

        List<Product> mostPurchasedProducts = productRepository.getMostPurchasedProducts();
        model.addAttribute("mostPurchasedProducts", mostPurchasedProducts);

        // Preuzimanje evaluacijskih metrika za sve korisnike
        List<User> users = userRepository.findAll();
        List<Map<String, String>> metricsList = new ArrayList<>();

        for (User user : users) {
            Map<String, String> metrics = recommendationService.getEvaluationMetrics(user.getUserId());
            metrics.put("username", user.getUsername()); // Add the username to the metrics map
            metricsList.add(metrics);
        }

        model.addAttribute("metricsList", metricsList);

        return "admin-dashboard";
    }
}
