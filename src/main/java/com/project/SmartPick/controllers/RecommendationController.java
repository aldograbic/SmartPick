package com.project.SmartPick.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.SmartPick.modelServices.RecommendationService;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {
    
    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/{userId}")
    public List<Integer> getRecommendations(@PathVariable int userId) {
        return recommendationService.getRecommendations(userId);
    }
}