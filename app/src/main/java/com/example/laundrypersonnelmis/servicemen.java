package com.example.laundrypersonnelmis;

public class servicemen {
    String name,email,phone,password,location,imageUri;
    Float rating;

    public servicemen(String name, String email, String phone, String password,String location,Float rating,String imageUri) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.location=location;
        this.rating =rating;
        this.imageUri=imageUri;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}

