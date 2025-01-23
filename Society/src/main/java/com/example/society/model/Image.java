package com.example.society.model;

import java.util.Date;

public class Image {
    private String imageID;         // ID ảnh
    private String url;              // URL ảnh hoặc base64 nếu lưu ảnh trực tiếp
    private boolean isProfile;       // Xác định nếu đây là ảnh đại diện
    private Date uploadDate;         // Ngày đăng ảnh
    private User user;               // Quan hệ nhiều ảnh với 1 user

    // Các getter và setter
    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isProfile() {
        return isProfile;
    }

    public void setProfile(boolean profile) {
        isProfile = profile;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}