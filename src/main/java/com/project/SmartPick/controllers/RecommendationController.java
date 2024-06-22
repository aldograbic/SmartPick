package com.project.SmartPick.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import com.project.SmartPick.modelServices.RecommendationService;

@RestController
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/recommendations")
    public List<Integer> getRecommendations(@RequestParam int userId) {
        return recommendationService.getRecommendations(userId);
    }
}