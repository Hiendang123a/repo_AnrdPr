package com.example.app01.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.app01.model.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Register extends AppCompatActivity {
    private TextView dateTV, signInBtn;
    private ImageView datePickerBtn;
    private Calendar calendar = Calendar.getInstance();
    private Button signUpBtn;
    private RadioGroup genderRadioGroup;

    private RadioButton maleRdb, femaleRdb, selectedRadioButton ;
    private EditText fullnameET, usernameET, passwordET;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fullnameET = findViewById(R.id.fullnameET);
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        dateTV = findViewById(R.id.dateTV);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        maleRdb = findViewById(R.id.maleRdb);
        femaleRdb = findViewById(R.id.femaleRdb);
        datePickerBtn = findViewById(R.id.datePickerBtn);
        signInBtn = findViewById(R.id.signInBtn);
        signUpBtn = findViewById(R.id.signUpBtn);

        //Btn male luôn chọn trước để tránh xử lý th không rdb nào được chọn
        maleRdb.setChecked(true);
        //Set thoi gian hien tai
        String currentDate = dateFormat.format(calendar.getTime());
        dateTV.setText(currentDate);

        //Xu ly khi chon ngay
        datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        signInBtn.setOnClickListener(v->{
            startActivity(new Intent(Register.this, Login.class));
        });

        signUpBtn.setOnClickListener(v->{
            //startActivity(new Intent(Register.this, OTPVerifycation.class));
            String fullname = fullnameET.getText().toString();
            String username = usernameET.getText().toString();
            String password = passwordET.getText().toString();
            int selectedId = genderRadioGroup.getCheckedRadioButtonId();
            String gender = ((RadioButton) findViewById(selectedId)).getText().toString();
            String dobString = dateTV.getText().toString();
            try {
                // Chuyển đổi chuỗi thành đối tượng Date
                Date dob = dateFormat.parse(dobString);
                signUp(fullname,username,password,gender,dob);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }
    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, monthOfYear, dayOfMonth);
                        String formattedDate = dateFormat.format(selectedDate.getTime());
                        dateTV.setText(formattedDate);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void signUp(String fullname, String username, String password, String gender, Date dob){

        // Kiểm tra nếu tên null
        if (fullname == null || fullname.isEmpty()) {
            fullnameET.requestFocus();
            Toast.makeText(this, "Tên không được để trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra nếu tên đăng nhập null
        if (username == null || username.isEmpty()) {
            usernameET.requestFocus();
            Toast.makeText(this, "Tên đăng nhập không được để trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        String checkPW = PasswordStrengthChecker.checkPasswordStrength(password);
        // Kiểm tra nếu mật khẩu null
        if (checkPW != null) {
            passwordET.requestFocus();
            Toast.makeText(this, checkPW, Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(fullname,username,password,gender,dob);
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        AuthService authService = retrofit.create(AuthService.class);
        Call<String> call = authService.register(user); // Gọi API với kiểu String (OTP)
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String otp = response.body();

                    // Create an Intent to start the OTPVerifycation activity
                    Intent intent = new Intent(Register.this, OTPVerifycation.class);

                    // Put the OTP as an extra in the Intent
                    intent.putExtra("otp", otp);
                    intent.putExtra("username",user.getUsername());
                    intent.putExtra("CALL_TYPE", "fromRegister");
                    // Start the OTPVerifycation activity
                    startActivity(intent);
                    Log.d("API Response", "OTP: " + otp);  // OTP sẽ được hiển thị
                } else {
                    try {
                        String errorBody = response.errorBody().string();

                        Gson gson = new Gson();
                        ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                        Toast.makeText(Register.this, "Đăng Ký tài thất bại: " + errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("API Error", "Error: " + errorResponse.getMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(Register.this, "Lỗi đọc thông báo lỗi", Toast.LENGTH_SHORT).show();
                        Log.e("API Error", "IOException: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Xử lý lỗi nếu có
                Toast.makeText(Register.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API Error", "Error message: " + t.getMessage(), t);
            }
        });
    }
}