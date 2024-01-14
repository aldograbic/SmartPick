package com.project.SmartPick.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.SmartPick.classes.user.User;
import com.project.SmartPick.classes.user.UserRepository;
import com.project.SmartPick.config.EmailService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;
    
    @GetMapping("/account")
    public String getAccountPage(Model model) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);

        return "account";
    }

    @PostMapping("/account/{username}/update")
    public String updateUserDetails(@PathVariable("username") String username, User updatedUser, RedirectAttributes redirectAttributes, HttpSession session) {

        User user = userRepository.findByUsername(username);
    
        if (!user.getUsername().equals(updatedUser.getUsername())) {

            User existingUserWithNewUsername = userRepository.findByUsername(updatedUser.getUsername());
            if (existingUserWithNewUsername != null) {
                redirectAttributes.addFlashAttribute("errorMessage", "User already exists with the same username. Please try again.");
                return "redirect:/account";
            }

            user.setUsername(updatedUser.getUsername());
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setAddress(updatedUser.getAddress());
            user.setCity(updatedUser.getCity());
            user.setEmail(updatedUser.getEmail());
            userRepository.updateUser(user);
            session.invalidate();
            redirectAttributes.addFlashAttribute("successMessage", "The account information update was completed successfully! Kindly proceed by logging in again to continue.");
            return "redirect:/login";
        }

        if (!user.getEmail().equals(updatedUser.getEmail())) {

            User existingUserWithNewEmail = userRepository.findByEmail(updatedUser.getEmail());
            if (existingUserWithNewEmail != null) {
                redirectAttributes.addFlashAttribute("errorMessage", "User already exists with the same e-mail address. Please try again.");
                return "redirect:/account";
            }

            String token = UUID.randomUUID().toString();
            user.setConfirmationToken(token);
            user.setEmailVerified(false);
            
            user.setUsername(updatedUser.getUsername());
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setAddress(updatedUser.getAddress());
            user.setCity(updatedUser.getCity());
            user.setEmail(updatedUser.getEmail());
    
            userRepository.updateUser(user);
            String confirmationLink = "http://localhost:8080/confirm?token=" + token;
            emailService.sendMessage(user.getEmail(), "Confirm your e-mail address - SmartPick", "Hello, " + user.getFirstName() + "!\nWe see that you changed your e-mail address to this one!\nWe just need one more thing to get you going... click the link below to confirm your e-mail address!\nConfirmation link: " + confirmationLink);
            session.invalidate();
    
            redirectAttributes.addFlashAttribute("successMessage", "Account information has been successfully updated! Before logging in, please check your email for confirmation of the new email address.");
            return "redirect:/";
        }
    
        user.setUsername(updatedUser.getUsername());
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setAddress(updatedUser.getAddress());
        user.setCity(updatedUser.getCity());
        user.setEmail(updatedUser.getEmail());
    
        userRepository.updateUser(user);
    
        redirectAttributes.addFlashAttribute("successMessage", "The account information has been successfully updated!");
    
        return "redirect:/account";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@ModelAttribute User user, @RequestParam("password") String password, RedirectAttributes redirectAttributes) {

        String encryptedPassword = passwordEncoder.encode(password);
        if(encryptedPassword == user.getPassword()) {
            try {
                userRepository.deleteUser(user);
                redirectAttributes.addFlashAttribute("successMessage", "Account successfully deleted.");

            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("errorMessage", "There was an issue with deleting your account. Please try again.");
                return "redirect:/account";
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "There was an issue with deleting your account. Please try again.");
            return "redirect:/account";
        }

        return "redirect:/";
    }
}