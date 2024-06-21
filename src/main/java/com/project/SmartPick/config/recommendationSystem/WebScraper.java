package com.project.SmartPick.config.recommendationSystem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.project.SmartPick.classes.product.Product;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class WebScraper {
    public static void main(String[] args) throws IOException {
        String url = "http://localhost:8080/products"; // Replace with your website URL
        Document doc = Jsoup.connect(url).get();
        Elements productElements = doc.select(".border.shadow-lg.rounded-lg.h-full");

        List<Product> products = new ArrayList<>();
        for (Element productElement : productElements) {
            String productId = productElement.select("input[name=productId]").attr("value");
            String name = productElement.select("p.text-lg.font-semibold.pb-2").text();
            Elements priceElements = productElement.select("p.text-lg.text-[#d11a2a]");

            BigDecimal price = BigDecimal.ZERO; // Default value if price element is not found
            if (!priceElements.isEmpty()) {
                String priceText = priceElements.first().text().replace(" EUR", "");
                if (!priceText.isEmpty()) {
                    price = new BigDecimal(priceText);
                }
            }

            String image = productElement.select("img").attr("th:src");

            Product product = new Product();
            product.setProductId(Integer.parseInt(productId));
            product.setName(name);
            product.setPrice(price);
            product.setImage(image);

            products.add(product);
        }

        // Now you can save the products or perform further processing
        DataPreprocessor.saveProductsAsJson(products);
    }
}