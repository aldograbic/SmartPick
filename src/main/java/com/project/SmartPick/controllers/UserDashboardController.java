package com.project.SmartPick.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserDashboardController {
    
    @GetMapping("/user-dashboard")
    public String getUserDashboardPage() {
        return "user-dashboard";
    }
}
