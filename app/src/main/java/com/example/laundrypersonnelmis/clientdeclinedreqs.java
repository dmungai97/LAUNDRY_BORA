package com.example.laundrypersonnelmis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class clientdeclinedreqs extends AppCompatActivity {
    TextView txtmessage, txtphone, txtname;
    String name, phone, message;

    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientdeclinedreqs);
        txtmessage = (TextView) findViewById(R.id.message);
        txtphone = (TextView) findViewById(R.id.phone);
        txtname = (TextView) findViewById(R.id.name);
        //getdata();


        //public void getdata() {
        Intent getphone = getIntent();
        String phone1 = getphone.getStringExtra("email");
        reff = FirebaseDatabase.getInstance().getReference("user").child(phone1);
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("Declined").child("name").getValue(String.class);
                String phone = snapshot.child("Declined").child("phone").getValue(String.class);
                String message = snapshot.child("Declined").child("message").getValue(String.class);
                txtphone.setText(phone);
                txtname.setText(name);
                txtmessage.setText(message);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    public void clearreq(View view){
        Intent getphone = getIntent();
        String phone1 = getphone.getStringExtra("email");
        reff = FirebaseDatabase.getInstance().getReference("user").child(phone1);
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String phone = snapshot.child("Declined").child("phone").getValue(String.class);
               // Toast.makeText(clientdeclinedreqs.this, phone, Toast.LENGTH_SHORT).show();
               if (phone.equals("null")){
                   Toast.makeText(clientdeclinedreqs.this, "The fields are empty", Toast.LENGTH_SHORT).show();

               }else{
                  String name2= "null";
                  String phone2 = "null";
                  String message2= "null";
                   Newreqfromclient newdel = new Newreqfromclient(phone2, name2, message2);
                   reff.child("Declined").setValue(newdel);
                   Toast.makeText(clientdeclinedreqs.this, "Successfully cleared", Toast.LENGTH_SHORT).show();
               }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }






}