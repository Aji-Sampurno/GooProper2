package com.gooproper.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.gooproper.R;
import com.gooproper.customer.MainCustomerActivity;
import com.gooproper.ui.edit.EditAkunActivity;
import com.gooproper.util.Preferences;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        LinearLayout editakun  = findViewById(R.id.lyteditakun);
        LinearLayout ubahsandi = findViewById(R.id.lytubahsandi);
        LinearLayout logout    = findViewById(R.id.lytkeluar);
        ImageView keluar       = findViewById(R.id.ivcancel);

        editakun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this, EditAkunActivity.class));
            }
        });

        ubahsandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this, UbahSandiActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomAlertDialog(view);
            }
        });

        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    public void showCustomAlertDialog(View view) {
        Dialog customDialog = new Dialog(SettingActivity.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.custom_dialog_konfirmasi);

        if (customDialog.getWindow() != null) {
            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
        Button ya = customDialog.findViewById(R.id.btnya);
        Button tidak = customDialog.findViewById(R.id.btntidak);

        ya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferences.clearLoggedInUser(SettingActivity.this);
                startActivity(new Intent(SettingActivity.this, MainCustomerActivity.class));
                finish();
            }
        });

        tidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
            }
        });

        dialogTitle.setText("Apakah Anda Yakin Untuk Keluar");

        ImageView gifImageView = customDialog.findViewById(R.id.ivdialog);

        Glide.with(this)
                .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(gifImageView);

        customDialog.show();
    }
}