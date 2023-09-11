package com.gooproper.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.gooproper.ImageViewAdapter;
import com.gooproper.R;
import com.gooproper.adapter.ViewPagerAdapter;

import java.util.ArrayList;

public class ImageViewActivity extends AppCompatActivity {

    ImageView IVBack;
    ArrayList<String> images = new ArrayList<>();
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        viewPager = findViewById(R.id.VPImageView);
        IVBack = findViewById(R.id.IVBackImageView);

        IVBack.setOnClickListener(v -> startActivity(new Intent(ImageViewActivity.this, DetailListingActivity.class)));

        Intent intent = getIntent();
        images = intent.getStringArrayListExtra("imageResources");

        ImageViewAdapter newPagerAdapter = new ImageViewAdapter(this, images);
        viewPager.setAdapter(newPagerAdapter);

        viewPager.setCurrentItem(0);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

    }
}