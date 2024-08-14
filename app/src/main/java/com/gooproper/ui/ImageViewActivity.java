package com.gooproper.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.gooproper.adapter.image.ImageViewAdapter;
import com.gooproper.R;

import java.util.ArrayList;

public class ImageViewActivity extends AppCompatActivity {

    TextView TVJumlahGambar;
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
        TVJumlahGambar = findViewById(R.id.TVJumlahGambar);

        IVBack.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        images = intent.getStringArrayListExtra("imageResources");

        imageViewAdapter = new ImageViewAdapter(this, images);
        viewPager.setAdapter(imageViewAdapter);

        int position = getIntent().getIntExtra("position", 0);
        viewPager.setCurrentItem(position);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                updatePageInfo(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        updatePageInfo(position);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    private void updatePageInfo(int currentPage) {
        String info = (currentPage + 1) + "/" + images.size();
        TVJumlahGambar.setText(info);
    }
    @Override
    public void onBackPressed() {
        if (imageViewAdapter != null && imageViewAdapter.getCurrentExoPlayer() != null && imageViewAdapter.getCurrentExoPlayer().isPlaying()) {
            imageViewAdapter.getCurrentExoPlayer().stop();
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}