package com.project.SmartPick.classes.purchase;

import java.sql.Timestamp;

public class Purchase {
    private int purchaseId;
    private int userId;
    private int productId;
    private Timestamp purchaseDate;

    public Purchase() {}

    public Purchase(int userId, int productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Timestamp getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Timestamp purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}

