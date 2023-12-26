package com.project.SmartPick.classes.product;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Product {
    private int productId;
    private String name;
    private String description;
    private String size;
    private String color;
    private String gender;
    private BigDecimal price;
    private Timestamp createdAt;

    private ProductCategory category;

    public Product() {}

    public Product(String name, String description, BigDecimal price, String size, String color, String gender) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.size = size;
        this.color = color;
        this.gender = gender;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }
}