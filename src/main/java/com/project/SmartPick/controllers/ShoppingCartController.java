package com.project.SmartPick.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShoppingCartController {
    
    @GetMapping("/shopping-cart")
    public String getShoppingCartPage() {
        return "shopping-cart";
    }
}
