package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

// We implement Runnable directly on the class to avoid the compiler bug
public class SplashActivity extends AppCompatActivity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Pass 'this' directly instead of 'new Runnable()'
        new Handler().postDelayed(this, 3000);
    }

    @Override
    public void run() {
        // This code executes after 3 seconds
        Intent intent = new Intent(SplashActivity.this, PortraitActivity.class);
        startActivity(intent);
        finish();
    }
}