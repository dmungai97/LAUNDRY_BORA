package com.example.laundrypersonnelmis;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laundrypersonnelmis.ui.dashboard.DashboardViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Laundry_Mart_List extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference laundryMartRef;
    private Laundry_Mart_Adapter laundry_Mart_Adapter;
    private ArrayList<laundryMart> laundry_Mart_List;
    private String userPhone;
    private String collectionDateTime;
    private String deliveryDateTime;
    private String frequency;
    private String specialInstructionsText;
    private String selectedItems;
    private DashboardViewModel dashboardViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundry_mart_list);
        //Dashboard ViewModel
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        // Retrieve data from the previous activity
        Intent intent = getIntent();
        userPhone = intent.getStringExtra("phone");
        collectionDateTime = intent.getStringExtra("collectionDateTime");
        deliveryDateTime = intent.getStringExtra("deliveryDateTime");
        frequency = intent.getStringExtra("frequency");
        specialInstructionsText = intent.getStringExtra("specialInstructionsText");
        selectedItems = intent.getStringExtra("Orders");

        // Initialize UI components
        recyclerView = findViewById(R.id.laundryMartList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Firebase
        laundryMartRef = FirebaseDatabase.getInstance().getReference("laundry_mart");

        // Initialize data list and adapter
        laundry_Mart_List = new ArrayList<>();
        laundry_Mart_Adapter = new Laundry_Mart_Adapter(this, laundry_Mart_List);
        recyclerView.setAdapter(laundry_Mart_Adapter);

        // Set up ValueEventListener for Firebase data
        laundryMartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                laundry_Mart_List.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    laundryMart laundryMart = dataSnapshot.getValue(laundryMart.class);
                    laundry_Mart_List.add(laundryMart);
                }
                laundry_Mart_Adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Laundry_Mart_List.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up RecyclerView item click listener
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        laundryMart clickedItem = laundry_Mart_List.get(position);
                        String clickedPhone = clickedItem.getPhone();
                        Toast.makeText(Laundry_Mart_List.this, "Clicked on: " + clickedPhone, Toast.LENGTH_SHORT).show();
                        sendRequest(clickedPhone); // Send request to selected laundry mart
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // Handle long item click here
                    }
                })
        );
        TextView textView = findViewById(R.id.textDashboard);
        textView.setVisibility(View.VISIBLE); // Make sure the TextView is visible
    }

    private void sendRequest(final String laundryMartPhone) {
        // Generate a unique key for this request
        final String pendingKey = laundryMartRef.push().getKey();

        // Create the LaundryRequest object
        final LaundryRequest request = new LaundryRequest(pendingKey, userPhone, selectedItems, collectionDateTime, deliveryDateTime, frequency, specialInstructionsText);

        // Set the user's phone number in the request for the laundry mart
        request.setUserPhone(userPhone);

        // Push the request to the selected laundry mart
        final DatabaseReference laundryMartOrdersRef = FirebaseDatabase.getInstance().getReference("laundry_mart")
                .child(laundryMartPhone)
                .child("Orders")
                .child("ClientRequest")
                .child(pendingKey);

        // Send the request to the laundry mart
        laundryMartOrdersRef.setValue(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Request successfully sent to laundry mart
                        Toast.makeText(Laundry_Mart_List.this, "Laundry request sent successfully", Toast.LENGTH_SHORT).show();

                        // After successfully sending the request, retrieve the requests from laundry_mart
                        laundryMartRef.child(laundryMartPhone)
                                .child("Orders")
                                .child("ClientRequest")
                                .child(pendingKey)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        LaundryRequest request = dataSnapshot.getValue(LaundryRequest.class);
                                        if (request != null) {
                                            ArrayList<LaundryRequest> requests = new ArrayList<>();
                                            requests.add(request);
                                            Log.d("Laundry_Mart_List", "Received Laundry Request: " + request);
                                            passRequestsToDashboard(requests);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        // Handle onCancelled
                                    }
                                });
                        // After sending to laundry mart, send a modified request to user
                        sendRequestToUser(pendingKey, request);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to send request to laundry mart
                        Toast.makeText(Laundry_Mart_List.this, "Failed to send laundry request", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void sendRequestToUser(String pendingKey, LaundryRequest request) {
        // Create a new LaundryRequest object for sending to the user without the user's phone number
        LaundryRequest userRequest = new LaundryRequest(pendingKey, null, request.getSelectedItems(), request.getCollectionDateTime(),
                request.getDeliveryDateTime(), request.getFrequency(), request.getSpecialInstructionsText());

        // Push the modified request under user's Orders node
        DatabaseReference userOrdersRef = FirebaseDatabase.getInstance().getReference("user")
                .child(userPhone)
                .child("Orders")
                .child("NewRequest")
                .child(pendingKey);

        // Send the modified request to user
        userOrdersRef.setValue(userRequest)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Modified request successfully sent to user
                        // No need to show a toast as it's not a direct interaction with the user
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to send modified request to user
                        // No need to show a toast as it's not a direct interaction with the user
                    }
                });
    }

    private void passRequestsToDashboard(ArrayList<LaundryRequest> requests) {
        // Retrieve the existing DashboardViewModel instance associated with the activity's lifecycle
        Log.d("Laundry_Mart_List", "Received Laundry Request: " + requests.size());
        dashboardViewModel.setLaundryRequests(requests);
    }




    private void navigateToHome() {
        Intent intent = new Intent(this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }

}