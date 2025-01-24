package com.example.app01.model;



import java.util.Date;

public class User {

    private Long userID;

    private String username;
    private String password;
    private String name;
    private String gender;

    private Date dob;
    //private String googleAccount;
    //private Address currentAddress;
    //private Address hometown;
    //private List<Image> images;    // Danh sách các ảnh mà người dùng đã đăng

    public User() {
    }

    public User(String name, String username, String password, String gender, Date dob) {
        this.dob = dob;
        this.gender = gender;
        this.name = name;
        this.password = password;
        this.username = username;
    }

    public User(String username, String password) {
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
