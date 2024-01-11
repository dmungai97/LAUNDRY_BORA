package com.example.laundrypersonnelmis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class clienthistory extends AppCompatActivity {
    EditText txtphone;
    RecyclerView recyclerView;
    DatabaseReference reff;
    ClienthistAdapter myad;
    ArrayList<Client_req_history> List;
    String  phone1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clienthistory);
        Intent getphone = getIntent();
        phone1 = getphone.getStringExtra("email" );

        recyclerView = findViewById(R.id.servicehistory);
        reff = FirebaseDatabase.getInstance().getReference("user").child(phone1).child("Ordersmade");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List = new ArrayList<>();
        myad = new ClienthistAdapter(this, List);
        recyclerView.setAdapter(myad);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Client_req_history client_req_history = dataSnapshot.getValue(Client_req_history.class);
                    List.add(client_req_history);
                }
                myad.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}