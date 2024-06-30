package com.project.SmartPick.controllers;

import com.project.SmartPick.classes.user.User;
import com.project.SmartPick.classes.user.UserRepository;
import com.project.SmartPick.classes.userBehavior.UserBehavior;
import com.project.SmartPick.classes.userBehavior.UserBehaviorRepository;
import com.project.SmartPick.config.EmailService;
import com.project.SmartPick.classes.order.Order;
import com.project.SmartPick.classes.order.OrderItem;
import com.project.SmartPick.classes.order.OrderRepository;
import com.project.SmartPick.classes.product.Product;
import com.project.SmartPick.classes.product.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class CheckoutController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserBehaviorRepository userBehaviorRepository;

    @GetMapping("/checkout")
    public String getCheckoutPage(HttpSession session, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);

        BigDecimal estimatedTotal = (BigDecimal) session.getAttribute("estimatedTotal");
        model.addAttribute("estimatedTotal", estimatedTotal);
        
        @SuppressWarnings("unchecked")
        List<OrderItem> orderItems = (List<OrderItem>) session.getAttribute("orderItems");
        if(orderItems == null) {
            return "redirect:/";
        }

        for (OrderItem orderItem : orderItems) {
            int productId = orderItem.getProductId();
            Product product = productRepository.getProductByProductId(productId);
            orderItem.setProduct(product);
        }
        model.addAttribute("orderItems", orderItems);

        return "checkout";
    }

    @PostMapping("/order-confirmation")
    public String order(HttpSession session, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        BigDecimal estimatedTotal = (BigDecimal) session.getAttribute("estimatedTotal");
        @SuppressWarnings("unchecked")
        List<OrderItem> orderItems = (List<OrderItem>) session.getAttribute("orderItems");

        Order order = new Order(user.getUserId(), estimatedTotal);

        orderRepository.createOrder(order);

        Order savedOrder = orderRepository.findById(order.getOrderId());

        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderId(order.getOrderId());
            orderRepository.createOrderItem(orderItem);

            UserBehavior userBehavior = new UserBehavior();
            userBehavior.setUserId(user.getUserId());
            userBehavior.setProductId(orderItem.getProductId());
            userBehavior.setBehaviorType("purchase");
            userBehaviorRepository.saveBehavior(userBehavior);
        }

        model.addAttribute("userEmail", user.getEmail());
        model.addAttribute("order", savedOrder);

        String message = "Dear " + user.getUsername() + ",\n\n"
        + "Thank you for your order with SmartPick! We are delighted to confirm that your order has been successfully placed.\n\n"
        + "Order Details:\n"
        + "Order ID: " + savedOrder.getOrderId() + "\n"
        + "Order Date: " + savedOrder.getOrderDate() + "\n"
        + "Order Total: " + savedOrder.getOrderTotal() + " EUR\n\n"
        + "If you have any questions or concerns regarding your order, please feel free to contact our customer support.\n\n"
        + "Best Regards,\n"
        + "SmartPick Team";

        emailService.sendMessage(user.getEmail(), "Successfull order - SmartPick", message);

        productRepository.removeAllProductsFromShoppingCartForUser(user.getUserId());

        session.removeAttribute("orderItems");
        session.removeAttribute("estimatedTotal");

        return "order-confirmation";
    }
}