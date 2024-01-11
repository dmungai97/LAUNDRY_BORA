package com.example.laundrypersonnelmis;

public class Client_req_history {
    String name,phone,email,location,message;

    public Client_req_history(String name, String phone, String email, String location,String message) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.location = location;
        this.message= message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Client_req_history() {
    }
}
