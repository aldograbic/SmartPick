package com.project.SmartPick.modelServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendationService {
    
    @Autowired
    private DataExportService dataExportService;

    public List<Integer> getRecommendations(int userId) {
        
        // Prvo eksportiramo interakcije u CSV
        String filePath = "interactions.csv";
        try {
            dataExportService.exportBehaviorsToCsv(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

        // Pozivamo Python skriptu
        ProcessBuilder processBuilder = new ProcessBuilder("python3", "recommendation_script.py", filePath, String.valueOf(userId));
        processBuilder.redirectErrorStream(true);
        List<Integer> recommendations = new ArrayList<>();
        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                recommendations.add(Integer.parseInt(line));
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return recommendations;
    }
}