package com.example.laundrypersonnelmis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;
import java.util.Map;

public class Rating extends AppCompatActivity {
    DatabaseReference reff;
    String phone,name;
    int rating1;
    Button btnsubmit,btncancel;
    TextView phonetxt,nametxt;
    RatingBar rate;
    RatingBar ratingBar;
    ImageView Imgprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        phonetxt=(TextView)findViewById(R.id.phonetxt);
        nametxt=(TextView)findViewById(R.id.nametxt);

        Intent getphone = getIntent();
         name = getphone.getStringExtra("name");
        phone = getphone.getStringExtra("phone");
        phonetxt.setText(phone);
        nametxt.setText(name);
        rate=findViewById(R.id.rating);
        btnsubmit=findViewById(R.id.btnsub);
        btncancel=findViewById(R.id.btncan);
        ratingBar=findViewById(R.id.rating);
        Imgprofile=findViewById(R.id.profile);


         pic();



    }

    public void rate(View view) {

        Float rating1=rate.getRating();

        if (rating1 > 0) {

            Intent getphone = getIntent();
            String phone = getphone.getStringExtra("phone");


            reff = FirebaseDatabase.getInstance().getReference("servicemen");


            reff.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {





                    Float rating = snapshot.child(phone).child("rating").getValue(Float.class);
                    if (rating == 0) {
                        rating = Float.valueOf(rating1);

                        Map<String,Object> update= new HashMap<String,Object>();
                        update.put("rating",rating);
                        reff.child(phone).updateChildren(update);
                        ratingBar.setIsIndicator(true);
                        btnsubmit.setClickable(false);
                        btncancel.setClickable(false);
                        Snackbar.make(phonetxt, "Thanks for rating", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Dismiss", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Home();
                                    }

                                }).show();



                    } else {
                        rating = (rating + rating1) / 2;
                        Map<String,Object> update= new HashMap<String,Object>();
                        update.put("rating",rating);
                        reff.child(phone).updateChildren(update);
                        ratingBar.setIsIndicator(true);
                        btnsubmit.setClickable(false);
                        btncancel.setClickable(false);

                        Snackbar.make(phonetxt, "Thanks for rating", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Dismiss", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Home();

                                    }

                                }).show();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }


            });


        } else {
            Toast.makeText(this, "Select a rating", Toast.LENGTH_SHORT).show();
        }


    }

    public void Home() {
        Intent intent = new Intent(this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        this.finish();

    }
    public void Cancel(View view) {
        Intent intent = new Intent(this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        this.finish();

    }

    public void  pic(){
        reff = FirebaseDatabase.getInstance().getReference("servicemen");

        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String link= snapshot.child(phone).child("imageUri").getValue(String.class);
               // Toast.makeText(Rating.this, link, Toast.LENGTH_SHORT).show();
                Glide.with(Rating.this).load(link)
                        .circleCrop()
                        .into(Imgprofile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}