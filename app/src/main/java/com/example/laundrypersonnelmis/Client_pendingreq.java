package com.example.laundrypersonnelmis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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

public class Client_pendingreq extends AppCompatActivity {
    TextView txtmessage, txtphone, txtname;
    String name, phone, message,phonecheck;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_pendingreq);

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
                String name = snapshot.child("Pending req").child("name").getValue(String.class);
                String phone = snapshot.child("Pending req").child("phone").getValue(String.class);
                String message = snapshot.child("Pending req").child("message").getValue(String.class);
                txtphone.setText(phone);
                txtname.setText(name);
                txtmessage.setText(message);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }
    public void  cancel(View view) {
        phonecheck = txtphone.getText().toString();
        if (phonecheck.isEmpty()) {
            Toast.makeText(Client_pendingreq.this, "You dont have a pending request", Toast.LENGTH_SHORT).show();
        } else {
            Intent getphone = getIntent();
            String phone1 = getphone.getStringExtra("email");
            reff = FirebaseDatabase.getInstance().getReference("user").child(phone1);
            reff.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String name = snapshot.child("Pending req").child("name").getValue(String.class);
                    String phone = snapshot.child("Pending req").child("phone").getValue(String.class);
                    String message = snapshot.child("Pending req").child("message").getValue(String.class);


                    if (name.equals("null")) {
                        Toast.makeText(Client_pendingreq.this, "No pending request", Toast.LENGTH_SHORT).show();
                    } else {

                        String name2 = "null";
                        String phone2 = "null";
                        String message2 = "null";
                        Newreqfromclient clear = new Newreqfromclient(phone2, name2, message2);
                        reff.child("Pending req").setValue(clear);
                        reff = FirebaseDatabase.getInstance().getReference("servicemen").child(phone);
                        reff.child("new request").setValue(clear);

                        Toast.makeText(Client_pendingreq.this, "Request Cancelled", Toast.LENGTH_SHORT).show();


                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });


        }
    }


}