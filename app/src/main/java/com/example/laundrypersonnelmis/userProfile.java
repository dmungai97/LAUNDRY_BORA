package com.example.laundrypersonnelmis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class userProfile extends AppCompatActivity {
    TextView username ,userPhone,useremail;
    ImageView profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        username=(TextView) findViewById(R.id.textView3);
        userPhone=(TextView) findViewById(R.id.textView6);
        useremail=(TextView) findViewById(R.id.textView7);
        profile=findViewById(R.id.profileimg);
        Intent getname=getIntent();
        String name1= getname.getStringExtra("name");
        username.setText(name1);

        Intent getphone=getIntent();
        String phone1= getphone.getStringExtra("phone");
        userPhone.setText(phone1);

        Intent getemail=getIntent();
        String email1= getemail.getStringExtra("email");
        String profileimage=getemail.getStringExtra("profile");
        Glide.with(userProfile.this).load(profileimage).circleCrop().into(profile);
        useremail.setText(email1);

    }
}