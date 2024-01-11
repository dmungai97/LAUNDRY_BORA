package com.example.laundrypersonnelmis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Serviceman_inbox extends AppCompatActivity {
    TextView txtmessage, txtphone, txtname;
    String name, phone, message;

    DatabaseReference reff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceman_inbox);

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
                String name = snapshot.child("new request").child("name").getValue(String.class);
                String phone = snapshot.child("new request").child("phone").getValue(String.class);
                String message = snapshot.child("new request").child("message").getValue(String.class);
                txtphone.setText(phone);
                txtname.setText(name);
                txtmessage.setText(message);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void accept(View view) {
        Intent getphone = getIntent();
        String phone1 = getphone.getStringExtra("email");
        String name1= getphone.getStringExtra("name");
        reff = FirebaseDatabase.getInstance().getReference("servicemen").child(phone1);
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String null1="null";
                String name = snapshot.child("new request").child("name").getValue(String.class);
                String phone = snapshot.child("new request").child("phone").getValue(String.class);
                String message = snapshot.child("new request").child("message").getValue(String.class);
                if (phone.equals("null")){
                    Toast.makeText(Serviceman_inbox.this, "YOU dont have any request", Toast.LENGTH_SHORT).show();

                }else{
                Newreqfromclient newreq = new Newreqfromclient(phone, name, message);
                reff.child("Inprogress").setValue(newreq);
                    reff=FirebaseDatabase.getInstance().getReference("user").child(phone);
                    Newreqfromclient newreq2 = new Newreqfromclient(phone1, name1, message);
                    reff.child("Inprogress").setValue(newreq2);
                name = "null";
                phone = "null";
                message = "null";
                Newreqfromclient newdel = new Newreqfromclient(phone, name, message);
                reff.child("Pending req").setValue(newdel);
                    reff=FirebaseDatabase.getInstance().getReference("servicemen").child(phone1);
                reff.child("new request").setValue(newdel);
                Toast.makeText(Serviceman_inbox.this, "Request accepted accepted", Toast.LENGTH_SHORT).show();
                Home();}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



    public void decline(View view) {
        Intent getphone = getIntent();
        String phone1 = getphone.getStringExtra("email");
        String name1= getphone.getStringExtra("name");
        reff = FirebaseDatabase.getInstance().getReference("servicemen").child(phone1);
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name = snapshot.child("new request").child("name").getValue(String.class);
                String phone = snapshot.child("new request").child("phone").getValue(String.class);
                String message = snapshot.child("new request").child("message").getValue(String.class);
                if (phone.equals("null")){
                    Toast.makeText(Serviceman_inbox.this, "YOU dont have any request", Toast.LENGTH_SHORT).show();

                }else{
                    reff=FirebaseDatabase.getInstance().getReference("user").child(phone);
                    Newreqfromclient newreq = new Newreqfromclient(phone1, name1, message);
                    reff.child("Declined").setValue(newreq);
                    name = "null";
                    phone = "null";
                    message = "null";
                    Newreqfromclient newdel = new Newreqfromclient(phone, name, message);
                    reff.child("Pending req").setValue(newdel);

                    reff=FirebaseDatabase.getInstance().getReference("servicemen").child(phone1);
                    reff.child("new request").setValue(newdel);
                    Toast.makeText(Serviceman_inbox.this, "Request  Declined", Toast.LENGTH_SHORT).show();
                    Home();}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }








    public void Home() {
        Intent intent = new Intent(this, Serviceman_home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        this.finish();

    }





}













