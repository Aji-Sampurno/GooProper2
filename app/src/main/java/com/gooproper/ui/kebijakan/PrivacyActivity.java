package com.gooproper.ui.kebijakan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.gooproper.R;

public class PrivacyActivity extends AppCompatActivity {
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        back = findViewById(R.id.backFormBtn);
        back.setOnClickListener(view -> finish());
    }
}