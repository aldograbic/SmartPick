package com.project.SmartPick.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.SmartPick.classes.user.User;
import com.project.SmartPick.classes.user.UserRepository;

import jakarta.servlet.http.HttpSession;

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

        model.addAttribute("estimatedTotal", session.getAttribute("estimatedTotal"));
        return "checkout";
    }
}
