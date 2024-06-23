package com.project.SmartPick.modelServices;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.SmartPick.classes.product.Product;
import com.project.SmartPick.classes.product.ProductRepository;
import com.project.SmartPick.classes.userBehavior.UserBehavior;
import com.project.SmartPick.classes.userBehavior.UserBehaviorRepository;

@Service
public class DataExportService {
    
    @Autowired
    private UserBehaviorRepository userBehaviorRepository;

    @Autowired
    private ProductRepository productRepository;

    public void exportBehaviorsToCsv(String filePath) throws IOException {
        List<UserBehavior> behaviors = userBehaviorRepository.findAll();
        FileWriter fileWriter = new FileWriter(filePath);
        @SuppressWarnings("deprecation")
        CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader("userId", "productId", "behaviorType", "behaviorDate"));

        for (UserBehavior behavior : behaviors) {
            csvPrinter.printRecord(behavior.getUserId(), behavior.getProductId(), behavior.getBehaviorType(), behavior.getBehaviorDate());
        }

        csvPrinter.flush();
        csvPrinter.close();
    }

    public void exportProductsToCsv(String filePath) throws IOException {
        List<Product> products = productRepository.getAllProducts();
        FileWriter fileWriter = new FileWriter(filePath);
        @SuppressWarnings("deprecation")
        CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader("productId", "name", "description", "size", "color", "gender", "price", "createdAt", "categoryId", "image"));

        for (Product product : products) {
            csvPrinter.printRecord(product.getProductId(), product.getName(), product.getDescription(), product.getSize(), product.getColor(), product.getGender(), product.getPrice(), product.getCreatedAt(), product.getCategoryId(), product.getImage());
        }

        csvPrinter.flush();
        csvPrinter.close();
    }
}