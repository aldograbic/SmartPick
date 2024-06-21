package com.project.SmartPick.classes.userBehavior;

import java.sql.Timestamp;

public class UserBehavior {
    private int behaviorId;
    private int userId;
    private int productId;
    private String behaviorType;
    private Timestamp behaviorDate;

    public int getBehaviorId() {
        return behaviorId;
    }
    public void setBehaviorId(int behaviorId) {
        this.behaviorId = behaviorId;
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
    public String getBehaviorType() {
        return behaviorType;
    }
    public void setBehaviorType(String behaviorType) {
        this.behaviorType = behaviorType;
    }
    public Timestamp getBehaviorDate() {
        return behaviorDate;
    }
    public void setBehaviorDate(Timestamp behaviorDate) {
        this.behaviorDate = behaviorDate;
    } 
}