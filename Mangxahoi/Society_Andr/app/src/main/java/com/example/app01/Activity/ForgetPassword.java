package com.example.app01.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app01.R;
import com.example.app01.api.AuthService;
import com.example.app01.api.RetrofitClient;
import com.example.app01.model.ErrorResponse;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ForgetPassword extends AppCompatActivity {
    private EditText usernameET;
    private Button getOTPBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        usernameET = findViewById(R.id.usernameET);
        getOTPBtn = findViewById(R.id.getOTPBtn);
        getOTPBtn.setOnClickListener(v ->{
            String username = usernameET.getText().toString();
            getOTP(username);
        });
    }
    private void getOTP(String username)
    {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        AuthService authService = retrofit.create(AuthService.class);
        Call<String> call = authService.forgetPass(username); // Gọi API với kiểu String (OTP)
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String otp = response.body();

                    // Create an Intent to start the OTPVerifycation activity
                    Intent intent = new Intent(ForgetPassword.this, OTPVerifycation.class);

                    // Put the OTP as an extra in the Intent
                    intent.putExtra("otp", otp);
                    intent.putExtra("username",username);
                    intent.putExtra("CALL_TYPE", "fromForgetPass");

                    // Start the OTPVerifycation activity
                    startActivity(intent);
                    Log.d("API Response", "OTP: " + otp);  // OTP sẽ được hiển thị
                } else {
                    try {
                        String errorBody = response.errorBody().string();

                        Gson gson = new Gson();
                        ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                        Toast.makeText(ForgetPassword.this, "Đăng Ký tài thất bại: " + errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("API Error", "Error: " + errorResponse.getMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(ForgetPassword.this, "Lỗi đọc thông báo lỗi", Toast.LENGTH_SHORT).show();
                        Log.e("API Error", "IOException: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Xử lý lỗi nếu có
                Toast.makeText(ForgetPassword.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API Error", "Error message: " + t.getMessage(), t);
            }
        });
    }
}