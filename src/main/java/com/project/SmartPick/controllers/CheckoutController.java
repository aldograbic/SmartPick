package com.project.SmartPick.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class CheckoutController {

    @GetMapping("/checkout")
    public String getCheckoutPage(Model model, @RequestParam(value = "estimatedTotal", required = false) BigDecimal estimatedTotal) {
        // Check if the estimatedTotal is available in flash attributes
        if (estimatedTotal != null) {
            model.addAttribute("estimatedTotal", estimatedTotal);
        } else {
            // Handle the case when estimatedTotal is not available
            // You can set a default value or handle it as needed
            model.addAttribute("estimatedTotal", BigDecimal.ZERO);
        }

        // Additional logic for the checkout page

        return "checkout";
    }
}
