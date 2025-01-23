package com.example.society.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    private String username;
    private String password;
    private String name;
    private String gender;

    @JsonFormat(pattern="MMM dd, yyyy hh:mm:ss a")
    private Date dob;
    //private String googleAccount;
    //private Address currentAddress;
    //private Address hometown;
    //private List<Image> images;    // Danh sách các ảnh mà người dùng đã đăng

    public User() {
    }

    public User(Date dob, String gender, String name, String password, String username) {
        this.dob = dob;
        this.gender = gender;
        this.name = name;
        this.password = password;
        this.username = username;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
}
