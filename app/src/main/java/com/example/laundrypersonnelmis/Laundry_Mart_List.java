package com.example.laundrypersonnelmis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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


public class Laundry_Mart_List extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference LaundryMartRef;
    private Laundry_Mart_Adapter Laundry_Mart_Adapter;
    private ArrayList<laundryMart> Laundry_Mart_List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundry_mart_list);
        recyclerView = findViewById(R.id.laundryMartList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Firebase
        LaundryMartRef = FirebaseDatabase.getInstance().getReference("laundry_mart");
        Laundry_Mart_List = new ArrayList<>();
        Laundry_Mart_Adapter = new Laundry_Mart_Adapter(this, Laundry_Mart_List);
        recyclerView.setAdapter(Laundry_Mart_Adapter);

        LaundryMartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Laundry_Mart_List.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    laundryMart laundryMart = dataSnapshot.getValue(laundryMart.class);
                    Laundry_Mart_List.add(laundryMart);
                }
                Laundry_Mart_Adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Laundry_Mart_List.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });

    }
}