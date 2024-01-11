package com.example.laundrypersonnelmis;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 2000; // 2 seconds

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Use a Handler to delay the start of the main activity
        new Handler().postDelayed(() -> {
            // Start the main activity after the splash delay
            Intent intent = new Intent(SplashActivity.this, activity_Start.class);
            startActivity(intent);
            finish(); // close the splash activity so that it's not accessible by pressing back
        }, SPLASH_DELAY);
    }
}
