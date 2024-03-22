package com.example.laundrypersonnelmis;

class Order {
    private String items;
    private String specialInstructions;
    private String deliveryDate;

    // Constructor (optional)
    public Order(String items, String specialInstructions, String deliveryDate) {
        this.items = items;
        this.specialInstructions = specialInstructions;
        this.deliveryDate = deliveryDate;
    }

    // Getters and setters for the class properties
    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}

