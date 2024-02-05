package com.gooproper.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.gooproper.adapter.ImageViewAdapter;
import com.gooproper.R;

import java.util.ArrayList;

public class ImageViewActivity extends AppCompatActivity {

    ImageView IVBack;
    ArrayList<String> images = new ArrayList<>();
    private ViewPager viewPager;
    private ImageViewAdapter imageViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        viewPager = findViewById(R.id.VPImageView);
        IVBack = findViewById(R.id.IVBackImageView);

        IVBack.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        images = intent.getStringArrayListExtra("imageResources");

        imageViewAdapter = new ImageViewAdapter(this, images);
        viewPager.setAdapter(imageViewAdapter);

        viewPager.setCurrentItem(0);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    @Override
    public void onBackPressed() {
        // Hentikan pemutaran video jika sedang berlangsung
        if (imageViewAdapter != null && imageViewAdapter.getCurrentExoPlayer() != null && imageViewAdapter.getCurrentExoPlayer().isPlaying()) {
            imageViewAdapter.getCurrentExoPlayer().stop();
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}