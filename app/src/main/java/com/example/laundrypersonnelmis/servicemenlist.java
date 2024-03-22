package com.example.laundrypersonnelmis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class servicemenlist extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference servicemenRef;
    private myAdapter myAdapter;
    private ArrayList<Servicemenhelper> servicemenList;
    private String phone, name, email, password, location;
    private Integer Trouser, Jacket, Shirt, Tshirt, Dress, Skirt, Shoes, Sweater, Total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicemenlist);

        // Retrieve data from the previous activity
        Intent getIntent = getIntent();
        Trouser = getIntent.getIntExtra("Trouser", 0);
        Jacket = getIntent.getIntExtra("Jacket", 0);
        Shirt = getIntent.getIntExtra("Shirt", 0);
        Dress = getIntent.getIntExtra("Dress", 0);
        Tshirt = getIntent.getIntExtra("Tshirt", 0);
        Skirt = getIntent.getIntExtra("Skirt", 0);
        Shoes = getIntent.getIntExtra("Shoes", 0);
        Sweater = getIntent.getIntExtra("Sweater", 0);
        Total = getIntent.getIntExtra("Total", 0);

        // Initialize UI components
        recyclerView = findViewById(R.id.servicelist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Firebase
        servicemenRef = FirebaseDatabase.getInstance().getReference("servicemen");

        // Initialize data list and adapter
        servicemenList = new ArrayList<>();
        myAdapter = new myAdapter(this, servicemenList);
        recyclerView.setAdapter(myAdapter);

        // Set up ValueEventListener for Firebase data
        servicemenRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                servicemenList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Servicemenhelper servicemenhelper = dataSnapshot.getValue(Servicemenhelper.class);
                    servicemenList.add(servicemenhelper);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(servicemenlist.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up RecyclerView item click listener
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Servicemenhelper clickedItem = servicemenList.get(position);
                        String clickedPhone = clickedItem.getPhone();
                        Toast.makeText(servicemenlist.this, "Clicked on: " + clickedPhone, Toast.LENGTH_SHORT).show();

                        sendRequest(clickedPhone);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // Handle long item click here
                    }
                })
        );
    }

    private void sendRequest(String clickedPhone) {
        // Use clickedPhone instead of extracting it from txtphone

        Intent getIntent = getIntent();
        String phone1 = getIntent.getStringExtra("phone");
        String name1 = getIntent.getStringExtra("name");
        String newPendingKey = servicemenRef.push().getKey();

        servicemenRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(clickedPhone)) {
                    String inProgress = snapshot.child(clickedPhone).child("Inprogress").child("phone").getValue(String.class);
                    String newRequest = snapshot.child(clickedPhone).child("new request").child("phone").getValue(String.class);

                    if (inProgress == null && newRequest == null) {
                        phone = phone1;
                        name = snapshot.child(phone).child("name").getValue(String.class);
                        email = snapshot.child(phone).child("password").getValue(String.class);
                        password = snapshot.child(phone).child("email").getValue(String.class);
                        location = snapshot.child(phone).child("location").getValue(String.class);

                        String message = "Trouser=" + Trouser + "\n Jacket=" + Jacket + "\n Shirt=" + Shirt + "\n Dress=" + Dress
                                + "\n Tshirt=" + Tshirt + "\n Skirt=" + Skirt + "\n Shoes=" + Shoes + "\n Sweater=" + Sweater + "\n" + "TOTAL=Ksh" + Total;

                        Newreqfromclient newreq = new Newreqfromclient(phone1, name1, message);
                        servicemenRef.child(clickedPhone).child("new request").setValue(newreq);

                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("user").child(phone1).child("Pending req");
                        Clientpending newPending = new Clientpending(clickedPhone, name, message);
                        userRef.child(newPendingKey).setValue(newPending);

                        Toast.makeText(servicemenlist.this, "Request sent to " + clickedPhone + "\n" + name + "......", Toast.LENGTH_LONG).show();
                        navigateToHome();
                    } else {
                        Snackbar.make(recyclerView, "The serviceman is busy. Please select another one.", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Dismiss", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        // Handle action
                                    }
                                }).show();
                    }
                } else {
                    Snackbar.make(recyclerView, "User does not exist", Snackbar.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(servicemenlist.this, "Failed to check serviceman status", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToHome() {
        Intent intent = new Intent(this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }
}
