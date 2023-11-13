package com.gooproper.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.res.ColorStateList;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gooproper.R;
import com.gooproper.admin.fragment.ListingAdminFragment;

public class MainAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        if (getIntent().hasExtra("fragment_to_open")) {
            String fragmentToOpen = getIntent().getStringExtra("fragment_to_open");
            if ("pralisting".equals(fragmentToOpen)) {
                openFragment();
            }
        }

        BottomNavigationView navigationView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.closingAdmin_nav, R.id.flowUpAdmin_nav, R.id.homeAdmin_nav, R.id.listingAdmin_nav, R.id.akunAdmin_nav).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        ColorStateList colorStateList = ContextCompat.getColorStateList(this, R.color.color_navigation);
        navigationView.setItemIconTintList(colorStateList);
        navigationView.setItemTextColor(colorStateList);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void openFragment() {
        // Buka fragment ListingFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host, new ListingAdminFragment())
                .commit();
    }
}