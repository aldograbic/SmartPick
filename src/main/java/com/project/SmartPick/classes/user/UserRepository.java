package com.project.SmartPick.classes.user;

public interface UserRepository {

    User findByUsername(String username);
    User findByEmail(String email);
    void saveUser(User user);
    User findByConfirmationToken(String token);
    void updateEmailVerification(User user);
    void contactUs(String name, String email, String message);
    void updateUser(User user);
    void deleteUser(User user);
}
