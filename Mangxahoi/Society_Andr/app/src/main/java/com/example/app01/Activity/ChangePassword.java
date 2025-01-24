package com.example.app01.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.example.app01.model.PasswordStrengthChecker;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangePassword extends AppCompatActivity {
    private TextView gmailTV;
    private EditText passwordET, cfpasswordET;
    private Button changePassBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gmailTV = findViewById(R.id.gmailTV);
        passwordET = findViewById(R.id.passwordET);
        cfpasswordET = findViewById(R.id.cfpasswordET);
        changePassBtn = findViewById(R.id.changePassBtn);

        String otp = getIntent().getStringExtra("otp");
        String username = getIntent().getStringExtra("username");

        gmailTV.setText(username);

        changePassBtn.setOnClickListener(v -> {
            String password = passwordET.getText().toString();
            String cfpassword = cfpasswordET.getText().toString();
            if(checkPassword(password,cfpassword))
            {
                changePassword(otp,password);
            }
        });

    }

    private void changePassword(String otp, String password)
    {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        AuthService authService = retrofit.create(AuthService.class);
        Call<ResponseBody> call = authService.verifyEmailRepass(otp,password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    startActivity(new Intent(ChangePassword.this, MainActivity.class));
                    Toast.makeText(ChangePassword.this, "Đặt lại mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Gson gson = new Gson();
                        ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                        Toast.makeText(ChangePassword.this, "Đặt lại mật khẩu thất bại: " + errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("API Error", "Error: " + errorResponse.getMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(ChangePassword.this, "Lỗi đọc thông báo lỗi", Toast.LENGTH_SHORT).show();
                        Log.e("API Error", "IOException: " + e.getMessage());
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ChangePassword.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API Error", "Error message: " + t.getMessage(), t);
            }
        });
    }
    private boolean checkPassword(String password, String cfpassword){
        if(password.equals(cfpassword))
        {
            String checkPW = PasswordStrengthChecker.checkPasswordStrength(password);
            if(checkPW != null)
            {
                Toast.makeText(this, checkPW, Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
        else
        {
            Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}