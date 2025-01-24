package com.example.society.service.Mail;

import com.example.society.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.Map;

import static com.example.society.service.Impl.UserServiceImpl.otpStorage;

@Service
public class UtilsEmail {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String text) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new MailException("Lỗi khi gửi email", e) {};
        }
    }
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        return email.matches(emailRegex);
    }
    public static String CreateContent(String otp)
    {
        StringBuilder message = new StringBuilder();
        message.append("Xác nhận OTP cho tài khoản của bạn:<br>");
        message.append("Để xác nhận tài khoản của bạn, vui lòng nhập mã OTP sau:<br>");
        message.append("OTP của bạn là: ").append(otp).append("<br>");
        message.append("Mã OTP này sẽ hết hạn trong vòng 5 phút.<br>");
        message.append("Xin cảm ơn!<br>");
        return message.toString();
    }
}
