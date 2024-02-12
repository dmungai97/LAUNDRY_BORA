package com.example.laundrypersonnelmis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class userProfile extends AppCompatActivity {
    TextView username ,userPhone, userEmail;
    ImageView profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        username= findViewById(R.id.textView3);
        userPhone= findViewById(R.id.textView6);
        userEmail = findViewById(R.id.textView7);
        profile=findViewById(R.id.profileimg);

        Intent getName=getIntent();
        String name1= getName.getStringExtra("name");
        username.setText(name1);

        Intent getPhone=getIntent();
        String phone1= getPhone.getStringExtra("phone");
        userPhone.setText(phone1);

        Intent getEmail=getIntent();
        String email1= getEmail.getStringExtra("email");
        String profileImage=getEmail.getStringExtra("profile");
        Glide.with(userProfile.this).load(profileImage).circleCrop().into(profile);
        userEmail.setText(email1);
    }
}