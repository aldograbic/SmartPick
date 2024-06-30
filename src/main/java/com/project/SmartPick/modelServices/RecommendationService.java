package com.project.SmartPick.modelServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendationService {

    @Autowired
    private DataExportService dataExportService;

    private static final Logger logger = LoggerFactory.getLogger(RecommendationService.class);

    // public List<Integer> getRecommendations(int userId) {

    //     String filePath = "src/main/resources/static/user_behavior.csv";
    //     String scriptPath = "src/main/resources/static/python/recommendation_script.py";
    
    //     try {
    //         dataExportService.exportBehaviorsToCsv(filePath);
    //     } catch (IOException e) {
    //         logger.error("Failed to export behaviors to CSV", e);
    //         return Collections.emptyList();
    //     }
    
    //     ProcessBuilder processBuilder = new ProcessBuilder("python", scriptPath, filePath, String.valueOf(userId));
    //     processBuilder.redirectErrorStream(true);
    //     List<Integer> recommendations = new ArrayList<>();
    //     try {
    //         Process process = processBuilder.start();
    //         BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    //         String line;
    //         while ((line = reader.readLine()) != null) {
    //             logger.info("Python script output: " + line);
    //             try {
    //                 recommendations.add(Integer.parseInt(line));
    //             } catch (NumberFormatException e) {
    //                 logger.warn("Skipping non-integer output: " + line);
    //             }
    //         }
    //         process.waitFor();
    //     } catch (IOException | InterruptedException e) {
    //         logger.error("Error running the recommendation script", e);
    //     }
    //     return recommendations;
    // }

    // public Map<String, String> getEvaluationMetrics(int userId) {
    //     String filePath = "src/main/resources/static/user_behavior.csv";
    //     String scriptPath = "src/main/resources/static/python/recommendation_script.py";

    //     try {
    //         dataExportService.exportBehaviorsToCsv(filePath);
    //     } catch (IOException e) {
    //         logger.error("Failed to export behaviors to CSV", e);
    //         return Collections.emptyMap();
    //     }

    //     ProcessBuilder processBuilder = new ProcessBuilder("python", scriptPath, filePath, String.valueOf(userId));
    //     processBuilder.redirectErrorStream(true);

    //     Map<String, String> metrics = new HashMap<>();
    //     try {
    //         Process process = processBuilder.start();
    //         BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    //         String line;
    //         while ((line = reader.readLine()) != null) {
    //             logger.info("Python script output: " + line);
    //             if (line.startsWith("Precision:")) {
    //                 metrics.put("precision", line.split(":")[1].trim());
    //             } else if (line.startsWith("Recall:")) {
    //                 metrics.put("recall", line.split(":")[1].trim());
    //             } else if (line.startsWith("F1 Score:")) {
    //                 metrics.put("f1", line.split(":")[1].trim());
    //             } else if (line.startsWith("Mean Average Precision (MAP):")) {
    //                 metrics.put("map", line.split(":")[1].trim());
    //             } else if (line.startsWith("Mean Reciprocal Rank (MRR):")) {
    //                 metrics.put("mrr", line.split(":")[1].trim());
    //             }
    //         }
    //         process.waitFor();
    //     } catch (IOException | InterruptedException e) {
    //         logger.error("Error running the recommendation script", e);
    //     }
    //     return metrics;
    // }

    // ZA CONTENT BASED STROJNO UCENJE
    public List<Integer> getRecommendations(int userId) {

        String userBehaviorFilePath = "/app/resources/static/user_behavior.csv";
        String productsDataFilePath = "/app/resources/static/products_data.csv";
        String scriptPath = "/app/resources/static/python/recommendation_script_content_based.py";
    
        try {
            dataExportService.exportBehaviorsToCsv(userBehaviorFilePath);
        } catch (IOException e) {
            logger.error("Failed to export behaviors to CSV", e);
            return Collections.emptyList();
        }
    
        ProcessBuilder processBuilder = new ProcessBuilder("python", scriptPath, userBehaviorFilePath, productsDataFilePath, String.valueOf(userId));

        processBuilder.redirectErrorStream(true);
        List<Integer> recommendations = new ArrayList<>();
        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                logger.info("Python script output: " + line);
                try {
                    recommendations.add(Integer.parseInt(line.trim()));
                } catch (NumberFormatException e) {
                    logger.warn("Skipping non-integer output: " + line);
                }
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            logger.error("Error running the recommendation script", e);
        }
        return recommendations;
    }

    public Map<String, String> getEvaluationMetrics(int userId) {
        String userBehaviorFilePath = "/app/resources/static/user_behavior.csv";
        String productsDataFilePath = "/app/resources/static/products_data.csv";
        String scriptPath = "/app/resources/static/python/recommendation_script_content_based.py";

        try {
            dataExportService.exportBehaviorsToCsv(userBehaviorFilePath);
        } catch (IOException e) {
            logger.error("Failed to export behaviors to CSV", e);
            return Collections.emptyMap();
        }

        ProcessBuilder processBuilder = new ProcessBuilder("python", scriptPath, userBehaviorFilePath, productsDataFilePath, String.valueOf(userId));
        processBuilder.redirectErrorStream(true);

        Map<String, String> metrics = new HashMap<>();
        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                logger.info("Python script output: " + line);
                if (line.startsWith("Precision:")) {
                    metrics.put("precision", line.split(":")[1].trim());
                } else if (line.startsWith("Recall:")) {
                    metrics.put("recall", line.split(":")[1].trim());
                } else if (line.startsWith("F1 Score:")) {
                    metrics.put("f1", line.split(":")[1].trim());
                } else if (line.startsWith("Mean Average Precision (MAP):")) {
                    metrics.put("map", line.split(":")[1].trim());
                } else if (line.startsWith("Mean Reciprocal Rank (MRR):")) {
                    metrics.put("mrr", line.split(":")[1].trim());
                }
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            logger.error("Error running the recommendation script", e);
        }
        return metrics;
    }
}