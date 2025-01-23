package com.example.society.controller;

import com.example.society.model.User;
import com.example.society.service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Cho phép mọi nguồn gửi yêu cầu
public class AuthController {

    @Autowired
    private UserService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User loginRequest) {
        try {
            // Gọi phương thức login từ AuthService và trả về thông báo thành công
            User user = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(user); // Trả về đối tượng User
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(e.getMessage())); // Trả về thông báo lỗi dưới dạng JSON
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody User registerRequest) {
        try {
            // Gọi phương thức login từ AuthService và trả về thông báo thành công
            String otp = authService.register(registerRequest);
            return ResponseEntity.ok(otp); // Trả về đối tượng User
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage())); // Trả về thông báo lỗi dưới dạng JSON
        }
    }

    @PostMapping("/verifyEmail")
    public ResponseEntity<Object> verifyEmail(@RequestBody String otp) {
        try {
            authService.verifyOTP(otp);
            return ResponseEntity.ok("Tạo tài khoản thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/forgetPass")
    public ResponseEntity<Object> register(@RequestBody String username) {
        try {
            // Gọi phương thức login từ AuthService và trả về thông báo thành công
            String otp = authService.forgetPassword(username);
            return ResponseEntity.ok(otp); // Trả về đối tượng User
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage())); // Trả về thông báo lỗi dưới dạng JSON
        }
    }

    @PutMapping("/verifyEmailRepass")
    public ResponseEntity<Object> verifyEmailRepass(@RequestBody String otp, @RequestBody String newPass) {
        try {
            authService.rePass(otp,newPass);
            return ResponseEntity.ok("Đổi mật khẩu thành công!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    // Lớp ErrorResponse để trả về lỗi dưới dạng JSON
    public static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}