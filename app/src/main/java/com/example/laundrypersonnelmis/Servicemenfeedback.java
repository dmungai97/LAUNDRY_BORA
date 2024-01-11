package com.example.laundrypersonnelmis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Servicemenfeedback extends AppCompatActivity {
    EditText txtfeedback;
    String feedback;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicemenfeedback);
        txtfeedback = (EditText) findViewById(R.id.feedbacktxt);
    }

    public void send_feedServiceman(View view) {
        feedback = txtfeedback.getText().toString();
        if(feedback.isEmpty()) {
            txtfeedback.setError("field is empty");
        }else {
            reff = FirebaseDatabase.getInstance().getReference("feedback").child("Servicemen_feedback");
            feedback clientfeed = new feedback(feedback);
            reff.push().setValue(clientfeed);
            Toast.makeText(Servicemenfeedback.this, "Feedback sent", Toast.LENGTH_SHORT).show();
            Home();
        }

    }

    public void Home() {
        Intent intent = new Intent(this, Serviceman_home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        this.finish();
    }
}