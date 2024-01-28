package com.example.laundrypersonnelmis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Toast;


@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 800; // 0.8 seconds

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Check internet connectivity
        if (isNetworkAvailable()) {
            // Use a Handler to delay the start of the main activity
            new Handler().postDelayed(() -> {
                // Start the main activity after the splash delay
                Intent intent = new Intent(SplashActivity.this, activity_Start.class);
                startActivity(intent);
                finish(); // close the splash activity so that it's not accessible by pressing back
            }, SPLASH_DELAY);
        } else {
            // Notify the user about the lack of internet connectivity
            Toast.makeText(this, "No internet connection. Please check your network settings.", Toast.LENGTH_SHORT).show();
            // close the splash activity if there is no internet connection
            Intent intent = new Intent(SplashActivity.this, activity_Start.class);
            startActivity(intent);
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
}