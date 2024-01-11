package com.example.laundrypersonnelmis;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText txtusername, txtphone, txtemail, txtpassword,txtlocation;
    String name, phone, email, password,message,location;
    Button registerBtn,pickimage;
    ImageButton btnback;
    DatabaseReference reff;
    RadioGroup radioGroup;
    RadioButton rbclient, rbservice;
    ImageView userimage;
    ProgressBar progressBar;
    StorageReference reference=FirebaseStorage.getInstance().getReference();
    StorageReference storereff;
     Uri imageuri;



    //user user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtusername = (EditText) findViewById(R.id.registerUsernameEditText);
        txtphone = (EditText) findViewById(R.id.registerPhoneEditText);
        txtemail = (EditText) findViewById(R.id.registerFinalEmailEditText);
        txtpassword = (EditText) findViewById(R.id.registerPasswordEditText);
        txtlocation=(EditText)findViewById(R.id.registerLocationEditText);
        registerBtn = (Button) findViewById(R.id.btnRegister);
        btnback = (ImageButton) findViewById(R.id.registerBackBtn);
        radioGroup = findViewById(R.id.radiogroup);
        rbclient = findViewById(R.id.registerClient);
        rbservice = findViewById(R.id.registerserviceman);
        userimage=findViewById(R.id.userimage);
         pickimage=findViewById(R.id.registerPickdateBtn);
         progressBar=findViewById(R.id.progressBar2);



        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();

            }
        });

        // user=new user();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = txtusername.getText().toString();
                password = txtpassword.getText().toString();
                phone = txtphone.getText().toString();
                email = txtemail.getText().toString();
                message = "null";
                location = txtlocation.getText().toString();
                if (name.isEmpty() || password.isEmpty() || phone.isEmpty() || email.isEmpty() || location.isEmpty()|| imageuri==null) {
                    Snackbar.make(txtphone, "fill all the fields", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }

                            }).show();
                } else {


                if (rbclient.isChecked()) {

                    reff = FirebaseDatabase.getInstance().getReference().child("user");
                    reff.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(phone)) {
                                txtphone.setError("Client already exists");
                                Snackbar.make(txtphone, "Client already exists", Snackbar.LENGTH_INDEFINITE)
                                        .setAction("Dismiss", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }

                                        }).show();
                            } else {
                            progressBar.setVisibility(View.VISIBLE);
                                SimpleDateFormat formatter=new SimpleDateFormat("yyyy_mm_dd_HH_mm_ss", Locale.CANADA);
                                Date now=new Date();
                                String filename=formatter.format(now);
                                storereff=FirebaseStorage.getInstance().getReference("user/"+phone+"/"+filename);
                                storereff.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        storereff.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {


                                                user newuser = new user(name, password, phone, email,uri.toString());

                                                reff.child(phone).setValue(newuser);


                                                String phone3="null";
                                                String name3="null";
                                                String message3="null";
                                                Newreqfromclient newnull=new Newreqfromclient(phone3,name3,message3);
                                                reff.child(phone).child("Inprogress").setValue(newnull);
                                                reff.child(phone).child("Declined").setValue(newnull);
                                                reff.child(phone).child("pending req").setValue(newnull);







                                              progressBar.setVisibility(View.INVISIBLE);

                                                Toast.makeText(MainActivity.this, "succesfully Registered A client", Toast.LENGTH_LONG).show();
                                                clientlogin();
                                            }
                                        });
                                    }
                                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(MainActivity.this, "uploading failed !!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                } else {

                    reff = FirebaseDatabase.getInstance().getReference().child("servicemen");
                    reff.addListenerForSingleValueEvent(new ValueEventListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(phone)) {
                                txtphone.setError("Serviceman already exists");
                                Snackbar.make(txtphone, "user already exists", Snackbar.LENGTH_INDEFINITE)
                                        .setAction("Dismiss", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }

                                        }).show();
                            } else {
                                progressBar.setVisibility(View.VISIBLE);
                                SimpleDateFormat formatter=new SimpleDateFormat("yyyy_mm_dd_HH_mm_ss", Locale.CANADA);
                                Date now=new Date();
                                String filename=formatter.format(now);
                                storereff=FirebaseStorage.getInstance().getReference("servicemen/"+phone+"/"+filename);
                                storereff.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                       storereff.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                           @Override
                                           public void onSuccess(Uri uri) {

                                               Float rating = Float.valueOf(0);
                                               servicemen newuser = new servicemen(name, password, phone, email, location, rating,uri.toString());

                                               reff.child(phone).setValue(newuser);


                                               String phone3="null";
                                               String name3="null";
                                               String message3="null";
                                               Newreqfromclient newnull=new Newreqfromclient(phone3,name3,message3);
                                               reff.child(phone).child("Inprogress").setValue(newnull);
                                               reff.child(phone).child("new request").setValue(newnull);
                                              progressBar.setVisibility(View.INVISIBLE);
                                               Toast.makeText(MainActivity.this, "succesfully Registered Serviceman", Toast.LENGTH_LONG).show();
                                               servicemanlogin();
                                           }
                                       });
                                    }
                                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(MainActivity.this, "uploading failed !!", Toast.LENGTH_SHORT).show();
                                    }
                                });







                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

            }
            }


        });

    }


    public void clientlogin() {
        Intent intent = new Intent(this, clientslogin.class);
        startActivity(intent);
        this.finish();
    }

    public void servicemanlogin() {
        Intent service = new Intent(this, servicemenlogin.class);
        startActivity(service);
        this.finish();
    }

    public void back() {
        Intent backactivity = new Intent(this, activity_Start.class);
        backactivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(backactivity);
        this.finish();
    }





    public void pickimage(View view)
    {

        Intent i = new Intent();

        i.setType("image/*");

        i.setAction(Intent.ACTION_GET_CONTENT);


        launchSomeActivity.launch(i);
    }

    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(

            new ActivityResultContracts.StartActivityForResult(),

            result -> {

                if (result.getResultCode()

                        == Activity.RESULT_OK) {

                    Intent data = result.getData();

                    // do your operation from here....

                    if (data != null

                            && data.getData() != null) {

                        imageuri = data.getData();
                        Glide.with(this).load(imageuri).circleCrop().into(userimage);
                       // userimage.setImageURI(imageuri);
                        pickimage.setText("Change Image");
                        Bitmap selectedImageBitmap;

                        try {

                            selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageuri);

                        }

                        catch (IOException e) {

                            e.printStackTrace();

                        }


                    }

                }

            });
}




