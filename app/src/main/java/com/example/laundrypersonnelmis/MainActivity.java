package com.example.laundrypersonnelmis;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.OnFailureListener;
//import com.google.firebase.storage.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText txtUsername, txtPhone, txtEmail, txtPassword, txtLocation;
    String name, phone, email, password, location;
    Button registerBtn, pickImageBtn;
    ImageButton btnBack;
    DatabaseReference databaseReference;
    RadioGroup radioGroup;
    RadioButton rbClient, rbService,rbLaundryMart;
    ImageView userImage;
    ProgressBar progressBar;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    StorageReference imageRef;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeViews();

        btnBack.setOnClickListener(v -> back());

        registerBtn.setOnClickListener(v -> registerUser());

        pickImageBtn.setOnClickListener(this::pickImage);
    }

    private void initializeViews() {
        txtUsername = findViewById(R.id.registerUsernameEditText);
        txtPhone = findViewById(R.id.registerPhoneEditText);
        txtEmail = findViewById(R.id.registerFinalEmailEditText);
        txtPassword = findViewById(R.id.registerPasswordEditText);
        txtLocation = findViewById(R.id.registerLocationEditText);
        registerBtn = findViewById(R.id.btnRegister);
        btnBack = findViewById(R.id.registerBackBtn);
        radioGroup = findViewById(R.id.radiogroup);
        rbClient = findViewById(R.id.registerClient);
        rbService = findViewById(R.id.registerserviceman);
        rbLaundryMart = findViewById(R.id.registerLaundryMart); // New RadioButton

        userImage = findViewById(R.id.userimage);
        pickImageBtn = findViewById(R.id.registerPickdateBtn);
        progressBar = findViewById(R.id.progressBar2);
    }

    private void registerUser() {
        name = txtUsername.getText().toString();
        password = txtPassword.getText().toString();
        phone = txtPhone.getText().toString();
        email = txtEmail.getText().toString();
        location = txtLocation.getText().toString();

        if (name.isEmpty() || password.isEmpty() || phone.isEmpty() || email.isEmpty() || location.isEmpty() || imageUri == null) {
            Snackbar.make(txtPhone, "Please fill all the fields", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Dismiss", view -> { })
                    .show();
            return;
        }

        if (rbClient.isChecked()) {
            registerClient();
        } else if (rbService.isChecked()) {
            registerServiceMan();
        } else if (rbLaundryMart.isChecked()) { // Check if "Laundry Mart" is selected
            registerLaundryMart();
        } else {
            Snackbar.make(txtPhone, "Please select user type", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Dismiss", view -> { })
                    .show();
        }
    }

    // REGISTER USERS
    private void registerClient() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(phone);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    txtPhone.setError("Client already exists");
                    Snackbar.make(txtPhone, "Client already exists", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Dismiss", view -> {}).show();
                } else {
                    uploadImageAndRegister("user");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });
    }

    private void registerServiceMan() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("servicemen").child(phone);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    txtPhone.setError("Service man already exists");
                    Snackbar.make(txtPhone, "Service man already exists", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Dismiss", view -> {}).show();
                } else {
                    uploadImageAndRegister("servicemen");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });
    }

    private void registerLaundryMart() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("laundry_mart").child(phone);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    txtPhone.setError("Laundry Mart already exists");
                    Snackbar.make(txtPhone, "Laundry Mart already exists", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Dismiss", view -> {}).show();
                } else {
                    uploadImageAndRegister("laundry_mart");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });
    }


    //UPLOAD AND REGISTER
    private void uploadImageAndRegister(String userType) {
        progressBar.setVisibility(View.VISIBLE);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_mm_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String filename = formatter.format(now);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(userType + "/" + phone + "/" + filename);
        storageReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
            storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                if (userType.equals("user")) {
                    createUser(uri.toString());
                } else if (userType.equals("servicemen")) {
                    createServiceMan(uri.toString());
                } else if (userType.equals("laundry_mart")) { // New condition for laundry mart
                    createLaundryMart(uri.toString());
                }
            });
        }).addOnProgressListener(snapshot -> {}).addOnFailureListener(e -> {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(MainActivity.this, "Uploading failed!!", Toast.LENGTH_SHORT).show();
        });
    }



    //CREATE THE APP USERS
    private void createUser(String imageUrl) {
        user newUser = new user(name, password, phone, email, imageUrl);
        databaseReference.setValue(newUser);
        initializeUserRequests();
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(MainActivity.this, "Successfully Registered a Client", Toast.LENGTH_LONG).show();
        clientLogin();
    }

    private void createServiceMan(String imageUrl) {
        Float rating = Float.valueOf(0);
        servicemen newServiceMan = new servicemen(name, password, phone, email, location, rating, imageUrl);
        databaseReference.setValue(newServiceMan);
        initializeServiceManRequests();
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(MainActivity.this, "Successfully Registered a Service Man", Toast.LENGTH_LONG).show();
        serviceManLogin();
    }

    private void createLaundryMart(String imageUrl) {
        // Perform the necessary operations for registering a laundry mart user
        laundryMart newLaundryMart = new laundryMart(name, password, phone, email, location, imageUrl);
        databaseReference.setValue(newLaundryMart);
        // Any other specific operations for laundry mart user registration

        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(MainActivity.this, "Successfully Registered a Laundry Mart", Toast.LENGTH_LONG).show();
        laundryMartLogin(); // Assuming you have a login method for laundry mart users
    }


    private void initializeUserRequests() {
        String phone3 = "null";
        String name3 = "null";
        String message3 = "null";
        Newreqfromclient newNull = new Newreqfromclient(phone3, name3, message3);
        databaseReference.child("Inprogress").setValue(newNull);
        databaseReference.child("Declined").setValue(newNull);
        databaseReference.child("pending req").setValue(newNull);
    }

    private void initializeServiceManRequests() {
        String phone3 = "null";
        String name3 = "null";
        String message3 = "null";
        Newreqfromclient newNull = new Newreqfromclient(phone3, name3, message3);
        databaseReference.child("Inprogress").setValue(newNull);
        databaseReference.child("new request").setValue(newNull);
    }

    private void clientLogin() {
        Intent intent = new Intent(this, clientslogin.class);
        startActivity(intent);
        finish();
    }

    private void serviceManLogin() {
        Intent service = new Intent(this, servicemenlogin.class);
        startActivity(service);
        finish();
    }
    private void laundryMartLogin() {
        Intent service = new Intent(this, laundryMartLogin.class);
        startActivity(service);
        finish();
    }


    public void back() {
        Intent backActivity = new Intent(this, activity_Start.class);
        backActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(backActivity);
        finish();
    }

    public void pickImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launchSomeActivity.launch(intent);
    }

    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null && result.getData().getData() != null) {
                    imageUri = result.getData().getData();
                    Glide.with(this).load(imageUri).circleCrop().into(userImage);
                    pickImageBtn.setText("Change Image");
                }
            });
}
