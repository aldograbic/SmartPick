package com.project.SmartPick.config.recommendationSystem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RecommendationService {
    @Autowired
    private RestTemplate restTemplate;

    private final String FLASK_API_URL = "http://localhost:5000";

    public List<String> getRecommendedProducts(String userId) {
        String url = FLASK_API_URL + "/recommend/" + userId;
        return restTemplate.getForObject(url, List.class);
    }

    public List<String> getSimilarProducts(String productId) {
        String url = FLASK_API_URL + "/similar/" + productId;
        return restTemplate.getForObject(url, List.class);
    }
}