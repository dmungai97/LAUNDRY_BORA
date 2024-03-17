package com.example.laundrypersonnelmis;

import android.widget.Button;

public class laundryMart {
    private String name;
    private String password;
    private String phone;
    private String email;
    private String location;
    private String imageUrl;
    private Button selectVendorButton;

    public laundryMart() {
        // Default constructor required for Firebase
    }

    public laundryMart(String name, String password, String phone, String email, String location, String imageUrl) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.location = location;
        this.imageUrl = imageUrl;
    }

    // Getters and setters for the class properties
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Button getSelectVendorButton() {
        return selectVendorButton;
    }

    public void setSelectVendorButton(Button selectVendorButton) {
        this.selectVendorButton = selectVendorButton;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

