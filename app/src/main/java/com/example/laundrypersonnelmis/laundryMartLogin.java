package com.example.laundrypersonnelmis;

import androidx.appcompat.app.AppCompatActivity;


import androidx.annotation.NonNull;
import android.content.SharedPreferences;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;

import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class laundryMartLogin extends AppCompatActivity {
    EditText txtUserPhone, txtPassword;
    private static final String PREF_NAME = "LaundryPersonnelMIS";
    private static final String PREF_EMAIL = "email";
    private static final String PREF_PASSWORD = "password";

    private String userEnterPhone;
    private String userEnterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundry_mart_login);
        txtUserPhone = findViewById(R.id.loginEmailEditText);
        txtPassword = findViewById(R.id.loginPasswordEditText);
        txtUserPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Check if both email and password are not empty
                if (!txtUserPhone.getText().toString().isEmpty() && !txtPassword.getText().toString().isEmpty()) {
                    // Don't perform automatic login here
                }
            }
        });

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Check if both email and password are not empty
                if (!txtUserPhone.getText().toString().isEmpty() && !txtPassword.getText().toString().isEmpty()) {
                    // Don't perform automatic login here
                }
            }
        });

        loadSavedCredentials();
    }
    private boolean validateuemail() {
        String val = txtUserPhone.getText().toString();
        if (val.isEmpty()) {
            txtUserPhone.setError("field cannot be empty");

            return false;
        } else {

            return true;
        }
    }

    private boolean validatePassword() {
        String val = txtPassword.getText().toString();
        if (val.isEmpty()) {
            txtPassword.setError("field cannot be empty");
            //txtpassword.setError(null);
            return false;
        } else {
            txtPassword.setError(null);
            // txtpassword.setErrorEnabled(false);
            return true;
        }
    }

    private void loadSavedCredentials() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String savedEmail = preferences.getString(PREF_EMAIL, null);
        String savedPassword = preferences.getString(PREF_PASSWORD, null);

        if (savedEmail != null && savedPassword != null) {
            txtUserPhone.setText(savedEmail);
            txtPassword.setText(savedPassword);
            if (isNetworkAvailable()) {
                isUser(); // Attempt login automatically only when network is available
            } else {
                // Display a toast message indicating that the device is offline
                Toast.makeText(this, "No internet connection. Automatic login disabled.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void openServices(View view) {
        if (isNetworkAvailable()) {
            if (validateuemail() && validatePassword()) {
                isUser();
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

    private void isUser() {
        userEnterPhone = txtUserPhone.getText().toString();
        userEnterPassword = txtPassword.getText().toString();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("laundry_mart");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(userEnterPhone)) {
                    //Toast.makeText(login.this, "USER EXISTS", Toast.LENGTH_SHORT).show();
                    final String getPassword = snapshot.child(userEnterPhone).child("password").getValue(String.class);
                    assert getPassword != null;
                    if (getPassword.equals(userEnterPassword)) {

                        final String nameFromDb = snapshot.child(userEnterPhone).child("name").getValue(String.class);
                        final String emailFromDb = snapshot.child(userEnterPhone).child("phone").getValue(String.class);
                        final String phoneFromDb = snapshot.child(userEnterPhone).child("password").getValue(String.class);
                        final String imageFromDb =snapshot.child(userEnterPhone).child("imageUri").getValue(String.class);
                        Intent intent = new Intent(getApplicationContext(), Laundry_Mart_Profile.class);
                        intent.putExtra("name", nameFromDb);
                        intent.putExtra("email", emailFromDb);
                        intent.putExtra("phone", phoneFromDb);
                        intent.putExtra("image",imageFromDb);
                        startActivity(intent);

                        saveCredentials(); // Save credentials upon successful login

                    } else {
                        txtPassword.setError("invalid password");
                        // Toast.makeText(login.this, "wrong password", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    txtUserPhone.setError("No such user exists");
                    Toast.makeText(laundryMartLogin.this, "user does  not exist", Toast.LENGTH_SHORT).show();
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
        editor.putString(PREF_EMAIL, txtUserPhone.getText().toString().trim());
        editor.putString(PREF_PASSWORD, txtPassword.getText().toString().trim());

        if (editor.commit()) {
            // Data successfully saved
            Toast.makeText(laundryMartLogin.this, "Success", Toast.LENGTH_SHORT).show();
        } else {
            // Failed to save data
            Toast.makeText(laundryMartLogin.this, "Failed to save credentials", Toast.LENGTH_SHORT).show();
        }
    }
}
