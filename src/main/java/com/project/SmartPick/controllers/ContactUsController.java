package com.project.SmartPick.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.SmartPick.classes.user.UserRepository;
import com.project.SmartPick.config.EmailService;

@Controller
public class ContactUsController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Value("${spring.mail.username}")
    private String sender;

    @GetMapping("/contact-us")
    public String getContactUsPage() {
        return "contact-us";
    }

    @PostMapping("/contact-us")
    public String contactUs(@RequestParam String name, 
                                @RequestParam String email, 
                                @RequestParam String message, 
                                RedirectAttributes redirectAttributes) {

         try {
            userRepository.contactUs(name, email, message);
            emailService.sendMessage(sender, "New contact message from " + name, "User e-mail: " + email + "\nMessage: " + message);

            redirectAttributes.addFlashAttribute("successMessage", "Your message has been submitted successfully.");

         } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "There was an issue with sending your message. Please try again.");
            return "redirect:/contact-us";
         }
         
         return "redirect:/";
    }
}
