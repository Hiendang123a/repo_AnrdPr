package com.example.society.model;

public class Address {
    private String street;
    private String ward;
    private String district;
    private String city;

    public Address() {
    }
    public Address(String street, String district, String ward, String city) {}

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return street + " " + ward + " " + district + " " + city;
    }
}
