package com.example.society.service.Impl;

import com.example.society.dao.Interface.UserDAO;
import com.example.society.model.Otp;
import com.example.society.service.Mail.OTPGenerater;
import com.example.society.service.Mail.UtilsEmail;
import jakarta.transaction.Transactional;
import com.example.society.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.society.service.Interface.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.HashMap;


@Service
public class UserServiceImpl implements UserService {
    private static final ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
    @Autowired
    private UserDAO userDAO;
    @Autowired
    UtilsEmail utilsEmail;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public final static HashMap<String, User> otpStorage = new HashMap<>();
    public final static HashMap<String, String> otpRepass = new HashMap<>();
    @Transactional
    @Override
    public String register(User user) {

        if(!UtilsEmail.isValidEmail(user.getUsername()))
        {
            throw new RuntimeException("Định dạng Email không chính xác");
        }

        if (userDAO.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Tài khoản đã tồn tại");
        }

        String subject = "Xác nhận OTP";
        String OTP = OTPGenerater.generateUniqueOTP();
        String message = UtilsEmail. CreateContent(OTP);
        emailExecutor.submit(() -> {
            try {
                utilsEmail.sendEmail(user.getUsername(), subject, message);
            } catch (Exception e) {
                e.printStackTrace();  // Đảm bảo rằng lỗi được xử lý nếu có
            }
        });
        if(!Otp.updateOtpKey(user.getUsername(), OTP))//Nếu đã tồn tại chờ xác thực OTP thì update OTP mới
        {
            otpStorage.put(OTP, user); //nếu email đó chưa tồn tại thì thêm nó vào
            System.out.println("Đã thêm OTP vào otpStorage: " + OTP + ", User: " + user);
        }
        return OTP;
    }

    @Transactional
    @Override
    public void verifyOTP(String otp) {
        otp = otp.replace("\"", "").trim(); // Loại bỏ dấu nháy và khoảng trắng
        System.out.println("Giá trị OTP sau xử lý: " + otp);
        System.out.println("Mã hóa của OTP: " + Arrays.toString(otp.getBytes()));
        User user = otpStorage.get(otp);
        System.out.println("User với OTP: " + user); // Kiểm tra xem có null không
        System.out.println("Khóa trong otpStorage: " + otpStorage.keySet().toString());
        System.out.println(Arrays.toString(otpStorage.keySet().toString().getBytes()));
        otpStorage.remove(otp);  // xóa otp đó ra khỏi hashmap
        if (user == null) {
            throw new RuntimeException("OTP không hợp lệ!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.createUser(user);
    }

    @Transactional
    @Override
    public String forgetPassword(String username) {
        if (!userDAO.existsByUsername(username)) {
            throw new RuntimeException("Tên tài khoản không tồn tại");
        }
        String subject = "Xác nhận OTP";
        String OTP = OTPGenerater.generateUniqueOTPRepass();
        String message = UtilsEmail. CreateContent(OTP);
        emailExecutor.submit(() -> {
            try {
                utilsEmail.sendEmail(username, subject, message); // Gửi email trong background
            } catch (Exception e) {
                e.printStackTrace();  // Đảm bảo rằng lỗi được xử lý nếu có
            }
        });
        if(!Otp.updateOtpKeyRepass(username, OTP))//Nếu đã tồn tại chờ xác thực OTP thì update OTP mới
        {
            otpRepass.put(OTP, username); //nếu email đó chưa tồn tại thì thêm nó vào
            System.out.println("Đã thêm OTP vào otpRepass: " + OTP + ", User: " + username);
        }
        return OTP;
    }

    @Transactional
    @Override
    public void rePass(String otp, String newPass) {
        String username = otpRepass.get(otp);
        otpRepass.remove(otp);  // xóa otp đó ra khỏi hashmap
        if (username == null) {
            throw new RuntimeException("OTP không hợp lệ!");
        }
        User user = userDAO.findByUsername(username);
        user.setPassword(passwordEncoder.encode(newPass));
        userDAO.updateUser(user);
    }

    @Transactional
    @Override
    public User login(String username, String password) {
        User user = userDAO.findByUsername(username.trim());
        // Nếu người dùng không tồn tại
        if (user == null) {
            throw new RuntimeException("Tài khoản không tồn tại");
        }
        // Nếu mật khẩu không chính xác
        if (!passwordEncoder.matches(password.trim(), user.getPassword())) {
            throw new RuntimeException("Sai mật khẩu");
        }

        // Trả về thông tin người dùng nếu đăng nhập thành công
        return user;
    }
}
