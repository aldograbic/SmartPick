package com.project.SmartPick.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.SmartPick.classes.user.User;
import com.project.SmartPick.classes.user.UserRepository;

@Controller
public class AccountController {

    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/account")
    public String getAccountPage(Model model) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);

        return "account";
    }
}