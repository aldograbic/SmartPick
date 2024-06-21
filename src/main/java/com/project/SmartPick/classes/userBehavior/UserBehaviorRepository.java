package com.project.SmartPick.classes.userBehavior;

import java.util.List;

public interface UserBehaviorRepository {
    
    void saveBehavior(UserBehavior userBehavior);
    List<UserBehavior> findAll();
    List<UserBehavior> findByUserId(int userId);
}