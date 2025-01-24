package com.example.app01.api;

import com.example.app01.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface AuthService {
    @POST("/api/auth/login")
    Call<User> login(@Body User user);
    @POST("/api/auth/register")
    Call<String> register(@Body User user);
    @POST("/api/auth/verifyEmail")
    Call<ResponseBody> verifyEmail(@Body String otp);
    @POST("/api/auth/forgetPass")
    Call<String> forgetPass(@Body String username);

    @PUT("/api/auth/verifyEmailRepass")
    Call<ResponseBody> verifyEmailRepass(@Query("otp") String otp, @Body String newPass);
}
