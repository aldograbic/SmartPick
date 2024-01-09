package com.project.SmartPick.controllers;

import com.project.SmartPick.classes.user.User;
import com.project.SmartPick.classes.user.UserRepository;
import com.project.SmartPick.classes.order.OrderItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class CheckoutController {

    @Autowired
    private UserRepository userRepository;

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
        model.addAttribute("orderItems", orderItems);

        return "checkout";
    }
}
