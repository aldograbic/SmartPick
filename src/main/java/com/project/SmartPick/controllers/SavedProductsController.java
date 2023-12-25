package com.project.SmartPick.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SavedProductsController {
    
    @GetMapping("/saved-products")
    public String getSavedProductsPage() {
        return "saved-products";
    }
}
