package com.example.app01.model;

public class PasswordStrengthChecker {
    public static String checkPasswordStrength(String password) {
        if (password.length() < 8) {
            return "Mật khẩu phải có ít nhất 8 ký tự.";
        }

        if (!password.matches(".*[a-z].*")) {
            return "Mật khẩu phải chứa ít nhất 1 chữ thường.";
        }

        if (!password.matches(".*[0-9].*")) {
            return "Mật khẩu phải chứa ít nhất 1 chữ số.";
        }

        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            return "Mật khẩu phải chứa ít nhất 1 ký tự đặc biệt.";
        }

        return null;
    }
}
