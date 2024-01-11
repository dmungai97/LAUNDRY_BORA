package com.example.laundrypersonnelmis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.tv.TvInputService;
import android.os.Bundle;
//import android.os.Message;
import android.os.StrictMode;
//import android.se.omapi.Session;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class servicemenlist extends AppCompatActivity {
        EditText txtphone,txtClientLocation;
        RecyclerView recyclerView;
        DatabaseReference reff;
        myAdapter myad;
        ArrayList<Servicemenhelper> List;
    String message, phone, email, password, name,phone1,location,ClientLocation;
   Integer Trouser;
    Integer Jacket;
    Integer Shirt;
    Integer Tshirt;
    Integer Dress;
    Integer Skirt;
    Integer Shoes;
    Integer Sweater,Total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicemenlist);
        txtphone = (EditText) findViewById(R.id.Send_phone);
        txtClientLocation=findViewById(R.id.ClientLocation);

       Intent get = getIntent();
       // phone1 = get.getStringExtra("phone" );

         Trouser = get.getIntExtra("Trouser",0 );
        Jacket = get.getIntExtra("Jacket",0 );
        Shirt = get.getIntExtra("Shirt",0 );
        Dress = get.getIntExtra("Dress",0 );
        Tshirt = get.getIntExtra("Tshirt",0 );
        Skirt = get.getIntExtra("Skirt",0 );
        Shoes = get.getIntExtra("Shoes",0 );
        Sweater = get.getIntExtra("Sweater",0 );
        Total=get.getIntExtra("Total",0);


   recyclerView=findViewById(R.id.servicelist);
   reff= FirebaseDatabase.getInstance().getReference("servicemen");
   recyclerView.setHasFixedSize(true);
   recyclerView.setLayoutManager(new LinearLayoutManager(this));
List=new ArrayList<>();
myad=new myAdapter(this,List);
recyclerView.setAdapter(myad);
reff.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        for( DataSnapshot dataSnapshot : snapshot.getChildren())
        {
            Servicemenhelper servicemenhelper=dataSnapshot.getValue(Servicemenhelper.class);
            List.add(servicemenhelper);
        }
        myad.notifyDataSetChanged();
    }


    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});
    }


    public void send_now (View view) {

        phone = txtphone.getText().toString();
        ClientLocation = txtClientLocation.getText().toString();
      if(ClientLocation.isEmpty()){
          txtClientLocation.setError("Enter your Location");
      }else{
        if (phone.isEmpty()) {
            txtphone.setError("Enter a phone number");
        } else {
            Intent getphone = getIntent();
            String phone1 = getphone.getStringExtra("phone");
            String name1 = getphone.getStringExtra("name");

            reff = FirebaseDatabase.getInstance().getReference("servicemen");
            reff.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(phone)) {
                        String Inprogress, newr;
                        Inprogress = snapshot.child(phone).child("Inprogress").child("phone").getValue(String.class);
                        newr = snapshot.child(phone).child("new request").child("phone").getValue(String.class);

                        if (Inprogress.equals("null") && newr.equals("null")) {

                            final String username = "ianmunenemwenda@gmail.com";
                            final String passcode = "0711267846";
                            phone = txtphone.getText().toString();
                            name = snapshot.child(phone).child("name").getValue(String.class);
                            email = snapshot.child(phone).child("password").getValue(String.class);
                            password = snapshot.child(phone).child("email").getValue(String.class);
                            location = snapshot.child(phone).child("location").getValue(String.class);

                            String message ="LOCATION= "+ClientLocation+ "\n Trouser=" + Trouser + "\n Jacket=" + Jacket + "\n Shirt=" + Shirt + "\n Dress=" + Dress
                                    + "\n Tshirt=" + Tshirt + "\n Skirt=" + Skirt + "\n Shoes=" + Shoes + "\n Sweater=" + Sweater + "\n" + "TOTAL=Ksh" + Total;
                            Newreqfromclient newreq = new Newreqfromclient(phone1, name1, message);
                            reff.child(phone).child("new request").setValue(newreq);
                            reff = FirebaseDatabase.getInstance().getReference("user").child(phone1).child("Pending req");
                            Clientpending newpending = new Clientpending(phone, name, message);
                            reff.setValue(newpending);

                            Toast.makeText(servicemenlist.this, "Request sent to " + phone + "\n" + name + "......", Toast.LENGTH_LONG).show();
                            Home();
                        } else {
                            Snackbar.make(txtphone, "The serviceman is busy select another one", Snackbar.LENGTH_INDEFINITE)
                                    .setAction("Dismiss", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }

                                    }).show();
                        }


                    } else {
                        txtphone.setError("User does not exist");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            //StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
            //StrictMode.setThreadPolicy(policy);
        }
    }


        }
    public void Home() {
        Intent intent = new Intent(this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        this.finish();

    }
    }






