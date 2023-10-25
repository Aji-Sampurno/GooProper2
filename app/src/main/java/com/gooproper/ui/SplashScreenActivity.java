package com.gooproper.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.gooproper.R;
import com.gooproper.admin.MainAdminActivity;
import com.gooproper.agen.MainAgenActivity;
import com.gooproper.customer.MainCustomerActivity;
import com.gooproper.guest.MainGuestActivity;
import com.gooproper.util.Preferences;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        String status = Preferences.getKeyStatus(SplashScreenActivity.this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (status.equals("1")){
                    startActivity(new Intent(SplashScreenActivity.this, MainAdminActivity.class));
                    finish();
                } else if (status.equals("2")) {
                    startActivity(new Intent(SplashScreenActivity.this, MainAdminActivity.class));
                    finish();
                } else if (status.equals("3")) {
                    startActivity(new Intent(SplashScreenActivity.this, MainAgenActivity.class));
                    finish();
                } else if (status.equals("0")) {
                    startActivity(new Intent(SplashScreenActivity.this, MainCustomerActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashScreenActivity.this, MainGuestActivity.class));
                    finish();
                }

            }
        }, 2000);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}