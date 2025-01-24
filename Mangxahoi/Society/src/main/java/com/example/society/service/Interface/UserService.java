package com.example.society.service.Interface;

import com.example.society.model.User;

public interface UserService {
    User login(String username, String password);
    String register(User user);
    void verifyOTP(String otp);
    String forgetPassword(String username);
    void rePass(String otp, String newPass);
}
