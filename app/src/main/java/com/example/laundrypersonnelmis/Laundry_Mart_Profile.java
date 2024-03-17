package com.example.laundrypersonnelmis;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Laundry_Mart_Profile extends AppCompatActivity {

    private DatabaseReference laundryMartRef;
    private String userPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundry_mart_profile);

        userPhone = getIntent().getStringExtra("email");
        // Initialize database reference for laundry_mart
        laundryMartRef = FirebaseDatabase.getInstance().getReference().child("laundry_mart").child(userPhone);

        // Retrieve laundry_mart data
        retrieveLaundryMartData();
    }

    private void retrieveLaundryMartData() {
        laundryMartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve data from dataSnapshot
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);
                    String location = dataSnapshot.child("location").getValue(String.class);
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String phone = dataSnapshot.child("phone").getValue(String.class);

                    // Update UI with retrieved data
                    TextView textViewEmail = findViewById(R.id.textViewEmail);
                    TextView textViewLocation = findViewById(R.id.textViewLocation);
                    TextView textViewName = findViewById(R.id.textViewName);
                    TextView textViewPhone = findViewById(R.id.textViewPhone);
                    ImageView imageViewProfile = findViewById(R.id.imageViewProfile);

                    // Perform null checks
                    if (email != null) textViewEmail.setText(email);
                    if (location != null) textViewLocation.setText(location);
                    if (name != null) textViewName.setText(name);
                    if (phone != null) textViewPhone.setText(phone);
                    if (imageUrl != null) Picasso.get().load(imageUrl).into(imageViewProfile);
                    // Set the button visibility to visible
                    Button buttonSelectVendor = findViewById(R.id.button_select_vendor);
                    buttonSelectVendor.setVisibility(View.VISIBLE);
                } else {
                    // Handle case when the laundry mart data does not exist
                    Toast.makeText(Laundry_Mart_Profile.this, "Laundry mart data does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(Laundry_Mart_Profile.this, "Failed to retrieve laundry mart data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
