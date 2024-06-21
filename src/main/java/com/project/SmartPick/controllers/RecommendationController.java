package com.project.SmartPick.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.SmartPick.config.recommendationSystem.RecommendationService;

@RestController
@RequestMapping("/api")
public class RecommendationController {
    
    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/recommendations/{userId}")
    public List<String> getRecommendations(@PathVariable String userId) {
        return recommendationService.getRecommendedProducts(userId);
    }

    @GetMapping("/similar/{productId}")
    public List<String> getSimilarProducts(@PathVariable String productId) {
        return recommendationService.getSimilarProducts(productId);
    }
}