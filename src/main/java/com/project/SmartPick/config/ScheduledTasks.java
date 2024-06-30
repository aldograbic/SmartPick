package com.project.SmartPick.config;

import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.project.SmartPick.modelServices.DataExportService;

@Component
public class ScheduledTasks {

    @Autowired
    private DataExportService dataExportService;

    @Scheduled(fixedRate = 300000)
    public void exportBehaviorsToCsvTask() {
        String filePath = Paths.get("/app/resources/static/user_behavior.csv").toString();
        try {
            dataExportService.exportBehaviorsToCsv(filePath);
            System.out.println("Podaci o interakcijama su uspješno izvezeni u CSV datoteku.");
        } catch (IOException e) {
            System.err.println("Greška prilikom izvoza podataka u CSV datoteku: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void exportProductsToCsvTask() {
        String filePath = Paths.get("/app/resources/static/products_data.csv").toString();
        try {
            dataExportService.exportProductsToCsv(filePath);
            System.out.println("Podaci o proizvodima su uspješno izvezeni u CSV datoteku.");
        } catch (IOException e) {
            System.err.println("Greška prilikom izvoza podataka u CSV datoteku: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

