package com.project.SmartPick.config.recommendationSystem;

import com.google.gson.Gson;
import com.project.SmartPick.classes.product.Product;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataPreprocessor {
    public static void saveProductsAsJson(List<Product> productList) {
        Gson gson = new Gson();
        String filePath = "C:/Users/Korisnik/OneDrive/Dokumenti/faks/2. godina/2. semestar/Diplomski rad/SmartPick - E-Commerce Recommender/src/main/resources/static/pythonScripts/products.json";
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(productList, writer);
            System.out.println("Products saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public static void main(String[] args) {
        // Example: Manually populate productList with scraped data for now
        List<Product> productList = new ArrayList<>();

        // Call saveProductsAsJson to output JSON
        saveProductsAsJson(productList);
    }
}
