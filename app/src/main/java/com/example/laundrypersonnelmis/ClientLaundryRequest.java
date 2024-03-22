package com.example.laundrypersonnelmis;

public class ClientLaundryRequest {
    private String requestId;
    private String userPhone;
    private String selectedItems;
    private String collectionDateTime;
    private String deliveryDateTime;
    private String frequency;
    private String specialInstructionsText;

    public ClientLaundryRequest() {
        // Default constructor required for Firebase
    }

    public ClientLaundryRequest(String requestId, String userPhone,String selectedItems, String collectionDateTime, String deliveryDateTime, String frequency, String specialInstructionsText) {
        this.requestId = requestId;
        this.userPhone=userPhone;
        this.selectedItems = selectedItems;
        this.collectionDateTime = collectionDateTime;
        this.deliveryDateTime = deliveryDateTime;
        this.frequency = frequency;
        this.specialInstructionsText = specialInstructionsText;
    }

    // Getters and setters for serialization
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String requestId) {
        this.userPhone = userPhone;
    }

    public String getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(String selectedItems) {
        this.selectedItems = selectedItems;
    }

    public String getCollectionDateTime() {
        return collectionDateTime;
    }

    public void setCollectionDateTime(String collectionDateTime) {
        this.collectionDateTime = collectionDateTime;
    }

    public String getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public void setDeliveryDateTime(String deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getSpecialInstructionsText() {
        return specialInstructionsText;
    }

    public void setSpecialInstructionsText(String specialInstructionsText) {
        this.specialInstructionsText = specialInstructionsText;
    }
}
