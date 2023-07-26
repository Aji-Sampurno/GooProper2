package com.gooproper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class UbahSandiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_sandi);

        ImageView back  = findViewById(R.id.ivback);
        ImageView save  = findViewById(R.id.ivsave);
        EditText pass   = findViewById(R.id.etpassword);
        EditText repass = findViewById(R.id.etrepassword);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}