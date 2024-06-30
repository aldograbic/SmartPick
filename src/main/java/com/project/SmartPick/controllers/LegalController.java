package com.project.SmartPick.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LegalController {
    
    @GetMapping("/privacy-policy")
    public String getPrivacyPolicyPage() {
        return "privacy-policy";
    }

    @GetMapping("/terms-and-conditions")
    public String getTermsAndConditionsPage() {
        return "terms-and-conditions";
    }
}