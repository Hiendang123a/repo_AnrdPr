package com.example.app01.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.app01.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    private TextInputEditText usernameET, passwordET;
    private Button signInBtn;
    private TextView signUpBtn, forgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        signUpBtn = findViewById(R.id.signUpBtn);
        forgetPassword = findViewById(R.id.forgotPasswordBtn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, ForgetPassword.class));
            }
        });

        signInBtn = findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();
                // Kiểm tra nếu dữ liệu không rỗng
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Vui lòng nhập tên người dùng và mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }
                login(username, password);
            }
        });


    }

    private void login(String username, String password) {
//        //
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();


        //Khoi tao retrofit
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();

        // Tạo instance của AuthService
        AuthService authService = retrofit.create(AuthService.class);
        // Gửi yêu cầu đăng nhập
        User user = new User(username, password);  // Đảm bảo User class có constructor phù hợp



        //Log.d("Request Body", "User JSON: " + userJson);  // In ra để kiểm tra
        //
        Call<User> call = authService.login(user);


        // Gọi API bất đồng bộ
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // Đăng nhập thành công
                    User loggedInUser = response.body();
                    Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Log.d("API Response", "User: " + loggedInUser.getUsername());
                } else {
                    // Đăng nhập thất bại
                    //Toast.makeText(MainActivity.this, "Đăng nhập thất bại: " + response.message(), Toast.LENGTH_SHORT).show();
                    try {
                        // Lấy thông tin lỗi từ errorBody
                        String errorBody = response.errorBody().string();  // Đọc errorBody (có thể ném IOException)

                        // Chuyển đổi errorBody thành ErrorResponse object
                        Gson gson = new Gson();
                        ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);

                        // Hiển thị thông báo lỗi chi tiết
                        Toast.makeText(Login.this, "Đăng nhập thất bại: " + errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("API Error", "Error: " + errorResponse.getMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
                        // Xử lý IOException khi đọc errorBody
                        Toast.makeText(Login.this, "Lỗi đọc thông báo lỗi", Toast.LENGTH_SHORT).show();
                        Log.e("API Error", "IOException: " + e.getMessage());
                    }
                }

            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Xử lý lỗi nếu có
                Toast.makeText(Login.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API Error", "Error message: " + t.getMessage(), t);

            }
        });
    }
}