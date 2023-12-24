package com.project.SmartPick.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductsController {
    
    @GetMapping("/products")
    public String getProductsPage() {
        return "products";
    }
}
