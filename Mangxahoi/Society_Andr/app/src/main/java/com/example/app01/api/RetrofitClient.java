package com.example.app01.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://192.168.1.6:8080/"; //

    private static
    Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            // Tạo OkHttpClient và cấu hình timeout
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(300, TimeUnit.SECONDS)  // Thời gian timeout kết nối 5 phút
                    .readTimeout(300, TimeUnit.SECONDS)     // Thời gian timeout đọc 5 phút
                    .writeTimeout(300, TimeUnit.SECONDS)    // Thời gian timeout ghi 5 phút
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)  // Sử dụng OkHttpClient đã cấu hình
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
