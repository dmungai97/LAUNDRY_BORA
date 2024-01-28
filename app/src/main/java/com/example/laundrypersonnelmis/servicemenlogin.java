package com.example.laundrypersonnelmis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class servicemenlogin extends AppCompatActivity {
    EditText txtusername, txtpassword;
    private static final String PREF_NAME = "LaundryPersonnelMIS";
    private static final String PREF_EMAIL = "email";
    private static final String PREF_PASSWORD = "password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicemenlogin);
        txtusername = (EditText)findViewById(R.id.loginEmailEditText);
        txtpassword = (EditText)findViewById(R.id.loginPasswordEditText);
        txtusername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Check if both email and password are not empty
                if (!txtusername.getText().toString().isEmpty() && !txtpassword.getText().toString().isEmpty()) {
                    // Don't perform automatic login here
                }
            }
        });

        txtpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Check if both email and password are not empty
                if (!txtusername.getText().toString().isEmpty() && !txtpassword.getText().toString().isEmpty()) {
                    // Don't perform automatic login here
                }
            }
        });

        loadSavedCredentials();
    }

    private boolean validateuemail() {
        String val = txtusername.getText().toString();
        if (val.isEmpty()) {
            txtusername.setError("field cannot be empty");
            //Toast.makeText(login.this, "this field cannot be empty", Toast.LENGTH_LONG).show();
            return false;
        } else {
            // txtusername.setError(null);
            return true;
        }
    }

    private boolean validatepassword() {
        String val = txtpassword.getText().toString();
        if (val.isEmpty()) {
            txtpassword.setError("field cannot be empty");
            //txtpassword.setError(null);
            return false;
        } else {
            txtpassword.setError(null);
            // txtpassword.setErrorEnabled(false);
            return true;
        }
    }

    private void loadSavedCredentials() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String savedEmail = preferences.getString(PREF_EMAIL, null);
        String savedPassword = preferences.getString(PREF_PASSWORD, null);

        if (savedEmail != null && savedPassword != null) {
            txtusername.setText(savedEmail);
            txtpassword.setText(savedPassword);
            if (isNetworkAvailable()) {
                isuser(); // Attempt login automatically only when network is available
            } else {
                // Display a toast message indicating that the device is offline
                Toast.makeText(this, "No internet connection. Automatic login disabled.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void loginuser(View view) {
        if (isNetworkAvailable()) {
            if (validateuemail() && validatepassword()) {
                isuser();
            }
        } else {
            // Notify the user about the lack of internet connectivity
            Toast.makeText(this, "No internet connection. Please check your network settings.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
        return false;
    }

    private void isuser() {
        String userEnteremail = txtusername.getText().toString();
        String userEnterPassword = txtpassword.getText().toString();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("servicemen");
        //Query checkUser = reference.orderByChild("user").equalTo(userEnteremail);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(userEnteremail)) {

                    final String getpassword = snapshot.child(userEnteremail).child("email").getValue(String.class);
                    assert getpassword != null;
                    if (getpassword.equals(userEnterPassword)) {

                        final String nameFromDb = snapshot.child(userEnteremail).child("name").getValue(String.class);
                        final String emailFromDb = snapshot.child(userEnteremail).child("password").getValue(String.class);
                        final String phoneFromDb = snapshot.child(userEnteremail).child("phone").getValue(String.class);
                        final String messageFromDb =snapshot.child(userEnteremail).child("message").getValue(String.class);
                        final String imageFromDb =snapshot.child(userEnteremail).child("imageUri").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), Serviceman_home.class);
                        intent.putExtra("name", nameFromDb);
                        intent.putExtra("email", emailFromDb);
                        intent.putExtra("phone", phoneFromDb);
                        intent.putExtra("message",messageFromDb);
                        intent.putExtra("image",imageFromDb);
                        startActivity(intent);

                        saveCredentials(); // Save credentials upon successful login

                        //startActivities(intent);
                    } else {
                        txtpassword.setError("invalid password");
                        // Toast.makeText(login.this, "wrong password", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    txtusername.setError("No such user exists");
                    Log.d("UserNotExist", "User does not exist");;
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void saveCredentials() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_EMAIL, txtusername.getText().toString());
        editor.putString(PREF_PASSWORD, txtpassword.getText().toString());
        editor.apply();
    }

}