package com.project.SmartPick.modelServices;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.SmartPick.classes.userBehavior.UserBehavior;
import com.project.SmartPick.classes.userBehavior.UserBehaviorRepository;

@Service
public class DataExportService {
    
    @Autowired
    private UserBehaviorRepository userBehaviorRepository;

    public void exportBehaviorsToCsv(String filePath) throws IOException {
        List<UserBehavior> behaviors = userBehaviorRepository.findAll();
        FileWriter fileWriter = new FileWriter(filePath);
        CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader("userId", "productId", "behaviorType", "behaviorDate"));

        for (UserBehavior behavior : behaviors) {
            csvPrinter.printRecord(behavior.getUserId(), behavior.getProductId(), behavior.getBehaviorType(), behavior.getBehaviorDate());
        }

        csvPrinter.flush();
        csvPrinter.close();
    }
}
