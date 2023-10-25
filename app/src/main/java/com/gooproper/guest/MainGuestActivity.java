package com.gooproper.guest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gooproper.R;
import com.gooproper.ui.LoginConditionActivity;

public class MainGuestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_guest);

        BottomNavigationView navigationView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.home_guest_nav, R.id.listing_guest_nav, R.id.agen_guest_nav, R.id.akun_guest_nav).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId(); // Dapatkan ID item yang diklik

                if (itemId == R.id.listing_guest_nav || itemId == R.id.agen_guest_nav || itemId == R.id.akun_guest_nav) {
                    startActivity(new Intent(MainGuestActivity.this, LoginConditionActivity.class));
                    return true;
                }

                // Tambahkan logika untuk item lain jika diperlukan

                return false;
            }
        });


        ColorStateList colorStateList = ContextCompat.getColorStateList(this, R.color.color_navigation);
        navigationView.setItemIconTintList(colorStateList);
        navigationView.setItemTextColor(colorStateList);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    public void onBackPressed() {
        finishAffinity();
    }
}