package com.example.laundrypersonnelmis;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Service_Order_DetailsActivity extends AppCompatActivity {

    private static final String TAG = "Service_Order_Details";

    private String userPhone;
    private String selectedItems;
    private String collectionDateTime;
    private String deliveryDateTime;
    private String frequency;
    private String specialInstructionsText;
    private boolean toastShown = false;

    // Database reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_order_details);

        // Initialize database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Retrieve selected items data from the intent
        if (getIntent().hasExtra("Orders")) {
            selectedItems = getIntent().getStringExtra("Orders");

            // Convert the selected items text to uppercase
            String uppercaseSelectedItems = selectedItems.toUpperCase();

            // Display selected items in TextView or any other UI component
            TextView textView = findViewById(R.id.textViewSelectedItems);
            textView.setText(uppercaseSelectedItems);
        }

        // Get the user's phone number from the intent
        userPhone = getIntent().getStringExtra("phone");
        // Get collection and delivery date/time from the intent
        collectionDateTime = getIntent().getStringExtra("collectionDateTime");
        deliveryDateTime = getIntent().getStringExtra("deliveryDateTime");

        //get frequency
        frequency = getIntent().getStringExtra("frequency");
        //get special instructions text
        specialInstructionsText = getIntent().getStringExtra("specialInstructionsText");

        // Display collection and delivery date/time in TextView,frequency,instructions
        TextView textViewDateTime = findViewById(R.id.textViewDateTime);
        TextView frequencyTextView = findViewById(R.id.frequencyGroup);
        TextView specialInstructionsTextView = findViewById(R.id.specialInstructions);

        textViewDateTime.setText("Collection Date/Time: " + collectionDateTime + "\nDelivery Date/Time: " + deliveryDateTime);
        frequencyTextView.setText("Frequency: " + frequency);
        specialInstructionsTextView.setText("Special Instructions: " + specialInstructionsText);
        textViewDateTime.setVisibility(View.VISIBLE); // Make the TextView visible

        // Button: Make Order
        Button buttonMakeOrder = findViewById(R.id.buttonMakeOrder);
        buttonMakeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Push selected cards to the database
                pushSelectedCardsToDatabase();
                // Save collection and delivery date/time to the database
                saveDateTimeToDatabase();
            }
        });
    }

    // Method to push selected cards to the database
    private void pushSelectedCardsToDatabase() {
        if (userPhone != null && selectedItems != null) {
            DatabaseReference userSelectedCardsRef = databaseReference
                    .child("user")
                    .child(userPhone)
                    .child("Orders");
            // Clear existing data before adding new data
            userSelectedCardsRef.removeValue();

            // Push selected items to the database
            for (String selectedCard : selectedItems.split("\n")) {
                userSelectedCardsRef.push().setValue(selectedCard)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Data successfully written to the database
                                Log.d(TAG, "Data successfully written to the database under user phone number: " + userPhone);
                                if (!toastShown) {
                                    Toast.makeText(Service_Order_DetailsActivity.this, "Order Successful", Toast.LENGTH_SHORT).show();
                                    toastShown = true; // Set the flag to true
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle any errors that occurred during the operation
                                Log.e(TAG, "Error writing to database", e);
                            }
                        });
            }
        } else {
            Log.e(TAG, "User phone number or selected items are null");
        }
    }

    // Method to save collection and delivery date/time to the database
    private void saveDateTimeToDatabase() {
        if (userPhone != null && collectionDateTime != null && deliveryDateTime != null) {
            // Push collectionDateTime and deliveryDateTime to the database
            DatabaseReference orderDetailsRef = databaseReference
                    .child("user")
                    .child(userPhone)
                    .child("Orders")
                    .child("Collection_and_Delivery");
            Map<String, Object> orderDetails = new HashMap<>();
            orderDetails.put("collectionDateTime", collectionDateTime);
            orderDetails.put("deliveryDateTime", deliveryDateTime);

            orderDetailsRef.setValue(orderDetails)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Data successfully written to the database
                            Log.d(TAG, "Data successfully written to the database for user phone number: " + userPhone);
                            Toast.makeText(Service_Order_DetailsActivity.this, "Order details saved successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle any errors that occurred during the operation
                            Log.e(TAG, "Error writing order details to database", e);
                            Toast.makeText(Service_Order_DetailsActivity.this, "Failed to save order details", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Log.e(TAG, "User phone number or date/time is null");
        }
    }
}
