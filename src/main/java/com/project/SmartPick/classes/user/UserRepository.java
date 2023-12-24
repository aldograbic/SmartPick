package com.project.SmartPick.classes.user;

public interface UserRepository {
    User findByUsername(String username);
}
