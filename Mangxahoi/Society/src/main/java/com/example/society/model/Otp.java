package com.example.society.model;

import java.util.Map;

import static com.example.society.service.Impl.UserServiceImpl.otpStorage;
import static com.example.society.service.Impl.UserServiceImpl.otpRepass;

public class Otp {
    public static boolean updateOtpKey(String email, String newKey) {
        String oldKey = null;
        User userToUpdate = null;

        for (Map.Entry<String, User> entry : otpStorage.entrySet()) {
            if (entry.getValue().getUsername().equals(email)) {
                oldKey = entry.getKey();
                userToUpdate = entry.getValue();
                break;
            }
        }
        if (oldKey != null && userToUpdate != null) {
            otpStorage.remove(oldKey);
            otpStorage.put(newKey, userToUpdate);
            return true;
        }
        return false;
    }

    public static boolean updateOtpKeyRepass(String email, String newKey) {
        String oldKey = null;
        String userToUpdate = "";

        for (Map.Entry<String, String> entry : otpRepass.entrySet()) {
            if (entry.getValue().equals(email)) {
                oldKey = entry.getKey();
                userToUpdate = entry.getValue();
                break;
            }
        }
        if (oldKey != null && !userToUpdate.isEmpty()) {
            otpRepass.remove(oldKey);
            otpRepass.put(newKey, userToUpdate);
            return true;
        }
        return false;
    }
}
