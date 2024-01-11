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

public class serviceman_Requestinprogress extends AppCompatActivity {
    TextView txtmessage, txtphone, txtname;
    String name, phone, message,phonecheck;

    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceman_requestinprogress);


        txtmessage = (TextView) findViewById(R.id.message);
        txtphone = (TextView) findViewById(R.id.phone);
        txtname = (TextView) findViewById(R.id.name);
        //getdata();


        //public void getdata() {
        Intent getphone = getIntent();
        String phone1 = getphone.getStringExtra("email");
        reff = FirebaseDatabase.getInstance().getReference("servicemen").child(phone1);
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("Inprogress").child("name").getValue(String.class);
                String phone = snapshot.child("Inprogress").child("phone").getValue(String.class);
                String message = snapshot.child("Inprogress").child("message").getValue(String.class);
                txtphone.setText(phone);
                txtname.setText(name);
                txtmessage.setText(message);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    public void  done(View view) {
        phonecheck = txtphone.getText().toString();
        if (phonecheck.isEmpty()) {
            Toast.makeText(serviceman_Requestinprogress.this, "No task is in progress", Toast.LENGTH_SHORT).show();
        } else {
            Intent getphone = getIntent();
            String phone1 = getphone.getStringExtra("email");
            reff = FirebaseDatabase.getInstance().getReference("user").child(phone1);
            reff.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String name = snapshot.child("Inprogress").child("name").getValue(String.class);
                    String phone = snapshot.child("Inprogress").child("phone").getValue(String.class);
                    String message = snapshot.child("Inprogress").child("message").getValue(String.class);
                    Newreqfromclient set_hist = new Newreqfromclient(phone, name, message);

                    if (name.equals("null")) {
                        Toast.makeText(serviceman_Requestinprogress.this, "No task is in progress", Toast.LENGTH_SHORT).show();
                    } else {
                        reff.child("Ordersmade").push().setValue(set_hist);
                        String name2 = "null";
                        String phone2 = "null";
                        String message2 = "null";
                        Newreqfromclient clear = new Newreqfromclient(phone2, name2, message2);
                        reff.child("Inprogress").setValue(clear);
                        reff = FirebaseDatabase.getInstance().getReference("servicemen").child(phone);
                        reff.child("Inprogress").setValue(clear);

                        Intent intent = new Intent(getApplicationContext(), Rating.class);
                        intent.putExtra("phone", phone);
                        intent.putExtra("name", name);
                        startActivity(intent);


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });


        }

    }
}