package com.example.app01.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app01.R;

public class MainActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private Button loginButton;
    private static final String BASE_URL = "http://192.168.1.2:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.btn_Login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Login.class));

            }
        });
        /*
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        loginButton = findViewById(R.id.btn_Login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                // Kiểm tra nếu dữ liệu không rỗng
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên người dùng và mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }
                //goi api
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

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
                    Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MainActivity.this, "Đăng nhập thất bại: " + errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("API Error", "Error: " + errorResponse.getMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
                        // Xử lý IOException khi đọc errorBody
                        Toast.makeText(MainActivity.this, "Lỗi đọc thông báo lỗi", Toast.LENGTH_SHORT).show();
                        Log.e("API Error", "IOException: " + e.getMessage());
                    }
                }

            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Xử lý lỗi nếu có
                Toast.makeText(MainActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API Error", "Error message: " + t.getMessage(), t);

            }
        });

         */



    }

}