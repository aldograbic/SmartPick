package com.project.SmartPick.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderConfirmationController {
    
    @GetMapping("/order-confirmation")
    public String getOrderConfirmationPage() {

        return "order-confirmation";
    }
}
