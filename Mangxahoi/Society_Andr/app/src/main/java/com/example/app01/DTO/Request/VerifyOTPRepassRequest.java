package com.example.app01.DTO.Request;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyOTPRepassRequest {
    private String otp;
    private String username;
    private String password;
}
