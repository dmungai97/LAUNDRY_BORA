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

public class Service_Order_DetailsActivity extends AppCompatActivity {

    private static final String TAG = "Service_Order_Details";

    private String userPhone;
    private String selectedItems;
    private boolean toastShown = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_order_details);

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
        userPhone = getIntent().getStringExtra("email");

        // Button: Make Order
        Button buttonMakeOrder = findViewById(R.id.buttonMakeOrder);
        buttonMakeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Push selected cards to the database
                pushSelectedCardsToDatabase();
            }
        });
    }

    // Method to push selected cards to the database
    private void pushSelectedCardsToDatabase() {
        if (userPhone != null) {
            DatabaseReference userSelectedCardsRef = FirebaseDatabase.getInstance()
                    .getReference("laundry_mart").child(userPhone).child("Orders");
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

            // Start the next activity or perform any other actions
            // Here you may start a new activity or perform any other relevant actions
            // For example:
            // startActivity(new Intent(Service_Order_DetailsActivity.this, NextActivity.class));
        } else {
            Log.e(TAG, "User phone number is null");
        }
    }
}
