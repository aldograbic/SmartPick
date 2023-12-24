package com.project.SmartPick.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactUsController {

    @GetMapping("/contact-us")
    public String getContactUsPage() {
        return "contact-us";
    }
}
