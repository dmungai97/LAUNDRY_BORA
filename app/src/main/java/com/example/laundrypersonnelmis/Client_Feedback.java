package com.example.laundrypersonnelmis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Client_Feedback extends AppCompatActivity {
    EditText txtfeedback;
    String feedback;
    DatabaseReference reff;
    int rating1;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_feedback);
        txtfeedback=(EditText) findViewById(R.id.feedbacktxt);
        reff = FirebaseDatabase.getInstance().getReference("feedback").child("Servicemen_feedback");
    }
    public void send_feed (View view){

        feedback=txtfeedback.getText().toString();
        if(feedback.isEmpty()) {
            txtfeedback.setError("field is empty");
        }else {
            reff = FirebaseDatabase.getInstance().getReference("feedback").child("Clients_feedback");
            com.example.laundrypersonnelmis.feedback clientfeed = new feedback(feedback);
            reff.push().setValue(clientfeed);
            Toast.makeText(Client_Feedback.this, "Feedback sent", Toast.LENGTH_SHORT).show();


            Home();
        }


    }
    public void Home() {
        Intent intent = new Intent(this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        this.finish();


    }
}