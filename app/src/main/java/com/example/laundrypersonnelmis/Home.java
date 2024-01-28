package com.example.laundrypersonnelmis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {
DatabaseReference reff;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        progressBar=findViewById(R.id.taskprogressbar);
        Intent getphone=getIntent();
        String phone1= getphone.getStringExtra("phone");
        reff= FirebaseDatabase.getInstance().getReference("user").child(phone1).child("Inprogress");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String phone2=snapshot.child("phone").getValue(String.class);
                if(phone2.equals("null")){
                    progressBar.setVisibility(View.INVISIBLE);
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public void feedback(View view) {
        Intent intent = new Intent(this, Client_Feedback.class);
        startActivity(intent);
    }


    public void profile(View view) {
        Intent intent = new Intent(getApplicationContext(), userProfile.class);
        Intent getname=getIntent();
        String name1= getname.getStringExtra("name");
        // username.setText("NAME: "+name1);

        Intent getphone=getIntent();
        String phone1= getphone.getStringExtra("phone");
        // userPhone.setText("phone: "+phone1);

        Intent getemail=getIntent();
        String email1= getemail.getStringExtra("email");
        String image=getemail.getStringExtra("image");

        intent.putExtra("name", name1);
        intent.putExtra("email", phone1);
        intent.putExtra("phone", email1);
        intent.putExtra("profile",image);
        startActivity(intent);
    }
    public void send_request(View view) {
        Intent intent = new Intent(getApplicationContext(),activity_pick_tems.class);
        Intent getname=getIntent();
        String name1= getname.getStringExtra("name");
        // username.setText("NAME: "+name1);

        Intent getphone=getIntent();
        String phone1= getphone.getStringExtra("phone");
        // userPhone.setText("phone: "+phone1);

        Intent getemail=getIntent();
        String email1= getemail.getStringExtra("email");
        // useremail.setText("password: "+email1);
        intent.putExtra("name", name1);
        intent.putExtra("email", phone1);
        intent.putExtra("phone", email1);
        startActivity(intent);
    }
    public void history(View view) {
        Intent intent = new Intent(getApplicationContext(),clienthistory.class);
        Intent getname=getIntent();
        String name1= getname.getStringExtra("name");
        // username.setText("NAME: "+name1);

        Intent getphone=getIntent();
        String phone1= getphone.getStringExtra("phone");
        // userPhone.setText("phone: "+phone1);

        Intent getemail=getIntent();
        String email1= getemail.getStringExtra("email");
        // useremail.setText("password: "+email1);
        intent.putExtra("name", name1);
        intent.putExtra("email", phone1);
        intent.putExtra("phone", email1);
        startActivity(intent);
    }
    public void declinedreqs(View view) {
        Intent intent = new Intent(getApplicationContext(),clientdeclinedreqs.class);
        Intent getname=getIntent();
        String name1= getname.getStringExtra("name");
        // username.setText("NAME: "+name1);

        Intent getphone=getIntent();
        String phone1= getphone.getStringExtra("phone");
        // userPhone.setText("phone: "+phone1);

        Intent getemail=getIntent();
        String email1= getemail.getStringExtra("email");
        // useremail.setText("password: "+email1);
        intent.putExtra("name", name1);
        intent.putExtra("email", phone1);
        intent.putExtra("phone", email1);

        startActivity(intent);
    }
    public void taskinprogress(View view) {
        Intent intent = new Intent(getApplicationContext(),Clienttaskinprogress.class);
        Intent getname=getIntent();
        String name1= getname.getStringExtra("name");
        // username.setText("NAME: "+name1);

        Intent getphone=getIntent();
        String phone1= getphone.getStringExtra("phone");
        // userPhone.setText("phone: "+phone1);

        Intent getemail=getIntent();
        String email1= getemail.getStringExtra("email");
        // useremail.setText("password: "+email1);
        intent.putExtra("name", name1);
        intent.putExtra("email", phone1);
        intent.putExtra("phone", email1);

        startActivity(intent);
    }
    public void pendingreq(View view) {
        Intent intent = new Intent(getApplicationContext(), Client_pendingreq.class);
        Intent getname = getIntent();
        String name1 = getname.getStringExtra("name");
        // username.setText("NAME: "+name1);

        Intent getphone = getIntent();
        String phone1 = getphone.getStringExtra("phone");
        // userPhone.setText("phone: "+phone1);

        Intent getemail = getIntent();
        String email1 = getemail.getStringExtra("email");
        // useremail.setText("password: "+email1);
        intent.putExtra("name", name1);
        intent.putExtra("email", phone1);
        intent.putExtra("phone", email1);

        startActivity(intent);
    }



    public void openServices(View view){
        Intent intent =new Intent(this,services_activity.class);
        Intent getname = getIntent();
        String name1 = getname.getStringExtra("name");
        // username.setText("NAME: "+name1);

        Intent getphone = getIntent();
        String phone1 = getphone.getStringExtra("phone");
        // userPhone.setText("phone: "+phone1);

        Intent getemail = getIntent();
        String email1 = getemail.getStringExtra("email");
        // useremail.setText("password: "+email1);
        intent.putExtra("name", name1);
        intent.putExtra("email", phone1);
        intent.putExtra("phone", email1);

        startActivity(intent);
        startActivity(intent);
    }
}

