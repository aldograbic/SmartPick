package com.project.SmartPick.controllers;

import java.util.List;

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



@Controller
public class DashboardController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @GetMapping("/user-dashboard")
    public String getUserDashboardPage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

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

        return "user-dashboard";
    }

    @GetMapping("/admin-dashboard")
    public String getAdminDashboardPage() {
        return "admin-dashboard";
    }
}
