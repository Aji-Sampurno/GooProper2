package com.gooproper.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.gooproper.R;

public class LamarActivity extends AppCompatActivity {

    TabLayout TLPelamar;
    ViewPager2 VPPelamar;
    FragmentPelamarAdapter pelamarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamar);

        TLPelamar = findViewById(R.id.TLPelamar);
        VPPelamar = findViewById(R.id.VPPelamar);

        FragmentManager fm = getSupportFragmentManager();
        pelamarAdapter = new FragmentPelamarAdapter(fm, getLifecycle());
        VPPelamar.setAdapter(pelamarAdapter);

        TLPelamar.addTab(TLPelamar.newTab().setText("Agen"));
        TLPelamar.addTab(TLPelamar.newTab().setText("Mitra"));
        TLPelamar.addTab(TLPelamar.newTab().setText("Kantor Lain"));

        TLPelamar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                VPPelamar.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        VPPelamar.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                TLPelamar.selectTab(TLPelamar.getTabAt(position));
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}