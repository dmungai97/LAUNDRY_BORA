package com.example.laundrypersonnelmis;

public class Clientpending {
    String phone;
    String name;
    String message;

    public Clientpending(String phone, String name, String message) {
        this.phone = phone;
        this.name = name;
        this.message = message;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Clientpending() {
    }
}

