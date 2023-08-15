package com.gooproper.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.gooproper.R;

public class RewardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}